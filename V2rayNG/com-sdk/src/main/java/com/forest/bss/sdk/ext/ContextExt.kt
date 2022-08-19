@file:JvmName("ContextUtil")

package com.forest.bss.sdk.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import com.forest.bss.sdk.delegate.AppDelegate
import com.forest.net.app.App

/**
 * Created by wangkai on 2021/02/04 15:45

 * Desc TODO
 */


fun Context.defaultPref() = PreferenceManager.getDefaultSharedPreferences(this)

val appContext: Context
    get() {
        return AppDelegate.getAppBaseContext()
                ?: App.application().nonNull(AppDelegate.getAppBaseContext())
    }


@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getShape(id: Int): Drawable {
    return resources.getDrawable(id)
}

fun Context.startBrowser(url: String?): Boolean {
    return safeRun {
        require(url != null) {
            "Context.startBrowser url is null"
        }
        actionViewIntent().plusData(url.toValidUri()).asNewTask().firedBy(this)
    }
}
