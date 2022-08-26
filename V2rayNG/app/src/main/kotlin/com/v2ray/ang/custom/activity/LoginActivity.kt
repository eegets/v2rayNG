package com.v2ray.ang.custom.activity

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.act.BaseViewBindingActivity
import com.forest.bss.sdk.ext.viewModel
import com.forest.bss.sdk.toast.ToastExt
import com.forest.net.data.success
import com.v2ray.ang.custom.data.model.LoginModel
import com.v2ray.ang.custom.sign.PublicRequestParams
import com.v2ray.ang.databinding.CustomActivityLoginBinding
import kotlinx.coroutines.launch


/**
 * Created by wangkai on 2021/05/17 16:43

 * Desc TODO
 */

class LoginActivity : BaseViewBindingActivity<CustomActivityLoginBinding>() {

    private val model: LoginModel by lazy {
        viewModel(LoginModel::class.java)
    }

    override fun initView() {
        lifecycleScope.launch {
            PublicRequestParams.params()
        }

        binding?.submitLogin?.setOnClickListener {
            val userNameString = binding?.userName?.text.toString()
            val passWordString = binding?.passWord?.text.toString()
            when {
                userNameString.isEmpty() -> {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_LONG).show()
                }
                passWordString.isEmpty() -> {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_LONG).show()
                }
                else -> {
                    model.login(binding?.userName?.text.toString(), binding?.passWord?.text.toString())
                }
            }
        }
    }

    override fun viewModelObserve() {
        super.viewModelObserve()
        model.liveDataLogin.observe(this) {
            if (it.success()) {
                //跳转到首页
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("loginBean", it.getOrNull()?.results)
                startActivity(intent)
                finish()
            } else {
                ToastExt.show(it?.getOrNull()?.msg ?: "")
            }
        }
    }
    override fun bindingLayout(): CustomActivityLoginBinding {
        return CustomActivityLoginBinding.inflate(layoutInflater)
    }

}