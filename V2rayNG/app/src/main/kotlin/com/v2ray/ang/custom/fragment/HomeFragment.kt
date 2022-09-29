package com.v2ray.ang.custom.fragment

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.frag.BaseFragment
import com.forest.bss.sdk.ext.*
import com.forest.bss.sdk.toast.SnackBarExt
import com.forest.net.data.success
import com.tencent.mmkv.MMKV
import com.v2ray.ang.AppConfig
import com.v2ray.ang.R
import com.v2ray.ang.custom.activity.AppInfoActivity
import com.v2ray.ang.custom.activity.BuyActivity
import com.v2ray.ang.custom.activity.LoginActivity
import com.v2ray.ang.custom.activity.MainActivity
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.custom.data.model.HomeModel
import com.v2ray.ang.custom.data.model.MineModel
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.custom.dialog.BottomShellDialogFragment
import com.v2ray.ang.custom.dialog.CommonDialog
import com.v2ray.ang.custom.dialog.DownloadDialog
import com.v2ray.ang.custom.dialog.LoadingUtils
import com.v2ray.ang.custom.webSocket.ConnectUtils
import com.v2ray.ang.custom.webSocket.ConnectUtils.connectAppSend
import com.v2ray.ang.custom.webSocket.ConnectUtils.disConnectAppSend
import com.v2ray.ang.custom.webSocket.ConnectUtils.loginSend
import com.v2ray.ang.custom.webSocket.ConnectUtils.preConnect
import com.v2ray.ang.databinding.CustomFragmentHomeBinding
import com.v2ray.ang.extension.toast
import com.v2ray.ang.service.V2RayServiceManager
import com.v2ray.ang.util.AngConfigManager
import com.v2ray.ang.util.MmkvManager
import com.v2ray.ang.util.Utils
import com.v2ray.ang.viewmodel.MainViewModel
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/01 10:53

 * Desc 首页
 */

class HomeFragment : BaseFragment() {

    private val homeModel by lazy { viewModel<HomeModel>() }

    private val mainViewModel: MainViewModel? by viewModels()

    private val mimeModel by lazy { viewModel<MineModel>() }

    private var binding: CustomFragmentHomeBinding? = null

