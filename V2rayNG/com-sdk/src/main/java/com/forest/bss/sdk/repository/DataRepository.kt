package com.forest.bss.sdk.repository

/**
 * Created by wangkai on 2021/09/14 11:24

 * Desc 知识库
 */
interface DataRepository {
    /**
     * 加载loading
     */
    fun loading()

    /**
     * 主接口响应
     */
    suspend fun mainResponse()
}