package com.forest.bss.sdk.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context


/**
 * Created by wangkai on 2021/04/29 15:28

 * Desc TODO
 */

/**
 * 复制文本到剪切板
 */
fun copyClipboard(copyText: String?) {
    safeRun {
        if (copyText != null) {
            //获取剪贴板管理器：
            val cm: ClipboardManager? = appContext.getSystemService(Context.CLIPBOARD_SERVICE).asType<ClipboardManager>()
            // 创建普通字符型ClipData
            val mClipData: ClipData = ClipData.newPlainText("Label", copyText)
            // 将ClipData内容放到系统剪贴板里。
            cm?.setPrimaryClip(mClipData)
        }
    }
}