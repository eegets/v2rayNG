package com.forest.net.decorator.interceptor

import com.forest.net.log.logger
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class PostInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        //打印请求起始信息
        val requestStartMsg = "--> ${request.method} ${request.url} ${request.body?.contentLength()}-byte body"
        logger {
            (requestStartMsg)
        }
        request.body?.also { requestBody ->
            val bodyString = "Content-Type: ${requestBody.contentType()} Content-Length: ${requestBody.contentLength()}"
            logger { bodyString }
        }
        val requestHeaders = request.headers
        val size = requestHeaders.size
        //打印请求头信息
        for (i in 0 until size) {
            val name = requestHeaders.name(i)
            if (!"Content-Type".equals(name, true) && !"Content-Length".equals(name, true)) {
                logger {
                    ("${name} : ${requestHeaders.value(i)}")
                }
            }
        }
        val requestBody = request.body
        val buffer = Buffer()
        //将请求体内容写入buffer中
        requestBody?.writeTo(buffer)
        println()
        //打印整个请求体中的内容（这步全靠打断点找出来的）
        logger {
            (buffer.readByteString().utf8())
        }
        logger {
            println("--> END ${request.method} (${requestBody?.contentLength()}-byte body)")
        }

        val startNs = System.nanoTime()
        //执行请求
        val response = chain.proceed(request)
        //计算耗时
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        requestBody?.also { requestBody ->
            //请求完成后打印请求信息
            val bodyInfo = "<-- ${response.code} ${response.message} ${response.request.url} (${tookMs} ms) Content-Length ${requestBody.contentLength()}"
            println(bodyInfo)
        } ?: also {
            println("No request body found!!")
        }
        val responseHeaders = response.headers
        //打印Response头中的信息
        for (i in 0 until requestHeaders.size) {
//            println("${responseHeaders.name(i)} : ${responseHeaders.value(i)}")
        }
        response.body?.also { responseBody ->
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val sourceBuffer = source.buffer()
            //打印Response中的内容（如果直接用responseBody.string()会导致流关闭而报错！java.lang.IllegalStateException: closed）
            println(sourceBuffer.clone().readString(Charset.defaultCharset()))
            println("<-- END HTTP (${sourceBuffer.size} -byte body)")
        } ?: also {
            println("<-- END HTTP No response body found!")
        }
        return response
    }
}