package com.forest.bss.sdk.ext

//校验手机号
fun String?.isValidPhoneNumber(): Boolean {
    return this != null && this.length == 11 && startsWith("1")
}

/**
 * 将手机号中间4位数字替换成"*"
 */
fun String?.maskPhoneMiddle4Digits(): String? {
    return if (isValidPhoneNumber())
        this?.replaceRange(3, 7, "****")
    else
        this
}

/**
 * 将第四位以后的内容替换成省略号
 */
fun String?.ellipseAfter4(): String {
    return when {
        this == null -> ""
        this.length > 4 -> this.replaceRange(4, this.length, "...")
        else -> this
    }
}

/**
 * 从结尾到`start`截取得到的内容
 */
fun String?.getLastNumber(start: Int): String {
    return when {
        this == null -> ""
        this.length > start -> this.substring(this?.length - start, this.length)
        else -> this
    }
}