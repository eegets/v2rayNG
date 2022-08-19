package com.forest.net.headers.responder

import com.forest.net.app.App
import com.forest.net.log.logger
import com.forest.net.utils.AppUtils
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by wangkai on 2021/01/27 11:17

 * Desc 通用的Header请求信息
 */

object GeneralHeaderResponder : HeaderDataResponder {

    //缓存匿名下的header信息，避免每次重复计算
    private val headerHashMap = ConcurrentHashMap<String, String>()

    override fun providerDynamicHeaders(): MutableMap<String, String> {

        headerHashMap["Content-Type"] = "application/json"
        headerHashMap["serviceScene"] = "FACE_BUY" //服务场景
        headerHashMap["appChannel"] = "GOSZFB" //渠道   GOSWX - 微信    GOSZFB - 支付宝
        headerHashMap["versionCode"] = if (App.application()?.applicationContext != null) {AppUtils.getAppVersionCode(App.application()?.applicationContext!!).toString()} else ""

//        headerHashMap["Authorization"] = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJhdCI6MCwidV9pZCI6MSwiY3QiOjMsInVfbmEiOiLlvIDlj5HkurrlkZgiLCJjX2RhdGUiOjE2NDQ0OTEyMjQyODksImV4cCI6MTY0NTA5NjAyNH0.2XHZeDA7pAlM-8vpqg_0jkPcpwKwLObJDsFXmbma540WZkoBOF-w4GRRUC6ixc3ETXtrI00NF87uMk1ttzk5zA"
        logger {
            "Header Responder headerHashMap: $headerHashMap"
        }
        return headerHashMap
    }

}