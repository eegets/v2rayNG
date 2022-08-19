package com.forest.bss.sdk.ext

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.EditText
import com.forest.bss.sdk.BuildConfig
import com.forest.bss.sdk.listener.YqTextChangeCoroutineDebounceListener
import com.forest.bss.sdk.listener.YqTextChangeDebounceListener

/**
 * Created by wangkai on 2021/04/27 11:24

 * Desc TODO
 */

/**
 * 光标显示/隐藏
 * [hide] true表示显示， false表示显示隐藏
 */
fun EditText?.cursorVisible(visible: Boolean) {
    if (this == null) {
        null
    } else {
        this.isCursorVisible = visible
    }
}

/**
 * 光标显示
 */
@SuppressLint("ClickableViewAccessibility")
fun EditText?.cursorShow() {
    this?.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                cursorVisible(true)
            }
        }
        false
    }
}

/**
 * 移动光标的位置
 */
fun EditText?.selection(index: Int = 0) {
    this?.apply {
        if (index <= this.length()) {
            post { setSelection(length()) }
        } else {
            if (BuildConfig.DEBUG) {
                throw ArrayIndexOutOfBoundsException("invalid index: $index;  length= ${length()} ")
            }
        }
    }
}

/**
 * 搜索间隔
 */
fun EditText?.addTextChangedDebounceListener(debounceTimeOutInMills: Long = 300L, onTextChanged: ((String?) -> Unit)? = null) {

    this?.let {
        this.addTextChangedListener(YqTextChangeDebounceListener(this, debounceTimeOutInMills) { str: String? ->
            onTextChanged?.invoke(str)
        })
    }
}

/**
 * 搜索间隔
 */
fun EditText?.addTextChangedCoroutineDebounceListener(debounceTimeOutInMills: Long = 300L, onTextChanged: ((String?) -> Unit)? = null) {

    this?.let {
        this.addTextChangedListener(YqTextChangeCoroutineDebounceListener(this, debounceTimeOutInMills) { str: String? ->
            onTextChanged?.invoke(str)
        })
    }
}