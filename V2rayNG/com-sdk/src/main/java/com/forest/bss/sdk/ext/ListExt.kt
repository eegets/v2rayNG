package com.forest.bss.sdk.ext

/**
 * Created by wangkai on 2021/03/23 10:52

 * Desc TODO
 */

fun <T> Collection<T>?.isNotNullNotEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}