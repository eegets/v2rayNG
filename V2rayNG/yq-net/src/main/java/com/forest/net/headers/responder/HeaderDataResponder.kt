package com.forest.net.headers.responder

/**
 * Created by wangkai on 2021/01/27 11:21

 * Desc TODO
 */

interface HeaderDataResponder {
    fun providerDynamicHeaders(): MutableMap<String, String>
}