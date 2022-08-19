package com.forest.bss.sdk.ext

import com.forest.bss.sdk.log.logger

/**
 * Created by wangkai on 2021/01/27 13:52

 * Desc 安全的执行某一个操作或获取某个值
 */

/**
 * 安全的获取值的信息，其过程中发生异常会自动处理，返回null
 */
inline fun <T> getValueSafely(getValueAction: () -> T?): T? {
    return try {
        getValueAction()
    } catch(t: Throwable) {
        logger {
            "getValueSafely failed message=${t.localizedMessage};${t.message}"
        }
        null
    }
}

/**
 * 安全执行block，捕获其中可能发生的异常
 * 如果没有异常，返回true，否则返回false
 */
inline fun safeRun(block: () -> Unit): Boolean {
    var safeRan = false
    try {
        block()
        safeRan = true
    } catch (t: Throwable) {
        t.printStackTrace()
    }
    return safeRan
}
/**
 * 安全执行某个操作，可以接收返回值
 */
@JvmOverloads
inline fun <T> safeRun(fallback: T? = null, block: () -> T?): T? {
    return try {
        block()
    } catch (t: Throwable) {
        t.printStackTrace()
        fallback
    }
}