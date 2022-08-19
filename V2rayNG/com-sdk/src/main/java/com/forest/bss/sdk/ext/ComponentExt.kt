package com.forest.bss.sdk.ext

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Process
import com.forest.bss.sdk.BuildConfig

/**
 * Created by wangkai on 2021/07/20 18:35

 * Desc TODO
 */

/**
 * 打开应用
 * [context] 上下文
 * [packageName] 包名
 * [className] 包名+名称
 */
fun openComponentApp(context: Context?, packageName: String?, className: String?) {
    if (context == null) {
        return
    }
    if (packageName == null) {
        if (BuildConfig.DEBUG) {
            throw NullPointerException("package name is null")
        }
        return
    }
    if (className == null) {
        if (BuildConfig.DEBUG) {
            throw NullPointerException("class name is null")
        }
        return
    }
    Intent().apply {
        this.action = Intent.ACTION_MAIN
        this.addCategory(Intent.CATEGORY_LAUNCHER)
        this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.component = ComponentName(packageName, className)
    }.also {
        context.startActivity(it)
    }
}


/**
 * 判断是否安装包名对应的应用
 * [context] 上下文
 * [packageName] 包名
 */
fun isPackageVerification(context: Context?, packageName: String?): Boolean {
    if (context == null) {
        return false
    }
    if (packageName == null) {
        if (BuildConfig.DEBUG) {
            throw NullPointerException("package name is null")
        }
        return false
    }
    val packageManager: PackageManager = context.packageManager // 获取packagemanager
    val pinfo: List<PackageInfo> = packageManager.getInstalledPackages(0) // 获取所有已安装程序的包信息
    if (pinfo != null) {
        for (i in pinfo.indices) {
            val pn: String = pinfo[i].packageName
            if (pn == packageName) {
                return true
            }
        }
    }
    return false
}

/**
 * 判断对应包名的应用是否启动
 * [context] 上下文
 * [packageName] 包名
 */
fun isPackageActive(context: Context?, packageName: String?): Boolean {
    if (context == null) {
        return false
    }
    if (packageName == null) {
        if (BuildConfig.DEBUG) {
            throw NullPointerException("package name is null")
        }
        return false
    }
    val actManager: ActivityManager? = context.getSystemService(Context.ACTIVITY_SERVICE).asType() // 获取actManager
    val runningLists: List<ActivityManager.RunningAppProcessInfo>? = actManager?.runningAppProcesses // 获取正在运行的进程
    runningLists?.forEach {
        if (it.processName == packageName) {
            return true
        }
    }
    return false
}

/**
 * 杀死APP后重启
 * [context] 上下文
 * [millisecond] 毫秒，默认两秒
 */
fun reBootApp(context: Context?, millisecond: Long = 2000) {
    if (isPackageActive(context, context?.packageName)) {
        val intent: Intent? = context?.packageManager?.getLaunchIntentForPackage(context.packageName)
        val restartIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        // 设置杀死应用后2秒重启
        val mgr = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr[AlarmManager.RTC, System.currentTimeMillis() + millisecond] = restartIntent
        // 重启应用
        Process.killProcess(Process.myPid())
    }
}