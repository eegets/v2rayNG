package com.v2ray.ang.custom.data.api

import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.data.entity.VMessBean
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
    suspend fun login(@Query("username") userName: String, @Query("password") passWord: String): BaseResponse<UserInfoBean>

    /**
     * 登陆
     */
    @POST("/index/get_infos")
    suspend fun getInfo(@QueryMap map: MutableMap<String, String>): BaseResponse<HomeBean>

    /**
     * 一键加速验证
     */
    @POST("accelerate_validate/check_auth")
    suspend fun checkAuth(@QueryMap map: MutableMap<String, String>, @Query("smode") sMode: Int): BaseResponse<VMessBean>

}