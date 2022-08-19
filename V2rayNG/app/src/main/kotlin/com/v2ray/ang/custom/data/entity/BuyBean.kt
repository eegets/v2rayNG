package com.v2ray.ang.custom.data.entity

import java.io.Serializable

data class BuyBean(
    val code: Int,
    val msg: String,
    val results: BuyResults
): Serializable
data class BuyResults(
        val random: String
): Serializable