package com.forest.bss.sdk.listener

import android.annotation.SuppressLint
import android.text.Editable
import android.view.View
import androidx.lifecycle.*
import com.forest.bss.sdk.ext.getLifecycleOwner
import com.forest.bss.sdk.log.logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by wangkai on 2021/01/30 10:31

 * Desc EditText事件搜索监听，使用协程Coroutine的方式实现
 *
 * 参考自：https://juejin.cn/post/6925304772383735822
 */

@SuppressLint("RestrictedApi")
open class YqTextChangeCoroutineDebounceListener(val view: View, private val delayMillis: Long = 300L, onTextChanged: ((String?) -> Unit)? = null) : YqTextChangeListener(), LifecycleEventObserver {

    //定义一个全局的 StateFlow
    @ExperimentalCoroutinesApi
    private val _etState by lazy { MutableStateFlow("") }

    init {
        view.context?.getLifecycleOwner()?.also {
            it.lifecycle.addObserver(this)//注册自己到activity或fragment的LifecycleRegistry中
        }.apply {
            this?.lifecycleScope?.launch {
            _etState
                    .debounce(delayMillis) // 限流，500毫秒
//                    .filter {
//                        // 空文本过滤掉
//                        it.isNotBlank()
//                    }
                    .collect {
                        // 订阅数据
                        logger {
                            "Thread thread: ${Thread.currentThread()}"
                        }
                        onTextChanged?.invoke(it)
                    }
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)
        val editText = s.toString().trim()
        // 往流里写数据
        _etState.value = editText
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        logger {
            "onStateChanged source: $source; event: $event"
        }
    }
}