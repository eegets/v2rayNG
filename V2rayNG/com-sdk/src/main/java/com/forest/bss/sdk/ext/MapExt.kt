package com.forest.bss.sdk.ext

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * Desc
 *
 * Created by zhangzhixiang on 2/19/21 1:34 PM
 */

//@Deprecated("使用下方带范型的转换方法")
//fun Map<String, Any>.toRequestBody() = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(this))

fun <T> T.toRequestBody() = RequestBody.create("application/json".toMediaTypeOrNull(), Gson().toJson(this))