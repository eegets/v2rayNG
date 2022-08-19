package com.forest.net.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.preference.PreferenceManager
import com.forest.net.app.NetApplication

/**
 * Created by wangkai on 2021/02/04 15:45

 * Desc TODO
 */


fun Context.defaultPref() = PreferenceManager.getDefaultSharedPreferences(this)

val appContext: Context?
    get() {
        return NetApplication.instance?.applicationContext
    }


@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getShape(id: Int): Drawable {
    return resources.getDrawable(id)
}
