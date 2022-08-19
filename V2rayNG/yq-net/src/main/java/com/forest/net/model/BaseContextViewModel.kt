package com.forest.net.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.forest.net.data.Completed
import com.forest.net.data.Error
import com.forest.net.data.Started

/**
 * Created by wangkai on 2021/02/23 14:14

 * Desc 基础ViewModel，主要用于操作网络请求开始，异常，完成监听
 */

open class BaseContextViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * 请求开始时的监听回调，一般用于网络请求前的loading加载
     */
    var start: MutableLiveData<Started> = MutableLiveData()

    /**
     * 网络请求失败或token验证失败或过期
     * 网络请求失败为`-1`
     * token过期以网络返回结果code为准，默认为`401`
     */
    var error: MutableLiveData<Error> = MutableLiveData()

    /**
     * 请求完成时的监听回调，一般用于网络请求完成的一些操作，比如loading加载完成等
     */
    var complete: MutableLiveData<Completed> = MutableLiveData()
}