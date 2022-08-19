@file:JvmName("RxUtil")
package com.forest.bss.sdk.ext

import android.annotation.SuppressLint
import com.forest.bss.sdk.wrapper.CompletableObserverAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by wangkai on 2021/03/10 10:48

 * Desc TODO
 */


/**
 * 执行一个计算密集的操作
 */
@SuppressLint("CheckResult")
inline fun runOnComputationThread(crossinline block: () -> Unit) {
    Completable.fromAction {
        block()
    }.subscribeOn(Schedulers.computation()).subscribe(CompletableObserverAdapter())
}

/**
 * 执行在子线程的操作
 */
@SuppressLint("CheckResult")
inline fun runOnIOThread(crossinline block: () -> Unit) {
    Completable.fromAction {
        block()
    }.subscribeOn(Schedulers.io()).subscribe(CompletableObserverAdapter())
}

/**
 * 执行在主线程的操作
 */
@SuppressLint("CheckResult")
inline fun runOnMainThread(crossinline block: () -> Unit) {
    Completable.fromAction {
        block()
    }.subscribeOn(AndroidSchedulers.mainThread()).subscribe(CompletableObserverAdapter())
}

