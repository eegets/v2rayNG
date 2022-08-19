package com.v2ray.ang.custom.data.entity

import java.io.Serializable

data class UserInfoBean(
    val city: String,
    val country_code: String,
    val cover_url: String,
    val device_id: String,
    val device_type: String,
    val end_date: String,
    val from: Int,
    val gender: String,
    val id: Int,
    val imei: String,
    val is_vip: String,
    val net_work: String,
    val openid: String,
    val os_band: String,
    val os_version: String,
    val phone: String,
    val pro: String,
    val province: String,
    val remark: String,
    val sim_no: String,
    val start_date: String,
    val status: String,
    val token: String,
    val username: String
): Serializable