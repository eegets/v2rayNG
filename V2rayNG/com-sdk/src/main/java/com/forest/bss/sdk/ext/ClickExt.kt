package com.forest.bss.sdk.ext

import android.view.View
import com.forest.bss.sdk.ext.ViewClickDelay.SPACE_TIME
import com.forest.bss.sdk.ext.ViewClickDelay.hash
import com.forest.bss.sdk.ext.ViewClickDelay.lastClickTime

/**
 * Desc
 *
 * Created by zhangzhixiang on 4/2/21 4:08 PM
 */
object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 1000 * 1
}

infix fun View.clickDelay(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}
