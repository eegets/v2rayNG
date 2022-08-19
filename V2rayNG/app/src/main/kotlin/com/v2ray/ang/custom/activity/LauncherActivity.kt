package com.v2ray.ang.custom.activity

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.act.BaseViewBindingActivity
import com.forest.bss.sdk.log.logger
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.databinding.CustomActivityLauncherBinding
import kotlinx.coroutines.launch


class LauncherActivity : BaseViewBindingActivity<CustomActivityLauncherBinding>() {
    override fun initView() {
        lifecycleScope.launch {
            val read = UserInfoDataStore.suspendRead("token")
            if (read.isNotEmpty()) {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }

    override fun bindingLayout(): CustomActivityLauncherBinding {
        return CustomActivityLauncherBinding.inflate(layoutInflater)
    }
}