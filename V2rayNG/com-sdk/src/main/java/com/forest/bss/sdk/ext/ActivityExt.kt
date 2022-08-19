@file:JvmName("ActivityUtil")

package com.forest.bss.sdk.ext

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.forest.bss.sdk.ApplicationLifeManager
import com.forest.bss.sdk.core.ActivityLifecycleCallbacksImpl

/**
 * 判断Activity是否已经销毁，尤其是用来提前判断Glide图片加载
 */
fun Activity.wasAlreadyDestroyed(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && isDestroyed
}

/**
 * 判断Activity是否没有销毁
 */
fun Activity.wasNotDestroyed() = !wasAlreadyDestroyed()

/**
 * 判断context(可能是Activity) 是否已经被销毁掉了
 *   * 如果是Activity，判断真实的销毁情况
 *   * 如果context是null，也认定其销毁掉，返回true
 *   * 如果不是context，返回false,不代表任何一个activity销毁掉
 */
@Suppress("RedundantIf")
fun Context?.wasActivityAlreadyDestroyed(): Boolean {
    return when {
        this is Activity -> wasAlreadyDestroyed()
        this == null -> true
        else -> false
    }
}

fun Activity.observeCallback(callback: Application.ActivityLifecycleCallbacks) {
    ApplicationLifeManager.observeActivityCallback(this, callback)
}

/**
 * 在Activity，onDestroy的时候执行runnable
 */
fun Activity.onDestroy(runnable: (Activity) -> Unit) {
    observeCallback(object : ActivityLifecycleCallbacksImpl() {
        override fun onActivityDestroyed(activity: Activity) {
            super.onActivityDestroyed(activity)
            if (this@onDestroy == activity) {
                runnable(activity)
            }
        }
    })
}

/**
 * 修改状态栏颜色，支持5.0及以上版本
 * @param colorId
 */
fun Activity.changeStatusBarColor(colorId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ActivityCompat.getColor(this, colorId)
    }
//        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //使用SystemBarTint库使4.4版本状态栏变色，需要先将状态栏设置为透明
//            transparencyBar(activity)
//            val tintManager = SystemBarTintManager(activity)
//            tintManager.setStatusBarTintEnabled(true)
//            tintManager.setStatusBarTintResource(colorId)
//        }
}