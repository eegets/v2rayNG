package com.forest.net.decorator.interceptor

import com.forest.net.BuildConfig
import com.forest.net.log.logger
import okhttp3.Interceptor
import okhttp3.Response


/**
 * Created by wangkai on 2021/01/26 15:00
 *
 * Desc 日志拦截器
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return if (BuildConfig.DEBUG) {
            response.also {
                val inputStream = readContent(response)
                logger {
                    " \n LoggingRequestInfo >>>>>>>>>>>>>>>>>>" +
                            "\n method:   ${request.method}; " +
                            "\n isHttps:  ${request.isHttps}; " +
                            "\n url:      ${request.url}; " +
                            "\n code:     ${response.code}" +
                            "\n msg:      ${response.message}" +
                            "\n json:     $inputStream" +
                            "\n " +
                            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"
                }
            }
        } else {
            response
        }
    }


    private fun readContent(response: Response?): String? {
        if (response == null) {
            return ""
        }
        try {
            return response.peekBody(Long.MAX_VALUE).string()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}