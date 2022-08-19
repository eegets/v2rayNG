package com.forest.bss.sdk.ext

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by wangkai on 2021/02/03 11:06

 * Desc TODO
 */


fun dp2px(dipValue: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, Resources.getSystem().displayMetrics).toInt()
}

fun px2dp(dipValue: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, dipValue, Resources.getSystem().displayMetrics).toInt()
}

fun sp2px(spValue: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, Resources.getSystem().displayMetrics).toInt()
}
