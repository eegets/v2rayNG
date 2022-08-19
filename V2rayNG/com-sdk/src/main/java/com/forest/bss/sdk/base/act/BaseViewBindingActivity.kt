package com.forest.bss.sdk.base.act

import android.view.View
import androidx.viewbinding.ViewBinding

/**
 * Created by wangkai on 2021/10/20 13:45

 * Desc 更方便的ViewBinding封装类
 */
open abstract class BaseViewBindingActivity<B : ViewBinding> : BaseActivity() {

    abstract fun bindingLayout(): B

    var binding: B? = null

    override fun layoutId(): Int = 0

    override fun isEnableViewBinding(): Boolean = true

    override fun viewBinding(): View? {
        binding = bindingLayout()
       return binding?.root
    }
}