@file:JvmName("StringExt")
package com.forest.bss.sdk.ext

import android.net.Uri
import android.text.TextUtils

fun String?.notEmpty(defaultStr: String = ""): String {
    return if (this.isNullOrEmpty()) {
        defaultStr
    } else {
        this
    }
}

fun String?.isNull(): String {
    return this ?: ""
}

fun Double?.isNull(): Double {
    return this ?: 0.0
}

fun Int?.isNull(): Int {
    return this ?: 0
}

fun Float?.isNull(): Float {
    return this ?: 0f
}

fun Long?.isNull(): Long {
    return this ?: 0
}


fun String?.noEmpty(): Boolean {
    return !TextUtils.isEmpty(this)
}

/**
 * 多变量[String]判空
 *
 *  val aaa = "aaa"
 *  var bbb: String? = ""
 *  var ccc = null
 *  notNull(aaa, bbb, ccc) {
 *
 *  }
 */
inline fun <R> notNull(vararg args: String?, block: (Boolean) -> R) {
    val str = args.filter {
        it.isNullOrEmpty()
    }
    if (str.isNotEmpty()) {
        block(false)
    } else {
        when (args.filterNotNull().size) {
            args.size -> block(true)
            else -> block(false)
        }
    }
}

fun String?.brToNewLine(): String? {
    return this?.replace("<br>", "\n")?.replace("<br/>", "\n")
}

fun String?.toValidUri(): Uri? {
    return if (this?.isValidUriString() == true) {
        this.toUri()
    } else {
        null
    }
}

/**
 * 检测该字符串转成Uri是否合法
 */
fun String.isValidUriString(): Boolean {
    return try {
        toUri().isValid()
    } catch (t: Throwable) {
        t.printStackTrace()
        false
    }
}

/**
 * 链式处理将String转成Uri
 */
fun String.toUri(): Uri {
    return Uri.parse(this)
}
