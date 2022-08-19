@file:JvmName("Inspector")
package com.forest.bss.sdk.ext

import com.forest.bss.sdk.BuildConfig
import com.forest.bss.sdk.log.logger

/**
 * 断定当前执行线程为工作者线程(非主线程),否则日志报错或者异常抛出
 */
fun assertWorkerThread(scene: String?) {
    if (!Thread.currentThread().isWorkerThread()) {
        val errorMessage = "WorkerThread expected, scene=$scene"
        if (BuildConfig.DEBUG) {
            throw IllegalThreadStateException(errorMessage)
        } else {
            logger {
                errorMessage
            }
        }
    }
}



