package com.v2ray.ang.custom.activity

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.act.BaseViewBindingActivity
import com.forest.bss.sdk.ext.makeGone
import com.forest.bss.sdk.ext.makeVisible
import com.forest.bss.sdk.ext.viewModel
import com.forest.bss.sdk.log.logger
import com.forest.bss.sdk.toast.ToastExt
import com.forest.net.data.success
import com.v2ray.ang.custom.adapter.AppInfoAdapter
import com.v2ray.ang.custom.data.entity.AppInfoBean
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.data.model.BuyModel
import com.v2ray.ang.custom.dataStore.AppInfoDataStore
import com.v2ray.ang.custom.dialog.LoadingUtils
import com.v2ray.ang.custom.utils.getPackageInfoByAppName
import com.v2ray.ang.databinding.CustomActivityAppInfoBinding
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/31 14:11

 * Desc 加速模式
 */


class AppInfoActivity : BaseViewBindingActivity<CustomActivityAppInfoBinding>() {

    private var packageInfos: List<String>? = null

    private val appInfos: MutableList<AppInfoBean>? = mutableListOf()

    private var adapter: AppInfoAdapter? = null

    private var checkAppInfo: MutableSet<String>? = mutableSetOf()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun initView() {
        packageInfos = intent?.getSerializableExtra("packageInfos") as List<String>

        packageInfos?.forEach { packageName ->
            val packageInfo = getPackageInfoByAppName(this, packageName)
            packageInfo?.applicationInfo?.apply {
                appInfos?.add(
                    AppInfoBean(
                        packageName = packageName,
                        appName = this.loadLabel(packageManager).toString(),
                        appIcon = this.loadIcon(packageManager)
                    )
                )
            }
        }
        lifecycleScope.launch {
            val dataAppInfos = AppInfoDataStore.suspendRead()
            if (dataAppInfos != null) {
                checkAppInfo?.clear()
                checkAppInfo?.addAll(dataAppInfos)
            }
        }
        adapter = AppInfoAdapter(this) { packageName, checked ->
            lifecycleScope.launch {
                val filterAppInfos = checkAppInfo?.filter {
                    it == packageName
                }
                if (filterAppInfos != null && filterAppInfos.isNotEmpty() && !checked) {
                    checkAppInfo?.remove(packageName)
                } else {
                    checkAppInfo?.add(packageName)
                }
                AppInfoDataStore.save(checkAppInfo)
            }
        }
        binding?.recyclerView?.adapter = adapter

        lifecycleScope.launch {
            appInfos?.forEach { appBean ->
                val selectLists: MutableSet<String>? = AppInfoDataStore.suspendRead()
                selectLists?.forEach {
                    if (appBean.packageName == it) {
                        appBean.isChecked = true
                    }
                }
            }
            if (appInfos.isNullOrEmpty()) {
                binding?.empty?.makeVisible()
                binding?.recyclerView?.makeGone()
            } else {
                binding?.recyclerView?.makeVisible()
                binding?.empty?.makeGone()
                adapter?.data = appInfos
            }
        }

        binding?.back?.setOnClickListener {
            finish()
        }
    }

    override fun viewModelObserve() {
        super.viewModelObserve()
    }

    override fun bindingLayout(): CustomActivityAppInfoBinding {
        return CustomActivityAppInfoBinding.inflate(layoutInflater)
    }
}
