package com.v2ray.ang.custom.dialog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.forest.net.ext.safeRun
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 * Created by wangkai on 2021/02/05 18:25

 * Desc TODO
 */

class LoadingUtils {

    var loadingPopup: LoadingPopupView? = null

    /**
     * 展示loading弹框
     */
    fun show(context: Context, message: String? = null) {
        safeRun {
            if (loadingPopup == null) {
                loadingPopup = if (message != null) {
                    XPopup.Builder(context)
                            .dismissOnBackPressed(false)
                            .asLoading(message)
                            .show() as LoadingPopupView
                } else {
                    XPopup.Builder(context)
                            .dismissOnBackPressed(false)
                            .asLoading()
                            .show() as LoadingPopupView
                }
            } else {
                loadingPopup?.setTitle(message)?.show()
            }
        }
    }

    /**
     * 展示loading弹框
     */
    fun show(activity: AppCompatActivity, message: String? = null) {
        safeRun {
            if (loadingPopup == null) {
                loadingPopup = if (message != null) {
                    XPopup.Builder(activity)
                            .dismissOnBackPressed(false)
                            .asLoading(message)
                            .show() as LoadingPopupView
                } else {
                    XPopup.Builder(activity)
                            .dismissOnBackPressed(false)
                            .asLoading()
                            .show() as LoadingPopupView
                }
            } else {
                loadingPopup?.setTitle(message)?.show()
            }
        }
    }

    /**
     * 展示loading弹框
     */
    fun show(fragment: Fragment?, message: String? = null) {
        safeRun {
            if (fragment != null) {
                if (loadingPopup == null) {
                    loadingPopup = if (message != null) {
                        XPopup.Builder(fragment.requireContext())
                                .dismissOnBackPressed(false)
                                .asLoading(message)
                                .show() as LoadingPopupView
                    } else {
                        XPopup.Builder(fragment.requireContext())
                                .dismissOnBackPressed(false)
                                .asLoading()
                                .show() as LoadingPopupView
                    }
                } else {
                    loadingPopup?.setTitle(message)?.show()
                }
            }
        }
    }

    /**
     * 隐藏loading弹框
     * 通用隐藏loading弹框
     */
    fun hideCommon() {
        if (loadingPopup != null) {
            loadingPopup?.dismiss()
        }
    }

    /**
     * 隐藏loading弹框
     * 会等待弹窗show动画执行完毕再消失
     */
    fun hide() {
        if (loadingPopup != null) {
            loadingPopup!!.smartDismiss()
        }
    }

    /**
     * 隐藏loading弹框
     * 延迟[millisecond]毫秒关闭
     */
    fun hideDelay(millisecond: Long, call:()->Unit) {
        if (loadingPopup != null) {
            loadingPopup!!.delayDismissWith(millisecond) {
                call?.invoke()
            }
        }
    }

}