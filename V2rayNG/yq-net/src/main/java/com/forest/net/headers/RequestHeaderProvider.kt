package com.forest.net.headers

import com.forest.net.headers.responder.GeneralHeaderResponder

/**
 * Created by wangkai on 2021/01/27 11:10

 * Desc 请求Header数据提供者
 * 如果想要为某个域名增加特定的header，建议创建HeaderDataResponder实例，并加入到headerDataResponders中
 */

object RequestHeaderProvider {

    fun providerHeaders(host: String?): MutableMap<String, String> {
        if (host.isNullOrEmpty()) {
            return mutableMapOf()
        }
        return GeneralHeaderResponder.providerDynamicHeaders()

    }
}