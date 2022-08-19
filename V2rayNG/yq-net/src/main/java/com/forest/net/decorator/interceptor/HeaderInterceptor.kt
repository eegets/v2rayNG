package com.forest.net.decorator.interceptor

import com.forest.net.headers.RequestHeaderProvider
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by wangkai on 2021/01/27 11:00
 *
 * Desc 请求头Header拦截器
 */

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        // 如果需要再请求服务器之前做一些操作, 则重新返回一个做过操作的的 Request 如增加 Header,
        // 不做操作则直接返回参数 request
        val builder = chain.request().newBuilder()
        val headers = RequestHeaderProvider.providerHeaders(request.url.host)
        if (headers.isNotEmpty()) {
            val keys = headers.keys
            for (headerKey in keys) {
                headers[headerKey]?.let { builder.addHeader(headerKey, it) }
            }
        } else {
            emptyMap<String, String>()
        }
        return chain.proceed(builder.build())
    }
}