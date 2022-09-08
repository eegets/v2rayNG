package com.forest.bss.sdk.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.forest.bss.sdk.ext.appContext
import com.forest.bss.sdk.ext.isNull
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlin.coroutines.EmptyCoroutineContext

/**
 * 创建dataStore对象
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "YqFaceCabinetDataStore")


/**
 * 清除dataStore对象数据
 */
suspend fun clearDataStore() = run {
    coroutineScope.cancel()
    appContext.dataStore.edit {
        it.clear()
    }
}

private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

private val coroutineScope: CoroutineScope by lazy { CoroutineScope(EmptyCoroutineContext) }

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`String`
 */
suspend fun DataStore<Preferences>.blockReadStringKey(key: String): String {
    var value: String? = null
    if (!coroutineScope.isActive) {
        coroutineScope
    }
    withContext(defaultDispatcher)  {
        val data = data.first()
        value = data[stringPreferencesKey(key)]
    }
    return value.isNull()
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`String`
 */
suspend fun DataStore<Preferences>.suspendReadStringKey(key: String): String {
    return data.map { preferences ->
        preferences[stringPreferencesKey(key)].isNull()
    }.first()
}

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Int`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
fun DataStore<Preferences>.blockReadIntKey(key: String): Int {
    var value: Int?
    runBlocking {
        val data = data.first()
        value = data[intPreferencesKey(key)]
    }
    return value.isNull()
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Int`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadIntKey(key: String): Int {
    return data.map { preferences ->
        preferences[intPreferencesKey(key)].isNull()
    }.first()
}

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Double`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
fun DataStore<Preferences>.blockReadDoubleKey(key: String): Double {
    var value: Double?
    runBlocking {
        val data = data.first()
        value = data[doublePreferencesKey(key)]
    }
    return value.isNull()
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Double`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadDoubleKey(key: String): Double {
    return data.map { preferences ->
        preferences[doublePreferencesKey(key)].isNull()
    }.first()
}

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Float`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
fun DataStore<Preferences>.blockReadFloatKey(key: String): Float {
    var value: Float?
    runBlocking {
        val data = data.first()
        value = data[floatPreferencesKey(key)]
    }
    return value.isNull()
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Float`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadFloatKey(key: String): Float {
    return data.map { preferences ->
        preferences[floatPreferencesKey(key)].isNull()
    }.first()
}

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Boolean`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
fun DataStore<Preferences>.blockReadBooleanKey(key: String): Boolean {
    var value = false
    coroutineScope.launch {
        val data = data.first()
        value = data[booleanPreferencesKey(key)] == true
    }
    return value
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Boolean`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadBooleanKey(key: String): Boolean {
    return data.map { preferences ->
        preferences[booleanPreferencesKey(key)] == true
    }.first()
}

/**
 * 同步的方式获取[key]对应的dataStore的值，以下代码会阻塞发起调用的线程，直到 DataStore 返回数据
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Long`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
fun DataStore<Preferences>.blockReadLongKey(key: String): Long {
    var value: Long?
    runBlocking {
        val data = data.first()
        value = data[longPreferencesKey(key)]
    }
    return value.isNull()
}

/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Long`
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadLongKey(key: String): Long {
    return data.map { preferences ->
        preferences[longPreferencesKey(key)].isNull()
    }.first()
}


/**
 * 异步获取[key]对应的dataStore的值
 *
 * [key] 缓存的key
 *
 * 返回值类型为`Set<String>? `
 *
 * https://developer.android.com/topic/libraries/architecture/datastore?hl=zh-cn#kotlin
 */
suspend fun DataStore<Preferences>.suspendReadStringSetKey(key: String): Set<String>? {
    return data.map { preferences ->
        preferences[stringSetPreferencesKey(key)]
    }.first()
}