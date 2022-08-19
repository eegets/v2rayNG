package com.v2ray.ang.custom.sign

import okhttp3.internal.and
import java.security.MessageDigest


/**
 * 利用java原生的摘要实现SHA256加密
 * @param str 加密后的报文
 * @return
 */
fun String?.string2SHA256StrJava(): String? {
    this ?: null
    val messageDigest = MessageDigest.getInstance("SHA-256")
    messageDigest.update(this?.toByteArray(charset("UTF-8")))
    return byte2Hex(messageDigest.digest())
}

/**
 * 将byte转为16进制
 * @param bytes
 * @return
 */
private fun byte2Hex(bytes: ByteArray): String? {
    val stringBuffer = StringBuffer()
    var temp: String? = null
    for (i in bytes.indices) {
        temp = Integer.toHexString(bytes[i] and 0xFF)
        if (temp.length == 1) {
            //1得到一位的进行补0操作
            stringBuffer.append("0")
        }
        stringBuffer.append(temp)
    }
    return stringBuffer.toString()
}