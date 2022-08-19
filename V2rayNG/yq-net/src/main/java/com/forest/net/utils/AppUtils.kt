package com.forest.net.utils

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build

/**
 * Created by wangkai on 2022/07/28 11:51

 * Desc TODO
 */
object AppUtils {
    /**
     * 取得应用本身的版本号
     *
     * @param context
     * @return 版本Name
     */
    fun getAppVersionName(context: Context): String? {
        val sPackageName = context.packageName
        return try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(sPackageName, 0)
            pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            ""
        }
    }
    /**
     * 取得应用VersionCode
     *
     * @param context
     * @return versionCode
     */
    fun getAppVersionCode(context: Context): Long {
        val packageName = context.packageName
        return try {
            val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
            val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
            versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0L
        }
    }
}