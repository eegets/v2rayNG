package com.forest.net.scheduler

import com.forest.net.data.BaseResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by wangkai on 2021/01/26 14:31
 * desc 线程切换操作类
 */

/**
 * io线程切换主线程
 */
fun <T> Observable<BaseResponse<T>?>?.ioToMain(): Observable<BaseResponse<T>?>? {
    return this?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
}

/**
 * 单线程切换主线程
 */
fun <T> Observable<BaseResponse<T>?>?.singleToMain(): Observable<BaseResponse<T>?>? {
    return this?.subscribeOn(Schedulers.single())
            ?.observeOn(AndroidSchedulers.mainThread())
}

/**
 * CPU密集线程切换主线程
 */
fun <T> Observable<BaseResponse<T>?>?.computationToMain(): Observable<BaseResponse<T>?>? {
    return this?.subscribeOn(Schedulers.computation())
            ?.observeOn(AndroidSchedulers.mainThread())
}