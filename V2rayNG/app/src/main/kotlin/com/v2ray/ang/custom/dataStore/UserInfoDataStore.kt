package com.v2ray.ang.custom.dataStore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.forest.bss.sdk.dataStore.dataStore
import com.forest.bss.sdk.dataStore.suspendReadStringKey
import com.forest.bss.sdk.ext.appContext
import com.v2ray.ang.custom.data.entity.UserInfoBean

/**
 * Created by wangkai on 2022/01/19 10:50

 * Desc 登陆信息存储
 */
object UserInfoDataStore {
    /**
     * 存储键值对数据
     */
    suspend fun save(bean: UserInfoBean?) {
        appContext.dataStore.edit { preferences ->
            bean.apply {
                preferences[stringPreferencesKey("id")] = this?.id.toString()
                preferences[stringPreferencesKey("openid")] = this?.openid.toString()
                preferences[stringPreferencesKey("username")] = this?.username.toString()
                preferences[stringPreferencesKey("country_code")] = this?.country_code.toString()
                preferences[stringPreferencesKey("phone")] = this?.phone.toString()
                preferences[stringPreferencesKey("cover_url")] = this?.cover_url.toString()
                preferences[stringPreferencesKey("is_vip")] = this?.is_vip.toString()
                preferences[stringPreferencesKey("start_date")] = this?.start_date.toString()
                preferences[stringPreferencesKey("end_date")] = this?.end_date.toString()
                preferences[stringPreferencesKey("province")] = this?.province.toString()
                preferences[stringPreferencesKey("city")] = this?.city.toString()
                preferences[stringPreferencesKey("gender")] = this?.gender.toString()
                preferences[stringPreferencesKey("from")] = this?.from.toString()
                preferences[stringPreferencesKey("remark")] = this?.remark.toString()
                preferences[stringPreferencesKey("status")] = this?.status.toString()
                preferences[stringPreferencesKey("device_id")] = this?.device_id.toString()
                preferences[stringPreferencesKey("device_type")] = this?.device_type.toString()
                preferences[stringPreferencesKey("os_version")] = this?.os_version.toString()
                preferences[stringPreferencesKey("os_band")] = this?.os_band.toString()
                preferences[stringPreferencesKey("sim_no")] = this?.sim_no.toString()
                preferences[stringPreferencesKey("imei")] = this?.imei.toString()
                preferences[stringPreferencesKey("net_work")] = this?.net_work.toString()
                preferences[stringPreferencesKey("pro")] = this?.pro.toString()
                preferences[stringPreferencesKey("token")] = this?.token.toString()
            }
        }
    }

    /**
     * 读取键值对数据
     * [key] 键
     */
    suspend fun suspendRead(key: String): String {
        return appContext.dataStore.suspendReadStringKey(key)
    }

    suspend fun clear() {
        appContext.dataStore.edit {
            it.clear()
        }
    }
}