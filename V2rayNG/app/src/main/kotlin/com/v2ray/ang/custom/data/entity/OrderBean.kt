package com.v2ray.ang.custom.data.entity

data class OrderBean(
    val id: Int,
    val card_no: String? = null,
    val create_time: String? = null,
    val end_date: String? = null,
    val exday:String? = null,
    val status: String? = null,
)