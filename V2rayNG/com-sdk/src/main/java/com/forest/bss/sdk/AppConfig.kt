
package com.forest.bss.sdk

import android.content.Context
import com.forest.bss.sdk.dao.RequestApiSharedPref
import com.forest.bss.sdk.ext.appContext
import com.forest.bss.sdk.ext.nonNull
import com.forest.bss.sdk.log.logger

/**
 * Desc
 *
 * Created by 王凯 on 2/23/21 10:19 AM
 */
object EnvironmentConfig {

    /**
     * dev-开发环境
     * test-测试环境
     * perView-预发生产环境
     * online-正式环境
     * newPreView-预发新生产环境
     */
    val environment: String = BuildConfig.ENVIRONMENT_CONFIG

    /**
     * 开发环境
     */
    private const val baseUrlDev = "http://mobile.moogo.club/"

    /**
     * 测试环境
     */
    private const val baseUrlPreRelease = "http://mobile.moogo.club/"

    /**
     * 正式环境，线上版本
     */
    private const val baseUrlOnlineRelease = "http://mobile.moogo.club/"


    fun getBaseUrl(context: Context = appContext): String {

        if (RequestApiSharedPref(context).get().isNullOrEmpty()) {
            logger {
                "AppConfig RequestApiSharedPref is Null"
            }
            return when (environment) {
                BuildConfig.API_DEV -> baseUrlDev
                BuildConfig.API_RELEASE -> baseUrlPreRelease
                BuildConfig.API_ONLINE -> baseUrlOnlineRelease
                else -> baseUrlOnlineRelease
            }
        } else {
            val apiPref = RequestApiSharedPref(context).get()?.nonNull("perView").toString()
            logger {
                "AppConfig RequestApiSharedPref is Not null apiPref:$apiPref"
            }
            return when (apiPref) {
                BuildConfig.API_DEV -> baseUrlDev
                BuildConfig.API_RELEASE -> baseUrlPreRelease
                BuildConfig.API_ONLINE -> baseUrlOnlineRelease
                else -> baseUrlOnlineRelease
            }
        }
    }
}