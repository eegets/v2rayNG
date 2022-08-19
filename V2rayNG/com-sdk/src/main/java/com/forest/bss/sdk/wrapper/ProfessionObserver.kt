package com.forest.bss.sdk.wrapper

import androidx.lifecycle.Observer
import com.forest.bss.sdk.BuildConfig
import com.forest.bss.sdk.ext.nonNull
import com.forest.bss.sdk.log.logger
import com.forest.bss.sdk.toast.ToastExt
import com.forest.net.data.BaseResponse
import com.forest.net.data.CODE

/**
 * Created by wangkai on 2021/06/07 19:10

 * Desc 增加code != 0的状态下的通用提示
 */

class ProfessionObserver<T>(val changed: ((data: BaseResponse<T>?) -> Unit)) : Observer<BaseResponse<T>> {

    override fun onChanged(data: BaseResponse<T>?) {
        if (data != null) {
            if (data.code == CODE) {
                changed.invoke(data)
            } else {
                ToastExt.show(data.msg.nonNull("接口异常，请重试"))
            }
        } else {
            logger {
                "DataObserver observer data is null"
            }
            if (BuildConfig.DEBUG) {
                throw NullPointerException( "ProfessionObserver observer data is null")
            }
        }
    }
}