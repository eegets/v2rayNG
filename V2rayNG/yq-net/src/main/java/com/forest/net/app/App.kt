package com.forest.net.app

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Created by wangkai on 2021/01/26 17:45
 * Desc 基础对外提供工具类
 */

object App {
    @kotlin.jvm.JvmField
    var baseUrl : String? = null

    /**
     * 对外暴露的Application
     */
    fun application() : Application?{
        return NetApplication.instance
    }

    /**
     * 对外暴露的OkHttpClient实例
     */
    fun okHttpClient(): OkHttpClient.Builder {
        return (application() as NetApplication).okHttpClientBuilder
    }

    /**
     * 对外暴露的retrofit实例
     */
    fun retrofit(): Retrofit {
        return (application() as NetApplication).retrofit
    }

    fun <T> obtainRetrofitService(clazz: Class<T>): T {
        return retrofit().create(clazz)
    }
}