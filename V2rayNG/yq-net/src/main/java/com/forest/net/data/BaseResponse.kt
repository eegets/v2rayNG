package com.forest.net.data

/**
 * Created by wangkai on 2021/01/26 14:31
 * desc 实体基础类
 */

/**
 * 网络请求相应成功，用来处理请求成功
 */
open class BaseResponse<out T> (open val code: Int = -1, open val msg: String = "", val results: T? = null)

/**
 * 网络请求失败或token验证失败或过期
 * @param 网络请求失败-1， token过期以网络返回结果code为准，默认为401
 *
 */
data class Error(override val code: Int = -1, override val msg: String): BaseResponse<Nothing>()

/**
 * 请求开始时的监听回调，一般用于网络请求前的loading加载
 */
object Started: BaseResponse<Nothing>()

/**
 * 请求完成时的监听回调，一般用于网络请求完成的一些操作，比如loading加载完成等
 */
object Completed: BaseResponse<Nothing>()

/**
 * 接口请求成功code
 */
const val CODE = 200

fun <T> Result<BaseResponse<T>>.success(): Boolean = this.isSuccess && this.getOrNull()?.code == CODE
