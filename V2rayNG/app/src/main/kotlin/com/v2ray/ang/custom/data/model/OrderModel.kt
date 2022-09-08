package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.bss.sdk.livedata.asLiveData
import com.forest.bss.sdk.netWork.rxLaunch
import com.forest.net.app.App
import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.api.ApiService
import com.v2ray.ang.custom.data.entity.OrderBean
import com.v2ray.ang.custom.data.params.PublicRequestParams

class OrderModel : ViewModel() {

    private val _liveDataOrderLists = MutableLiveData<Result<BaseResponse<MutableList<OrderBean>>>>()
    val liveDataOrderLists = _liveDataOrderLists.asLiveData()

    fun queryOrderLists() {
        viewModelScope.rxLaunch<BaseResponse<MutableList<OrderBean>>> {
            onRequest = {
                App.obtainRetrofitService(ApiService::class.java).queryOrderLists(PublicRequestParams.params())
            }
            onSuccess = {
                _liveDataOrderLists.postValue(Result.success(it))
            }
            onError = {
                _liveDataOrderLists.postValue(Result.failure(it))
            }
        }
    }
}