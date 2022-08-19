@file:JvmName("DialogFragmentUtils")

package com.forest.bss.sdk.ext

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.forest.net.ext.safeRun

/**
 * Created by wangkai on 2021/02/02 14:39

 * Desc DialogFragment 安全弹出，具体可以查看
 *
 * https://www.jianshu.com/p/8f7d86b85c4f
 * https://www.cnblogs.com/mengdd/p/5827045.html
 */

fun DialogFragment.showDialogSafely(fragmentManager: FragmentManager, tag: String? = this.simpleName()) {
    safeRun {
        if (!this.isAdded) {
            this.show(fragmentManager, tag)
        }
    }
}