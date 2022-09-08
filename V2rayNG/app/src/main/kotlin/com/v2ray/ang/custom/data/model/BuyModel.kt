package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.bss.sdk.livedata.asLiveData
import com.forest.bss.sdk.netWork.rxLaunch
import com.forest.net.app.App
import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.api.ApiService
import com.v2ray.ang.custom.data.entity.ErrorResult
import com.v2ray.ang.custom.data.params.PublicRequestParams
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/31 10:49

 * Desc TODO
 */

class BuyModel : ViewModel() {

    private val _liveDataBuy = MutableLiveData<Result<BaseResponse<ErrorResult>>>()
    val liveDataBuy = _liveDataBuy.asLiveData()

   fun buy(card_no: String) {
        viewModelScope.rxLaunch<BaseResponse<ErrorResult>> {
            onRequest = {
                val mutableMap = mutableMapOf<String, String>()
                mutableMap["card_no"] = card_no
                App.obtainRetrofitService(ApiService::class.java).activatePayment(
                    PublicRequestParams.params(mutableMap))
            }
            onSuccess = {
                _liveDataBuy.postValue(Result.success(it))
            }
            onError = {
                _liveDataBuy.postValue(Result.failure(it))
            }
        }
    }
}