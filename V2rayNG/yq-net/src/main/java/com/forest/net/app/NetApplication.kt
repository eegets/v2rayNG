package com.forest.net.app

import android.app.Application
import com.forest.net.log.isLogDebug
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by wangkai on 2021/01/26 15:18
 * Desc Hilt依赖注入入口
 */

@HiltAndroidApp
open class NetApplication : Application() {
    companion object{
        var instance: NetApplication? = null
    }

    /**
     * 注解得到okHttpClient实例
     */
    @Inject
    lateinit var okHttpClientBuilder: OkHttpClient.Builder

    /**
     * 注解得到retrofit实例
     */
    @Inject
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        instance = this
        isLogDebug(true)

    }
}