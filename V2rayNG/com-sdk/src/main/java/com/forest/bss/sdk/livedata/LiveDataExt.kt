package com.forest.bss.sdk.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by wangkai on 2022/01/13 15:45

 * Desc TODO
 */

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}