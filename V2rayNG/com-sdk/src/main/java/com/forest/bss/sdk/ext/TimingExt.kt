@file:kotlin.jvm.JvmName("TimingKt")
package com.forest.bss.sdk.ext

/**
 * Created by wangkai on 2021/11/10 14:44

 * Desc 执行代码块返回执行的时间
 */

/**
 * Executes the given [block] and returns elapsed time in milliseconds.
 * 执行`block代码块`以毫秒为单位返回经过的时间。
 */
inline fun measureTimeMillis(block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    return System.currentTimeMillis() - start
}

/**
 * Executes the given [block] and returns elapsed time in nanoseconds.
 * 执行`block代码块`以纳秒为单位返回经过的时间。
 */
inline fun measureNanoTime(block: () -> Unit): Long {
    val start = System.nanoTime()
    block()
    return System.nanoTime() - start
}