    private val mainStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_MAIN,
            MMKV.MULTI_PROCESS_MODE
        )
    }

    private val settingsStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_SETTING,
            MMKV.MULTI_PROCESS_MODE
        )
    }

    private var packageInfos: List<String> = mutableListOf()

    private var hostList: List<HomeBean.Host> = mutableListOf()

    private val connectLoading: LoadingUtils by lazy { LoadingUtils() }

    override fun layoutId(): Int = R.layout.custom_fragment_home
    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomFragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun isEnableViewBinding(): Boolean = true

    override fun bindingView(rootView: View?) {
        homeModel?.getInfo()

        mimeModel?.checkUpdate()

        mainViewModel?.startListenBroadcast()

        /**
         * 选择加速模式
         */
        binding?.selectAppInfo?.setOnClickListener {
            val intent = Intent(requireActivity(), AppInfoActivity::class.java)
            intent.putStringArrayListExtra("packageInfos", packageInfos.asType())
            startActivity(intent)
        }

        /**
         * 选择安全配置
         */
        binding?.securityConfig?.setOnClickListener {
            val securityLists: MutableList<HomeBean.Host> = mutableListOf()
            securityLists.add((HomeBean.Host(id= 1, host = "", title = "高")))
            securityLists.add((HomeBean.Host(id= 1, host = "",title = "中")))
            securityLists.add((HomeBean.Host(id= 1, host = "",title = "低")))
            val dialogFragment = BottomShellDialogFragment.newInstance(listData = securityLists)
            dialogFragment.showDialogSafely(parentFragmentManager)
            dialogFragment.selectSModeListener = { id, title ->
                binding?.securityText?.text = title
            }
        }

        /**
         * 选择节点
         */
        binding?.selectMode?.setOnClickListener{
            val dialogFragment = BottomShellDialogFragment.newInstance(listData = hostList)
            dialogFragment.showDialogSafely(parentFragmentManager)
            dialogFragment.selectSModeListener = { sModeId, sTitle ->
                selectModeId = sModeId
                binding?.selectModeText?.text = sTitle
            }
        }
        /**
         * 关闭加速
         */
        binding?.closeConnect?.setOnClickListener {
            Utils.stopVService(requireActivity())
            ConnectUtils.instance(wsUrl)?.disConnectAppSend(requireActivity(), selectModeId)
        }

        /**
         * 立即续费
         */
        binding?.homeBuy?.setOnClickListener {
            val intent = Intent(requireActivity(), BuyActivity::class.java)
            intent.putExtra("userInfo", activity?.asType<MainActivity>()?.userInfo)
            startActivity(intent)
        }

        /**
         * 连接
         */
        binding?.connected?.setOnClickListener {
            startLoading()
            homeModel?.checkAuth(selectModeId)
        }
    }

    override fun bindViewModelObserve(rootView: View?) {
        homeModel?.liveDataInfo?.observe(this) {
            if (it.success()) {

                it.getOrNull()?.results?.apply {
                    wsUrl = this.ws_url

                    val isConnected = ConnectUtils.instance(wsUrl)?.preConnect()
                    if (isConnected == true) {
                        ConnectUtils.instance(wsUrl)?.loginSend(requireActivity())
                    }
                    hostList = this.hosts.nonNull(mutableListOf())

                    packageInfos = this.auth_apps

                    this.user_info.is_vip.isVip(this.exp_day)
                    activity?.asType<MainActivity>()?.userInfo = this.user_info
                }
            } else if (it.getOrNull()?.code == 300) {

                lifecycleScope.launch {
                    UserInfoDataStore.clear()
                }

                SnackBarExt.show(requireActivity(), it?.getOrNull()?.msg ?: "")

                startActivity(Intent(activity, LoginActivity::class.java))

                activity?.finish()
            } else {
                SnackBarExt.show(requireActivity(), it?.getOrNull()?.msg ?: "")
            }
        }

        homeModel?.liveDataVMess?.observe(this) {
            if (it.success()) {
                val vMessString = it.getOrNull()?.results
                importBatchConfig(vMessString)
                ConnectUtils.instance(wsUrl)?.connectAppSend(requireActivity(), selectModeId)
            } else {
                CommonDialog.newInstance("您的VIP会员已过期，请联系代理商购买激活卡开通会员")
                    .showDialogSafely(parentFragmentManager)
            }
        }
        mainViewModel?.isRunning?.observe(this) { isRunning ->
            if (isRunning) {
                alreadyLoaded()
            } else {
                stopLoaded()
            }
        }
        mimeModel?.liveDataCheckUpdate?.observe(requireActivity()) {
            if (it.success()) {
                it.getOrNull()?.results?.apply {
                    if (!this.has_update) {
                        DownloadDialog.newInstance(this).showDialogSafely(parentFragmentManager)
                    }
                }
            }
        }
    }

    /**
     * 开始启动加速
     */
    private fun startLoading() {
        binding?.frameLayout?.makeVisible()
        binding?.loadingLayout?.makeVisible()
        val rotateAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.icon_rotate_in)
        val rotateAnimation1: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.icon_rotate_out)
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation1.interpolator = LinearInterpolator()
        binding?.loadingIconIn?.startAnimation(rotateAnimation)
        binding?.loadingIconOut?.startAnimation(rotateAnimation1)
        binding?.loadingText?.text = "连接中"
    }

    /**
     * 已经在加速中
     */
    private fun alreadyLoaded() {
        binding?.frameLayout?.makeVisible()
        binding?.loadingLayout?.makeInvisible()
        binding?.loadingIconIn?.clearAnimation()
        binding?.loadingIconOut?.clearAnimation()
        binding?.loadingText?.text = "连接成功"
    }

    /**
     * 已经断开了加速
     */
    private fun stopLoaded() {
        binding?.loadingIconIn?.clearAnimation()
        binding?.loadingIconOut?.clearAnimation()
        binding?.frameLayout?.makeGone()
    }

    /***
     * 配置Keys
     */
    private fun importBatchConfig(server: String?, subid: String = "") {
        var count = AngConfigManager.importBatchConfig(server, subid, true)
        if (count <= 0) {
            count = AngConfigManager.importBatchConfig(Utils.decode(server!!), subid, true)
        }
        if (count > 0) {
            mainViewModel?.reloadServerList()
            connectLoading.hide()
            if (mainViewModel?.isRunning?.value == true) {
                Utils.stopVService(requireContext())
            } else if (settingsStorage?.decodeString(AppConfig.PREF_MODE) ?: "VPN" == "VPN") {
                val intent = VpnService.prepare(requireContext())
                if (intent == null) {
                    startV2Ray()
                } else {
                    startActivityForResult(intent, REQUEST_CODE_VPN_PREPARE)
                }
            } else {
                startV2Ray()
            }
        } else {
            context?.toast(R.string.toast_failure)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_VPN_PREPARE ->
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    startV2Ray()
                }
            REQUEST_SCAN ->
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    importBatchConfig(data?.getStringExtra("SCAN_RESULT"))
                }
