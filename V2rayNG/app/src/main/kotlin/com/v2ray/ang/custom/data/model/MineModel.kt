package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.AppUtils
import com.forest.bss.sdk.livedata.asLiveData
import com.forest.bss.sdk.netWork.rxLaunch
import com.forest.net.app.App
import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.api.ApiService
import com.v2ray.ang.custom.data.entity.UpdateBean
import com.v2ray.ang.custom.data.params.PublicRequestParams
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/31 10:49

 * Desc TODO
 */

class MineModel : ViewModel() {

    private val _liveDataCheckUpdate = MutableLiveData<Result<BaseResponse<UpdateBean>>>()
    val liveDataCheckUpdate = _liveDataCheckUpdate.asLiveData()

   fun checkUpdate() {
        viewModelScope.rxLaunch<BaseResponse<UpdateBean>> {
            onRequest = {
                val mutableMap = mutableMapOf<String, String>()
                mutableMap["version_code"] = AppUtils.getAppVersionCode().toString()
                App.obtainRetrofitService(ApiService::class.java).checkUpdate(PublicRequestParams.params(mutableMap))
            }
            onSuccess = {
                viewModelScope.launch {
//                    UserInfoDataStore.save(it.results?.user_info)
                }
                _liveDataCheckUpdate.postValue(Result.success(it))
            }
            onError = {
                _liveDataCheckUpdate.postValue(Result.failure(it))
            }
        }
    }
}