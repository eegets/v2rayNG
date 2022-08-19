package com.forest.net.decorator.interceptor

import com.forest.net.app.App
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


/**
 * Created by wangkai on 2021/11/23 16:30

 * Desc TODO
 */
class ReplaceBaseUrlInterceptor : Interceptor {

    companion object {
        private const val HEADER_REPLACE_URL_KEY = "ReplaceUrl"
        private const val HEADER_REPLACE_URL_VALUE = "replaceUrl"
        const val HEADER_URL_PREFIX = "$HEADER_REPLACE_URL_KEY:$HEADER_REPLACE_URL_VALUE"


        /**
         * 设备绑定测试环境
         */
        private const val baseUrlDevicePreRelease = "https://ams.yqslmall.com/"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request() //获取原始的request


        originalRequest.body
        val builder: Request.Builder = originalRequest.newBuilder()
        val urlNameList = originalRequest.headers(HEADER_REPLACE_URL_KEY)
        return if (urlNameList.isNotEmpty()) {
            //删除原有配置中的值,就是namesAndValues集合里的值
            builder.removeHeader(HEADER_URL_PREFIX)
            //获取头信息中配置的value,如：$HEADER_REPLACE_URL_VALUE
            val urlName = urlNameList[0]
            var baseUrl = App.baseUrl
            if (baseUrl.isNullOrEmpty()) {
                baseUrl = ""
            }
            var httpUrl: HttpUrl = if (HEADER_REPLACE_URL_VALUE == urlName) {
                baseUrlDevicePreRelease.toHttpUrl()
            } else {
                baseUrl.toHttpUrl()
            }
            val newRequest = replaceRequest(originalRequest, httpUrl)
            chain.proceed(newRequest)
        } else {
            chain.proceed(originalRequest)
        }
    }

    private fun replaceRequest(request: Request, httpUrl: HttpUrl): Request {
        //重建新的HttpUrl，需要重新设置的url部分
        val newHttpUrl: HttpUrl = request.url.newBuilder()
                .scheme(httpUrl.scheme) //http协议如：http或者https
                .host(httpUrl.host) //主机地址
                .port(httpUrl.port) //端口
                .build()
        return request.newBuilder().post(request.body!!).url(newHttpUrl).build()
    }
}