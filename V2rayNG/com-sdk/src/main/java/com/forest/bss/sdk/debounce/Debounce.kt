package com.forest.bss.sdk.debounce

import com.forest.bss.sdk.log.logger

/**
 * 去重处理
 */
class  Debounce<T>(private val delayInMillSeconds: Long) {
    var currentTime = 0L
    /**
     * 去重后值的回调
     */
    var valueCallback:((T) -> Unit)? = null

    /**
     * 调用更新值，会只会在指定时间内选取并消费一次
     */
    fun updateValue(value: T): Boolean {
        return if (System.currentTimeMillis() - currentTime < delayInMillSeconds) {
            logger { "Debounce.ignored updateValue=$value" }
            false
        } else {
            currentTime = System.currentTimeMillis()
            valueCallback?.invoke(value)
            true
        }
    }
}