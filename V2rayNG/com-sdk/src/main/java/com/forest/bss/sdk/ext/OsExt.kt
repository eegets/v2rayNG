package com.forest.bss.sdk.ext

import android.os.Build

/**
 * Created by wangkai on 2021/03/03 16:34

 * Desc TODO
 */

/**
 * 是否是Android 10及以上手机
 */
var isAndroidQ: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

/**
 * 是否是Adroid 7及以上手机
 */
var isAndroidNougat: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q