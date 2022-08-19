package com.forest.bss.sdk.ext

import java.util.*

/**
 * Created by wangkai on 2021/03/27 15:10

 * Desc 日历
 */

fun date(millisecond: Long): Date {
    return Date(millisecond)
}

fun formatDate(millisecond: Long) : Calendar {
    safeRun {
        val calendar = Calendar.getInstance()
        calendar.time = date(millisecond)
        return calendar
    }
    return Calendar.getInstance()
}