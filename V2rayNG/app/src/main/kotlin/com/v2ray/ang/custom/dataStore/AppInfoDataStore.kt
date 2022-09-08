package com.v2ray.ang.custom.dataStore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.forest.bss.sdk.dataStore.dataStore
import com.forest.bss.sdk.dataStore.suspendReadStringKey
import com.forest.bss.sdk.dataStore.suspendReadStringSetKey
import com.forest.bss.sdk.ext.appContext
import com.forest.bss.sdk.ext.asType
import com.v2ray.ang.custom.data.entity.UserInfoBean

/**
 * Created by wangkai on 2022/01/19 10:50

 * Desc 登陆信息存储
 */
object AppInfoDataStore {
    /**
     * 存储键值对数据
     */
    suspend fun save(set: MutableSet<String>?) {
        appContext.dataStore.edit { preferences ->
            set.apply {
                preferences[stringSetPreferencesKey("packageName")] = this ?: mutableSetOf()
            }
        }
    }

    /**
     * 读取键值对数据
     * [key] 键
     */
    suspend fun suspendRead(): MutableSet<String>? {
        return appContext.dataStore.suspendReadStringSetKey("packageName")?.asType()
    }

    suspend fun clear() {
        appContext.dataStore.edit {
            it.clear()
        }
    }
}