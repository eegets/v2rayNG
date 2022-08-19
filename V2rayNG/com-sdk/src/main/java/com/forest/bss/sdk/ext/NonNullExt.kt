package com.forest.bss.sdk.ext

/**
 * Created by wangkai on 2021/01/28 14:20

 * Desc 非空处理
 */


fun <T> T?.nonNull(value: T): T {
    return this ?: value
}


/**
 * 判断first均为非null时执行consumer调用，避免变量导致过多?.let的麻烦
 */
fun <F> nonNull(first: F?, consumer: (F) -> Unit) {
    nonNull(first, Unit) {
        f, _ ->
        consumer.invoke(f)
    }
}

/**
 * 判断first和second均为非null时执行consumer调用，避免变量导致过多?.let的麻烦
 */
fun <F, S> nonNull(first: F?, second: S?, consumer: (F, S) -> Unit) {
    nonNull(first, second, Unit) {
        f, s, _ ->
        consumer.invoke(f, s)
    }
}

/**
 *  * 判断first,second,third 均为非null时执行consumer调用，避免变量导致过多?.let的麻烦
 */
fun <F, S, T> nonNull(first: F?, second: S?, third: T?, consumer: (F, S, T) -> Unit) {
    if (first != null && second != null && third != null) {
        consumer.invoke(first, second, third)
    }
}