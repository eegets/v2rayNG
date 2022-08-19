package com.forest.bss.sdk.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

/**
 * Created by wangkai on 2021/05/07 13:46

 * Desc 时间格式化
 */

/**
 * 时间格式化
 * [pattern] 格式化样式
 */
@SuppressLint("SimpleDateFormat")
fun dateFormat(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return dateFormat(pattern, System.currentTimeMillis())
}

/**
 * 时间格式化
 * [pattern] 格式化样式
 * [millisecond] 毫秒
 */
@SuppressLint("SimpleDateFormat")
fun dateFormat(pattern: String = "yyyy-MM-dd HH:mm:ss", millisecond: Long): String {
    return SimpleDateFormat(pattern).format(millisecond)
}


