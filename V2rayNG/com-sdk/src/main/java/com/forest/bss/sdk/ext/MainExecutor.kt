package com.forest.bss.sdk.ext

import android.os.Handler
import android.os.Looper

/**
 * Created by wangkai on 2021/03/30 18:47

 * Desc TODO
 */
object MainExecutor {
    private val HANDLER = Handler(Looper.getMainLooper())

    fun post(r: Runnable) {
        HANDLER.post(r)
    }

    fun postDelayed(r: Runnable, delayMillis: Long) {
        HANDLER.postDelayed(r, delayMillis)
    }
}