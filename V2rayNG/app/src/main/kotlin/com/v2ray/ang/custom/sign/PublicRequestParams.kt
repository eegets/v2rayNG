package com.v2ray.ang.custom.sign

import com.blankj.utilcode.util.*
import com.forest.bss.sdk.log.logger
import com.v2ray.ang.custom.dataStore.UserInfoDataStore

object PublicRequestParams {

    val app_secertKey = "1f1287b0ddb20c2b1ab7ad41380464358a79084d"

    suspend fun pair(): MutableMap<String, String> {
        val app_key = "6cc6fb7766656e6d73d5ddf2beac81d5"
        val timestamp = System.currentTimeMillis()
        val sign = ""
        val version = AppUtils.getAppVersionCode()
        val mid = UserInfoDataStore.suspendRead("id")
        val token = UserInfoDataStore.suspendRead("token")
        val device_id = DeviceUtils.getUniqueDeviceId()
        val device_type = "Android"
        val os_version = DeviceUtils.getSDKVersionName()
        val os_band = PhoneUtils.getPhoneType()
        val sim_no = PhoneUtils.getSimOperatorByMnc()
        val imei = "defaultImei"
        val phone = "defaultPhone"
        val net_work = NetworkUtils.getNetworkType()
        val pro = ScreenUtils.getScreenDensity()

        return  mutableMapOf(
            Pair("app_key", app_key),
            Pair("timestamp", timestamp.toString()),
            Pair("version", version.toString()),
            Pair("mid", if (mid.isNullOrEmpty()) "0" else mid ),
            Pair("token", token),
            Pair("device_id", device_id),
            Pair("device_type", device_type),
            Pair("os_version", os_version),
            Pair("os_band", os_band.toString()),
            Pair("sim_no", sim_no),
            Pair("imei", imei),
            Pair("phone", phone),
            Pair("net_work", net_work.toString()),
            Pair("pro", pro.toString()),
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