package com.forest.net.decorator

import com.forest.net.decorator.interceptor.*
import okhttp3.OkHttpClient

/**
 * Created by wangkai on 2021/01/27 10:27
 * Desc OkHttp拦截控制器
 */

/**
 * logger日志拦截器
 */
fun OkHttpClient.Builder.applyLogging(): OkHttpClient.Builder {
    return addInterceptor(LoggingInterceptor())
}

/**
 * 安全请求拦截器
 */
fun OkHttpClient.Builder.applySafeGuard(): OkHttpClient.Builder {
    return addInterceptor(SafeGuardInterceptor())
}

/**
 * header头部拦截器
 */
fun OkHttpClient.Builder.applyHeader(): OkHttpClient.Builder {
    return addInterceptor(HeaderInterceptor())
}

/**
 *  Mock模拟请求拦截器
 */
fun OkHttpClient.Builder.applyMock(): OkHttpClient.Builder {
    return addInterceptor(MockInterceptor())
}

fun OkHttpClient.Builder.appReplaceBaseUrl(): OkHttpClient.Builder {
    return addInterceptor(ReplaceBaseUrlInterceptor())
}