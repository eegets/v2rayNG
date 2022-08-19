package com.forest.bss.sdk.util

import android.util.Log

object VersionCodeCompareUtil {
    /**
     * 将版本号转换成整数，如输入： 1.5.1 那么可以得到 1151
     * @param {String} version 版本名称，如 1.4.1
     */
    fun swapVersionToVersionCode(versionName: String?): Int {
        val versionNameArr = versionName?.split(".")
        var versionCode = 1
        if (versionNameArr != null) {
            for (s in versionNameArr) {
                versionCode *= 10
                versionCode += s.toInt()
            }
        }
        return versionCode
    }
}