//            REQUEST_FILE_CHOOSER -> {
//                val uri = data?.data
//                if (resultCode == AppCompatActivity.RESULT_OK && uri != null) {
//                    readContentFromUri(uri)
//                }
//            }
//            REQUEST_SCAN_URL ->
//                if (resultCode == AppCompatActivity.RESULT_OK) {
//                    importConfigCustomUrl(data?.getStringExtra("SCAN_RESULT"))
//                }
        }
    }

    private fun String.isVip(exp_day: String? = null) {
        if (this == "是") {
            if (exp_day.isNullOrEmpty()) {
                binding?.homeDay?.text = "未开通会员或会员已到期，请前往续费或开通会员"
                return
            } else {
                val msg = "您的会员将于 $exp_day 天后到期"
                SpannableString(msg).let {
                    it.textSpan(msg, exp_day, resources.getColor(R.color.colorPingRed))
                    it
                }.apply {
                    binding?.homeDay?.text = this
                }
            }
        } else {
            binding?.homeDay?.text = "未开通会员或会员已到期，请前往续费或开通会员"
        }
    }

    /**
     * 开始连接
     */
    private fun startV2Ray() {
        if (mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER).isNullOrEmpty()) {
            return
        }
        V2RayServiceManager.startV2Ray(requireContext())
    }

    companion object {
        private const val REQUEST_CODE_VPN_PREPARE = 0
        private const val REQUEST_SCAN = 1
        private const val REQUEST_FILE_CHOOSER = 2
        private const val REQUEST_SCAN_URL = 3

        /**
         * 选择的节点ID
         */
        var selectModeId: Int = 0

        /**
         * webSocket Url 地址
         */
        var wsUrl: String? = null

        const val keys =
            "vmess://eyJhZGQiOiI1NC45NS4xMjYuNjUiLCJhaWQiOiI2NCIsImhvc3QiOiIiLCJpZCI6ImQwNjQ5ODlkLTg5NGMtNDJjNi1hYWE0LTA1ZjE3NDcxMzM5YSIsIm5ldCI6IndzIiwicGF0aCI6Ii93cy83czRjazRpOjQxYmQ3NWU2NmMyMzA2ZWYzZTAxZWVmZmIxYzYwZTNlLyIsInBvcnQiOiI4MCIsInBzIjoiZmlyc3QtdjJyYXkiLCJ0bHMiOiIiLCJ0eXBlIjoibm9uZSIsInYiOiIyIn0="
    }
}