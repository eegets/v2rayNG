@file:JvmName("DebugUtil")
package com.forest.bss.sdk.ext

import com.forest.bss.sdk.BuildConfig

/**
 * 只在Debug配置下执行runnable
 */
inline fun debugRun(runnable: () -> Unit) {
    if (BuildConfig.DEBUG) {
        runnable()
    }
}

