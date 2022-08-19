@file:JvmName("UriUtil")

package com.forest.bss.sdk.ext

import android.net.Uri

/**
 * 验证Uri是否合法，我们认为能够获得scheme不为null且不为空，则是合法的Uri
 */
fun Uri.isValid(): Boolean {
    return try {
        //如果能执行成功，则认为合法
        this.scheme.noEmpty()
    } catch (t: Throwable) {
        t.printStackTrace()
        false
    }
}

fun Uri.appendParameter(paramName: String?, paramValue: String?): String {
    return getValueSafely {
        this.buildUpon().appendQueryParameter(paramName, paramValue).build().toString()
    } ?: this.toString()
}

fun String.appendUrlParameter(paramName: String?, paramValue: String?): String {
    return this.toValidUri()?.appendParameter(paramName, paramValue) ?: this
}

/**
 * 多参数拼接uri
 * param map 以map形式传递多个参数
 */
fun String?.appendUrlParameter(map: Map<String, String>): String? {
    val uriBuilder = this.toValidUri()?.buildUpon() ?: return null
    map.onEach {
        uriBuilder.appendQueryParameter(it.key, it.value)
    }
    return uriBuilder.build().toString()
}


