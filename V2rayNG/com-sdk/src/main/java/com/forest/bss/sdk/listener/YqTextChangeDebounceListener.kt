package com.forest.bss.sdk.listener

import android.os.*
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.LifecycleObserver
import com.forest.bss.sdk.log.logger
import kotlinx.coroutines.GlobalScope
import kotlin.random.Random

/**
 * Created by wangkai on 2021/01/30 10:31

 * Desc EditText事件搜索监听，使用Handler延迟方式实现
 */

open class YqTextChangeDebounceListener(view: View, private val delayMillis: Long = 300L, onTextChanged: ((String?) -> Unit)? = null) : YqTextChangeListener() {

    private var handler: Handler? = null

    private val rand by lazy {
        Random(100)
    }

    companion object {

        private var HANDLER_WHAT = 0

        private const val BUNDLE_KEY = "bundleKey"
    }

    init {

        HANDLER_WHAT = rand.nextInt()

        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {
                logger {
                    "onViewAttachedToWindow 感知View状态变化"
                }

                handler = Handler(Looper.getMainLooper()) {
                    onTextChanged?.invoke(it.peekData().getString(BUNDLE_KEY))
                    false
                }
            }

            override fun onViewDetachedFromWindow(v: View?) {
                logger {
                    "onViewDetachedFromWindow 感知View状态变化"
                }
                handler?.removeCallbacksAndMessages(null)
            }
        })
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)
        val editText = s.toString().trim()

        if (handler?.hasMessages(HANDLER_WHAT) == true) {
            logger {
                "handler hasMessages"
            }
            handler?.removeMessages(HANDLER_WHAT)
        }

        Message().also {
            it.what = HANDLER_WHAT
            it.data = Bundle().apply {
                putString(BUNDLE_KEY, editText)
            }
        }.let {
            handler?.sendMessageDelayed(it, delayMillis)
        }
    }
}