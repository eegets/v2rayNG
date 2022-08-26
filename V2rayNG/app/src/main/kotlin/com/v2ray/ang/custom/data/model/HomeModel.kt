package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.forest.bss.sdk.livedata.asLiveData
import com.forest.bss.sdk.netWork.rxLaunch
import com.forest.net.app.App
import com.forest.net.data.BaseResponse
import com.forest.net.model.BaseViewModel
import com.v2ray.ang.custom.data.api.ApiService
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.custom.data.entity.VMessBean
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.custom.sign.PublicRequestParams
import kotlinx.coroutines.launch

class HomeModel : BaseViewModel() {

    private val _liveDataInfo = MutableLiveData<Result<BaseResponse<HomeBean>>>()
    val liveDataInfo = _liveDataInfo.asLiveData()

    private val _liveDataVMess = MutableLiveData<Result<BaseResponse<String>>>()
    val liveDataVMess = _liveDataVMess.asLiveData()

    fun getInfo() {
        viewModelScope.rxLaunch<BaseResponse<HomeBean>> {
            onRequest = {
                App.obtainRetrofitService(ApiService::class.java).getInfo(PublicRequestParams.params())
            }
            onSuccess = {
                viewModelScope.launch {
//                    UserInfoDataStore.save(it.results?.user_info)
                }
                _liveDataInfo.postValue(Result.success(it))
            }
            onError = {
                _liveDataInfo.postValue(Result.failure(it))
            }
        }
    }

    /**
     * 一键加速验证
     * [modeId] 节点id
     */
    fun checkAuth(modeId: Int) {
        viewModelScope.rxLaunch<BaseResponse<String>> {
            onRequest = {
                val mutableMap = mutableMapOf<String, String>()
                mutableMap["smode"] = modeId.toString()
                App.obtainRetrofitService(ApiService::class.java).checkAuth(PublicRequestParams.params(mutableMap))
            }
            onSuccess = {
                _liveDataVMess.postValue(Result.success(it))
            }
            onError = {
                _liveDataVMess.postValue(Result.failure(it))
            }
        }
    }
}