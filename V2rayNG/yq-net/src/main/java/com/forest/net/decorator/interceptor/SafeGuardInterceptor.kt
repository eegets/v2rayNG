package com.forest.net.decorator.interceptor

import com.forest.net.BuildConfig
import com.forest.net.log.logger
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by wangkai on 2021/01/26 15:00
 *
 * Desc 对于Interceptor的intercept中可能出现的Throwable包裹成IOExceptionWrapper，转成网络请求失败，而不是应用崩溃
 */
class SafeGuardInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!BuildConfig.DEBUG) {
//            try {
//                return chain.proceed(chain.request())
//            } catch (t: Throwable) {
//                t.printStackTrace()
//                logOnRequestFailed(chain, t)
//                throw IOExceptionWrapper("SafeGuarded when requesting ${chain.request().url}", t)
//            }
//        }
        return chain.proceed(chain.request())
    }

    private fun logOnRequestFailed(chain: Interceptor.Chain, throwable: Throwable) {
        logger {
            "logOnRequestFailed request=${chain.request()};error=${throwable.message}"
        }
    }
}

/**
 * 将chain.proceed处理中发生的Throwable包装成IOExceptionWrapper
 */
class IOExceptionWrapper(message: String?, cause: Throwable?) : IOException(message, cause)