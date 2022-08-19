package com.v2ray.ang.custom.data.entity

import java.io.Serializable

data class VMessBean(
    val has_auth: Boolean,
    val id: Int,
    val title: Int,
    val vmess_string: String
): Serializable