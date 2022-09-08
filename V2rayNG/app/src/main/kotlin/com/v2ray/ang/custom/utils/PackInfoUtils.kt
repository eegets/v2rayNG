package com.v2ray.ang.custom.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils

/**
 * 通过应用名获取包名
 *
 * @param packageName 应用包名
 * @return 应用信息
 */
fun getPackageInfoByAppName(context: Context, packageName: String): PackageInfo? {
    return try {
        if (TextUtils.isEmpty(packageName)) {
            return null
        }
        // 获取到包的管理者
        val packageManager: PackageManager = context.packageManager
        // 获取所有的安装程序
        val installedPackages: List<PackageInfo> = packageManager.getInstalledPackages(0)
        // 遍历获取到每个应用程序的信息
        for (packageInfo in installedPackages) {
            // 获取程序名
            val pkgName: String = packageInfo.applicationInfo.packageName
            if (packageName == pkgName) {
                return packageInfo
            }
        }
        null
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}