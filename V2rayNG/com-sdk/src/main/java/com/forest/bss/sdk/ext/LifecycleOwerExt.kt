package com.forest.bss.sdk.ext

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

/**
 * Created by wangkai on 2021/11/15 15:04

 * Desc LifecycleOwner
 */

/**
 * Context 用于返回LifecycleOwner
 */
fun Context?.getLifecycleOwner(): LifecycleOwner? {
    return when (this) {
        is LifecycleOwner -> {
            return this
        }
        else -> {
            null
        }
    }
}



/**
 * Fragment 用于返回LifecycleOwner
 */
fun Fragment?.getLifecycleOwner(): LifecycleOwner? {
    return when (this) {
        is LifecycleOwner -> {
            return this
        }
        else -> {
            null
        }
    }
}