package com.v2ray.ang.custom.sign

import com.forest.bss.sdk.log.logger
import com.v2ray.ang.custom.config.app_secertKey

/**
 * 通过`toSortedMap`进行ASCII码自然顺序排序
 */
fun MutableMap<String, String>.sign(): String {
    val subString = this.mapToString()
    logger {
        "Sign stringBuffer: ${subString + app_secertKey}"
    }
    return subString + app_secertKey
}

fun MutableMap<String, String>.secretSign(): String = this.sign().string2SHA256StrJava().toString()

fun MutableMap<String, String>.mapToString(): String {
    val stringBuffer = StringBuffer()
    val signParams = this.toSortedMap()  //通过`toSortedMap`进行ASCII码自然顺序排序
    signParams.map {
        stringBuffer.append("${it.key}=${it.value}")
        stringBuffer.append("&")
    }
    val string = stringBuffer.toString()
    val subString = string.substring(0, string.length - 1)
    logger {
        "mapToString params: $signParams; stringBuffer: $subString"
    }
    return subString
}