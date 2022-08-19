package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v2ray.ang.custom.data.entity.BuyBean

/**
 * Created by wangkai on 2021/05/31 10:49

 * Desc TODO
 */

class BuyModel : ViewModel() {

    val buyBean = MutableLiveData<BuyBean>()
    val buyError = MutableLiveData<Throwable>()
    fun buy(mid: String, token: String, card_no: String) {
//        OkHttpProxy.get()
//                .url("http://13.125.253.241:9091//index/activate?mid=$mid&token=$token&card_no=$card_no")
//                .tag(this)
//                .enqueue(object : OkCallback<BuyEntity>(object : OkJsonParser<BuyEntity?>() {}) {
//                    override fun onSuccess(code: Int, user: BuyEntity) {
//                        buyBean.postValue(user)
//                    }
//
//                    override fun onFailure(e: Throwable) {
//                        buyError.postValue(e)
//                    }
//                })
    }
}