package com.forest.net.module

import com.forest.net.app.App
import com.forest.net.decorator.*
import com.forest.net.decorator.interceptor.PostInterceptor
import com.forest.net.gson.GsonConverterFactory
import com.forest.net.utils.OK_HTTP_CALL_TIME
import com.forest.net.utils.OK_HTTP_CONNECT_TIME
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by wangkai on 2021/01/26 15:16
 * Desc 依赖注入基础网络模块
 */


@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    fun providesBaseUrl(): String {
        return if (App.baseUrl.isNullOrEmpty()) {
            "https://www.github.com"
        } else {
            App.baseUrl.let {
                return it.toString()
            }
        }
    }

    @Provides
    fun providesGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun providesOkHttpClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient {

        return builder
                .connectTimeout(OK_HTTP_CALL_TIME, TimeUnit.SECONDS)
                .readTimeout(OK_HTTP_CONNECT_TIME, TimeUnit.SECONDS)
                .writeTimeout(OK_HTTP_CONNECT_TIME, TimeUnit.SECONDS)
                .callTimeout(OK_HTTP_CALL_TIME, TimeUnit.MINUTES)
                .appReplaceBaseUrl()
                .applyMock()
                .applyLogging()
                .applySafeGuard()
                .applyHeader()
                .addInterceptor(PostInterceptor())
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient, baseUrl: String, gson: Gson): Retrofit {

        return builder
                .baseUrl(baseUrl)
                .client(okHttpClient.newBuilder().build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())//user Rxjava
                .addConverterFactory(GsonConverterFactory.create(gson)) //user Gson
                .build()
    }
}