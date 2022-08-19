package com.forest.bss.sdk.ext

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by wangkai on 2021/05/11 16:01

 * Desc TODO
 */


fun actionViewIntent(): Intent {
    return Intent(Intent.ACTION_VIEW)
}

fun Intent.plusData(uri: Uri?): Intent {
    return this.apply {
        data = uri
    }
}

/**
 * 链式处理为Intent实例增加FLAG_ACTIVITY_NEW_TASK
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Intent.asNewTask(): Intent {
    return this.apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}

/**
 * 链式处理启动Activity
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Intent.firedBy(context: Context) {
    context.startActivity(this)
}