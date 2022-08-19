package com.forest.bss.sdk.dao

import android.content.Context
import android.content.SharedPreferences
import com.forest.bss.sdk.ext.appContext
import com.forest.bss.sdk.ext.putKeyValue
import com.forest.bss.sdk.ext.sharedPref

/**
 * Created by wangkai on 2021/04/26 11:43

 * Desc
 */

class RequestApiSharedPref(val context: Context = appContext) {

    private val pref: SharedPreferences by lazy {
        context.sharedPref(PREF_IS_FIRST_LOCATION)
    }

    fun put(str: String) {
        pref.putKeyValue(KEY_IS_FIRST_LOCATION, str)
    }

    fun get(): String? {
        return pref.getString(KEY_IS_FIRST_LOCATION, "")
    }

    companion object {
        /**
         *
         */
        private const val KEY_IS_FIRST_LOCATION = "keyApi"

        /**
         *
         */
        private const val PREF_IS_FIRST_LOCATION = "requestApiPref"
    }
}


