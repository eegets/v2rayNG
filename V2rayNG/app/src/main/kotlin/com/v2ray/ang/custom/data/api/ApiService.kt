package com.v2ray.ang.custom.data.api

import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.entity.*
import retrofit2.http.*

/**
 * Created by wangkai on 2022/01/06 13:53

 * Desc TODO
 */
interface ApiService {

    /**
     * 登陆
     */
    @GET("/u/login")
    suspend fun login(@QueryMap map: MutableMap<String, String>): BaseResponse<UserInfoBean>

    /**
     * 登陆
     */
    @POST("/index/get_infos")
    suspend fun getInfo(@QueryMap map: MutableMap<String, String>): BaseResponse<HomeBean>

    /**
     * 一键加速验证
     */
    @POST("accelerate_validate/check_auth")
    suspend fun checkAuth(@QueryMap map: MutableMap<String, String>): BaseResponse<String>

    /**
     *  续费
     */
    @POST("pay/activate")
    suspend fun activatePayment(@QueryMap map: MutableMap<String, String>): BaseResponse<ErrorResult>

    /**
     *  检查更新
     */
    @POST("version/get_update")
    suspend fun checkUpdate(@QueryMap map: MutableMap<String, String>): BaseResponse<UpdateBean>

    /**
     *  我的订单
     */
    @POST("pay/get_orders")
    suspend fun queryOrderLists(@QueryMap map: MutableMap<String, String>): BaseResponse<MutableList<OrderBean>>

}