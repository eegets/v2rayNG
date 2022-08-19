package com.v2ray.ang.custom.fragment

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.forest.bss.sdk.base.frag.BaseFragment
import com.forest.bss.sdk.ext.nonNull
import com.forest.bss.sdk.ext.showDialogSafely
import com.forest.bss.sdk.ext.viewModel
import com.forest.bss.sdk.toast.ToastExt
import com.forest.net.data.success
import com.tencent.mmkv.MMKV
import com.v2ray.ang.AppConfig
import com.v2ray.ang.R
import com.v2ray.ang.custom.activity.BuyActivity
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.custom.data.model.HomeModel
import com.v2ray.ang.custom.dialog.BottomShellDailogFragment
import com.v2ray.ang.databinding.CustomFragmentHomeBinding
import com.v2ray.ang.extension.toast
import com.v2ray.ang.service.V2RayServiceManager
import com.v2ray.ang.util.AngConfigManager
import com.v2ray.ang.util.MmkvManager
import com.v2ray.ang.util.Utils
import com.v2ray.ang.viewmodel.MainViewModel

/**
 * Created by wangkai on 2021/05/01 10:53

 * Desc 首页
 */

class HomeFragment : BaseFragment() {

    private val homeModel by lazy { viewModel<HomeModel>() }

    private val mainViewModel: MainViewModel? by viewModels()

    private var binding: CustomFragmentHomeBinding? = null

    private val mainStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE) }

    private val settingsStorage by lazy { MMKV.mmkvWithID(MmkvManager.ID_SETTING, MMKV.MULTI_PROCESS_MODE) }

    private var homeDay: TextView? = null

    private var selectModeId: Int = 0

    private var hostList: List<HomeBean.Host> = mutableListOf()

    override fun layoutId(): Int = R.layout.custom_fragment_home
    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CustomFragmentHomeBinding.inflate(inflater)
        return binding?.root
    }

    override fun isEnableViewBinding(): Boolean = true

    override fun bindingView(rootView: View?) {
        homeModel?.getInfo()
        homeDay?.text = "xxx"

        /**
         * 选择节点
         */
        binding?.selectMode?.setOnClickListener {
            val dialogFragment = BottomShellDailogFragment.newInstance(listData = hostList)
            dialogFragment.showDialogSafely(parentFragmentManager)
            dialogFragment.selectSModeListener ={ sModeId->
                selectModeId = sModeId
            }
        }

        /**
         * 立即续费
         */
        binding?.homeBuy?.setOnClickListener {
            Intent(requireActivity(), BuyActivity::class.java).apply {
                startActivity(this)
            }
        }

        /**
         * 连接
         */
        binding?.connected?.setOnClickListener {
//            homeModel?.checkAuth(selectModeId)
            importBatchConfig(keys)
        }
    }

    override fun bindViewModelObserve(rootView: View?) {
        homeModel?.liveDataInfo?.observe(this) {
            if (it.success()) {
                hostList = it.getOrNull()?.results?.hosts.nonNull(mutableListOf())
            } else {
                ToastExt.show(it?.getOrNull()?.msg ?: "")
            }
        }

        homeModel?.liveDataVMess?.observe(this) {
            if (it.success()) {
                val vMessString = it.getOrNull()?.results?.vmess_string
                if (vMessString?.isNotEmpty() == true) {
                    importBatchConfig(vMessString)
                }
            }
        }
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
            context?.toast(R.string.toast_success)
            mainViewModel?.reloadServerList()
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

    /**
     * 开始连接
     */
    private fun startV2Ray() {
        if (mainStorage?.decodeString(MmkvManager.KEY_SELECTED_SERVER).isNullOrEmpty()) {
            return
        }
        requireContext().toast("开始连接")
        V2RayServiceManager.startV2Ray(requireContext())
        requireContext().toast("连接成功")
    }

    companion object {
        private const val REQUEST_CODE_VPN_PREPARE = 0
        private const val REQUEST_SCAN = 1
        private const val REQUEST_FILE_CHOOSER = 2
        private const val REQUEST_SCAN_URL = 3

        const val keys = "vmess://eyJhZGQiOiI1NC45NS4xMjYuNjUiLCJhaWQiOiI2NCIsImhvc3QiOiIiLCJpZCI6ImQwNjQ5ODlkLTg5NGMtNDJjNi1hYWE0LTA1ZjE3NDcxMzM5YSIsIm5ldCI6IndzIiwicGF0aCI6Ii93cy83czRjazRpOjQxYmQ3NWU2NmMyMzA2ZWYzZTAxZWVmZmIxYzYwZTNlLyIsInBvcnQiOiI4MCIsInBzIjoiZmlyc3QtdjJyYXkiLCJ0bHMiOiIiLCJ0eXBlIjoibm9uZSIsInYiOiIyIn0="
    }
}