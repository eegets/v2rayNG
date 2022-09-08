package com.v2ray.ang.custom.data.entity

import java.io.Serializable

data class HomeBean(
    val alert_text: Boolean,
    val auth_apps: List<String>,
    val hosts: List<Host>,
    val is_alert: String,
    val user_info: UserInfoBean,
    val web_domain: String,
    val exp_day: String? = null,
    val ws_url: String,
): Serializable {
    data class Host(
        val id: Int,
        val host: String,
        val title: String
    ): Serializable
}
