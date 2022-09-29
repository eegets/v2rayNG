package com.v2ray.ang.custom.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.frag.BaseFragment
import com.forest.bss.sdk.ext.asType
import com.forest.bss.sdk.ext.finish
import com.forest.bss.sdk.ext.showDialogSafely
import com.forest.bss.sdk.ext.viewModel
import com.forest.bss.sdk.toast.SnackBarExt
import com.forest.net.data.success
import com.v2ray.ang.R
import com.v2ray.ang.custom.activity.BuyActivity
import com.v2ray.ang.custom.activity.LoginActivity
import com.v2ray.ang.custom.activity.MainActivity
import com.v2ray.ang.custom.activity.MyOrderActivity
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.data.model.MineModel
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.custom.dialog.DownloadDialog
import com.v2ray.ang.custom.dialog.LoadingUtils
import com.v2ray.ang.custom.webSocket.ConnectUtils
import com.v2ray.ang.custom.webSocket.ConnectUtils.disConnectAppSend
import com.v2ray.ang.databinding.CustomFragmentMineBinding
import com.v2ray.ang.util.Utils
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/01 10:53

 * Desc 个人中心
 */

class MineFragment : BaseFragment() {

    private var userInfo: UserInfoBean? = null

    private var binding: CustomFragmentMineBinding? = null

    private val checkLoading: LoadingUtils by lazy {LoadingUtils()}

    private val model by lazy { viewModel<MineModel>() }

    override fun layoutId(): Int = R.layout.custom_fragment_mine
    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CustomFragmentMineBinding.inflate(inflater)
        return binding?.root
    }

    override fun isEnableViewBinding(): Boolean = true
    override fun bindingView(rootView: View?) {

        userInfo = activity?.asType<MainActivity>()?.userInfo

        binding?.mineTitle?.text = "${userInfo?.username}"

        binding?.mineCountDown?.text = "VIP会员 于${userInfo?.end_date}日到期"

        binding?.buySubmit?.setOnClickListener {
            lifecycleScope.launch {
                UserInfoDataStore.clear()
                Utils.stopVService(requireActivity())
                ConnectUtils.instance(HomeFragment.wsUrl)?.disConnectAppSend(requireActivity(), HomeFragment.selectModeId)
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding?.checkUpdate?.setOnClickListener {
            checkLoading.show(this)
            model?.checkUpdate()
        }

        binding?.order?.setOnClickListener {
            val intent = Intent(requireActivity(), MyOrderActivity::class.java)
            startActivity(intent)
        }

        binding?.mineItemFragment?.setOnClickListener {
            val intent = Intent(requireActivity(), BuyActivity::class.java)
            intent.putExtra("userInfo", userInfo)
            startActivity(intent)
        }
    }

    override fun bindViewModelObserve(rootView: View?) {
        model?.liveDataCheckUpdate?.observe(requireActivity()) {
            checkLoading.hide()
            if (it.success()) {
                it.getOrNull()?.results?.apply {
                    if (this.has_update) {
                        DownloadDialog.newInstance(this).showDialogSafely(parentFragmentManager)
                    } else {
                        SnackBarExt.show(requireActivity(), "当前已是最新版本")
                    }
                }
            }
        }
    }
}