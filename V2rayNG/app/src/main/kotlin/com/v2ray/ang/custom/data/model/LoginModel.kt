package com.v2ray.ang.custom.data.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.forest.bss.sdk.livedata.asLiveData
import com.forest.bss.sdk.netWork.rxLaunch
import com.forest.net.app.App
import com.forest.net.data.BaseResponse
import com.v2ray.ang.custom.data.api.ApiService
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.custom.data.params.PublicRequestParams
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/31 10:49

 * Desc TODO
 */

class LoginModel : ViewModel() {
//    private val loginTestJson = "{\n" +
//            "  \"msg\":\"ok\",\"code\":200,\"results\":{\"end_date\":\"2029-12-16 11:59:22\",\"update_time\":\"2020-12-26 12:06:53\",\"from\":0,\"id\":1,\"start_date\":\"2020-12-16 11:59:22\",\"vmess\":[{\"vmess_string\":\"vmess://eyJhZGQiOiI1NC45NS4xMjYuNjUiLCJhaWQiOiI2NCIsImhvc3QiOiIiLCJpZCI6ImQwNjQ5ODlkLTg5NGMtNDJjNi1hYWE0LTA1ZjE3NDcxMzM5YSIsIm5ldCI6IndzIiwicGF0aCI6Ii93cy83czRjazRpOjQxYmQ3NWU2NmMyMzA2ZWYzZTAxZWVmZmIxYzYwZTNlLyIsInBvcnQiOiI4MCIsInBzIjoiZmlyc3QtdjJyYXkiLCJ0bHMiOiIiLCJ0eXBlIjoibm9uZSIsInYiOiIyIn0=\",\"host\":\"54.95.126.65\"}],\"cover_url\":\"http://www.baidu.com/a.jpg\",\"create_time\":\"2020-12-16 11:59:22\",\"openid\":\"7s4ck4i\",\"is_vip\":\"是\",\"country_code\":\"11\",\"phone\":\"11\",\"username\":\"testdemo\",\"status\":\"正常\"\n" +
//            "}\n" +
//            "}"
    private val _liveDataLogin = MutableLiveData<Result<BaseResponse<UserInfoBean>>>()
    val liveDataLogin = _liveDataLogin.asLiveData()

   fun login(userName: String, passWord: String) {
//        OkHttpProxy.get()
//                .url("http://13.125.253.241:9091/u/login?name=$name&password=$password")
//                .tag(this)
//                .enqueue(object : OkCallback<LoginEntity>(object : OkJsonParser<LoginEntity?>() {}) {
//                    override fun onSuccess(code: Int, user: LoginEntity) {
//                        loginBean.postValue(user)
//                    }
//
//                    override fun onFailure(e: Throwable) {
//                        loginError.postValue(e)
//                    }
//                })
       viewModelScope.rxLaunch<BaseResponse<UserInfoBean>> {
           onRequest = {
               val mutableMap = mutableMapOf<String, String>()
               mutableMap["username"] = userName
               mutableMap["password"] = passWord
               App.obtainRetrofitService(ApiService::class.java).login(PublicRequestParams.params(mutableMap))
           }
           onSuccess = {
               viewModelScope.launch {
                   UserInfoDataStore.save(it.results)
               }
               _liveDataLogin.postValue(Result.success(it))
           }
           onError = {
               _liveDataLogin.postValue(Result.failure(it))
           }
       }
    }
}