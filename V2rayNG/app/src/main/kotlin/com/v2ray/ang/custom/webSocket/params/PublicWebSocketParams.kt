package com.v2ray.ang.custom.webSocket.params

import com.forest.bss.sdk.log.logger
import com.v2ray.ang.custom.config.app_key
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.custom.sign.secretSign

object PublicWebSocketParams {

    suspend fun pair(): MutableMap<String, String> {
        val timestamp = System.currentTimeMillis()
        val mid = UserInfoDataStore.suspendRead("id")
        val token = UserInfoDataStore.suspendRead("token")

        return mutableMapOf(
            Pair("app_key", app_key),
            Pair("timestamp", timestamp.toString()),
            Pair("mid", if (mid.isNullOrEmpty()) "0" else mid),
            Pair("token", token),
        )
    }

    suspend fun params(mutableMap: MutableMap<String, String> = mutableMapOf()): MutableMap<String, String> {
        var map = mutableMapOf<String, String>()
        pair().let {
            it.putAll(mutableMap)
            it
        }.apply {
            this["sign"] = this.secretSign()
        }.let {
            logger {
                "PublicRequestParams params: $it"
            }
            map = it
        }
        return map
    }
}