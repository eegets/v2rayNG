package com.forest.bss.sdk.ext

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by wangkai on 2021/01/27 13:59

 * Desc TODO
 */


fun <T: ViewModel> FragmentActivity.viewModel(clazz: Class<T>): T {
    return ViewModelProvider(this).get(clazz)
}

fun Context.asFragmentActivity(): FragmentActivity? {
    return asType<FragmentActivity>()
}

