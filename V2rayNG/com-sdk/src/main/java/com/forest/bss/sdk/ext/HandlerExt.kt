package com.forest.bss.sdk.ext

import android.os.Handler

/**
 * 安全执行post任务
 */
fun Handler.safePost(runnable: () -> Unit) {
    post {
        safeRun {
            runnable()
        }
    }
}

