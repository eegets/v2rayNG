package com.forest.bss.sdk.ext

/**
 * Created by wangkai on 2021/02/02 14:19

 * Desc TODO
 */

fun Any?.simpleName(): String? {
    return when {
        this == null -> null
        else -> {
            getValueSafely {
                this::class.java.simpleName
            }
        }
    }
}

