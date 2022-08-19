package com.forest.bss.sdk.ext

import android.view.View
import com.forest.bss.sdk.debounce.ViewClickDebounce

/**
 * Created by wangkai on 2021/01/30 10:09

 * Desc TODO
 */

fun View?.makeGone() {
    this?.visibility = View.GONE
}

fun View?.makeVisible() {
    this?.visibility = View.VISIBLE
}

fun View?.makeInvisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.isVisible() : Boolean {
    return this?.visibility == View.VISIBLE
}


/**
 * View的去重点击
 */
fun View?.setDebouncedOnClickListener(debounceTimeOutInMills: Long? = null, onClickListener: ((View?) -> Unit)?) {
    this ?: return
    onClickListener ?: return
    ViewClickDebounce.setupViewDebounceTimeout(this, debounceTimeOutInMills)
    this.doOnClick {
        if (!ViewClickDebounce.isFastClick(it)) {
            onClickListener.invoke(it)
        }
    }
}

/**
 * View的去重点击
 */
fun View?.setDebouncedOnClickListener(onClickListener: ((View?) -> Unit)?) {
    this.setDebouncedOnClickListener(null, onClickListener)
}

/**
 * OnClick点击事件
 */
inline fun View.doOnClick(crossinline action: (View) -> Unit) {
    this.setOnClickListener {
        action.invoke(it)
    }
}

/**
 * OnLongClick点击事件
 */
inline fun View.doOnLongClick(crossinline  action: (View) -> Unit, retCall: Boolean = false) {
    this.setOnLongClickListener {
        action.invoke(it)
        retCall
    }
}