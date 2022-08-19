package com.forest.bss.sdk.countdown

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by wangkai on 2022/03/15 15:59

 * Desc TODO
 */


fun countDownCoroutines(
        millisInFuture: Long,
        countDownIntervalInMills: Long = 1000L,
        scope: CoroutineScope,
        onTick: (Long) -> Unit,
        onStart: (() -> Unit)? = null,
        onFinish: (() -> Unit)? = null,
): Job {
    return flow {
        for (i in millisInFuture downTo 0) {
            if(currentCoroutineContext().isActive) {
                emit(i)
                delay(countDownIntervalInMills)
            }
        }
    }.flowOn(Dispatchers.IO)
            .onStart { onStart?.invoke() }
            .onCompletion { onFinish?.invoke() }
            .onEach { onTick.invoke(it) }
            .launchIn(scope)
}

/**
 * 每过'interval'时间执行一次'block()'
 */
fun CoroutineScope?.scheduleTimer(interval: Long, block : () -> Unit) {
    this ?: return
    this.launch {
        while (isActive) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            delay(interval)
        }
    }
}

fun CoroutineScope?.cancelTimer() {
    this ?: return
    this.cancel()
}