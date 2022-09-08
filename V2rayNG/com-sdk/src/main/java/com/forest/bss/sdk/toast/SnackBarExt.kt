package com.forest.bss.sdk.toast

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.SnackbarUtils
import com.forest.bss.sdk.ext.noEmpty
import com.forest.net.ext.safeRun

/**
 * Created by wangkai on 2021/02/06 15:38

 * Desc TODO
 */

object SnackBarExt {
    fun show(activity: FragmentActivity, toast: String) {
        if (toast.noEmpty()) {
            safeRun {
                SnackbarUtils.with(activity.window.decorView).setMessage(toast).show()
            }
        }
    }
    fun show(view: View, toast: String) {
        if (toast.noEmpty()) {
            safeRun {
                SnackbarUtils.with(view).setMessage(toast).show()
            }
        }
    }
}