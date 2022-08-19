package com.forest.net.decorator.interceptor

import com.forest.net.BuildConfig
import com.forest.net.log.logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by wangkai on 2021/02/05 13:36

 * Desc Mock数据拦截器
 */

class MockInterceptor : Interceptor  {
    companion object {
        private const val HEADER_NAME_MOCK_URL = "CooMockUrl"
        const val HEADER_MOCK_URL_PREFIX = "$HEADER_NAME_MOCK_URL: "
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return if (BuildConfig.DEBUG) {
            val realRequest = getMockRequest(chain) ?: chain.request()
            removeMockUrlHeader(realRequest).let {
                chain.proceed(it)
            }
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun getMockRequest(chain: Interceptor.Chain): Request? {
        val originalRequest = chain.request()
        val mockUrl = getMockUrlFromHeader(originalRequest)
        return if (mockUrl.isNullOrEmpty()) {
            null
        } else {
            replaceRequest(originalRequest, mockUrl)
        }
    }

    private fun replaceRequest(request: Request, url: String): Request? {
        logger {
            "MockInterceptor getMockRequest mockUrl=$url"
        }
        return request.newBuilder().url(url).build()
    }


    private fun getMockUrlFromHeader(request: Request): String? {
        return request.header(HEADER_NAME_MOCK_URL)
    }

    private fun removeMockUrlHeader(request: Request): Request {
        return request.newBuilder().removeHeader(HEADER_NAME_MOCK_URL).build()
    }

}