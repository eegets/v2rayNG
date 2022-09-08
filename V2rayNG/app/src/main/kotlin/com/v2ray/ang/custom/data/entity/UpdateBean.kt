package com.v2ray.ang.custom.data.entity

import java.io.Serializable

data class UpdateBean(
    val download_url: String,
    val has_update: Boolean,
    val update_detail: String,
    val update_type: String,
    val version_code: Int,
    val version_name: String
): Serializable