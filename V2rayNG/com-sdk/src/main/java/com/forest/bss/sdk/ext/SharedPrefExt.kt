package com.forest.bss.sdk.ext

import android.content.Context
import android.content.SharedPreferences

/**
 * 更便捷的方式获取默认的SharedPref实例
 */
val defaultSharedPref: SharedPreferences? by lazy {
    appContext.defaultPref()
}

@JvmOverloads
fun Context.sharedPref(sharedPrefName: String, mode: Int = Context.MODE_PRIVATE): SharedPreferences {
    return getSharedPreferences(sharedPrefName, mode)
}

inline fun SharedPreferences.modify(modification: SharedPreferences.Editor.() -> Unit) {
    edit().let {
        modification(it)
        it.apply()
    }
}
inline fun SharedPreferences.modifyCommit(modification: SharedPreferences.Editor.() -> Unit) {
    edit().let {
        modification(it)
        it.commit()
    }
}
fun SharedPreferences.putKeyValue(key: String, value: Boolean) {
    modify {
        putBoolean(key, value)
    }
}

fun SharedPreferences.putKeyValue(key: String, value: Int) {
    modify {
        putInt(key, value)
    }
}

fun SharedPreferences.putKeyValue(key: String, value: Float) {
    modify {
        putFloat(key, value)
    }
}

fun SharedPreferences.putKeyValue(key: String, value: Long) {
    modify {
        putLong(key, value)
    }
}

fun SharedPreferences.putKeyValue(key: String, value: String) {
    modify {
        putString(key, value)
    }
}

fun SharedPreferences.putKeyValue(key: String, value: Set<String>?) {
    modify {
        putStringSet(key, value)
    }
}

fun SharedPreferences.putKeyValueCommit(key: String, value: Set<String>?) {
    modifyCommit {
        putStringSet(key, value)
    }
}

fun SharedPreferences.remove(key: String) {
    modify {
        this.remove(key)
    }
}

fun SharedPreferences.clearAll() {
    modify {
        this.clear().commit()
    }
}