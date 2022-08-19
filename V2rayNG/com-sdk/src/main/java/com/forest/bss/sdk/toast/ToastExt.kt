package com.forest.bss.sdk.toast

import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.forest.bss.sdk.ext.noEmpty
import com.forest.net.ext.safeRun

/**
 * Created by wangkai on 2021/02/06 15:38

 * Desc TODO
 */

object ToastExt {
    fun show(toast: String) {
        if (toast.noEmpty()) {
            safeRun {
                ToastUtils().setGravity(Gravity.CENTER, 0, -100)?.show(toast)
            }
        }
    }
}