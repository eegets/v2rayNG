package com.forest.bss.sdk.ext

/**
 * Created by wangkai on 2021/05/21 14:24

 * Desc TODO
 */

/**
 * 仅在Boolean?实例为true的时候执行block
 */
inline fun Boolean?.runWhenTrue(block: () -> Unit) {
    if (this == true) {
        block()
    }
}

