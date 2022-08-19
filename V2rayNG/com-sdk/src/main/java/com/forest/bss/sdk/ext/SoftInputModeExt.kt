package com.forest.bss.sdk.ext

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.forest.net.ext.safeRun

/**
 * Created by wangkai on 2021/02/02 10:59

 * Desc 软件盘显示隐藏扩展
 */

fun EditText.showSoftInput() =
        safeRun {
                this.requestFocus()
                this.context.getSystemService(Context.INPUT_METHOD_SERVICE)?.asType<InputMethodManager>()?.showSoftInput(this, 0)
        }

fun EditText.hideSoftInput() =
        safeRun {
                val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE).asType<InputMethodManager>()
                imm?.hideSoftInputFromWindow(this.windowToken, 0)
        }