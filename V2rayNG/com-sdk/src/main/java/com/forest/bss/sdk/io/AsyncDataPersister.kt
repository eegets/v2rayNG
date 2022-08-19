package com.forest.bss.sdk.io

import android.os.Handler
import android.os.HandlerThread
import com.forest.bss.sdk.ext.*
import com.forest.bss.sdk.log.logger
import io.reactivex.rxjava3.functions.Consumer
import java.io.File
import kotlin.random.Random

/**
 * Created by wangkai on 2021/05/21 14:14

 * Desc 异步数据持久化类
 *  非UI线程，避免阻塞UI线程
 *  指定工作者线程的读和写，避免并发问题
 *
 */

/**
 * [persistFile] 数据持久化目标文件（不建议使用外置存储）
 */
class AsyncDataPersister(private val persistFile: File) {
    /**
     * 便于将读写操作执行在指定的线程中
     */
    private var handler: Handler? = null
    /**
     * 所有数据持久化(读和写)都需要在该线程中执行
     */
    private var handlerThread: HandlerThread? = null
    private val handlerThreadName by lazy {
        "AsyncDataPersister-${Random.nextInt(10)}"
    }

    init {
        ensureHandlerThread()
    }

    private fun ensureHandlerThread() {
        if (handlerThread == null) {
            initHandlerThread()
        }
    }

    private fun initHandlerThread() {
        logger { "initHandlerThread" }
        val thread = HandlerThread(handlerThreadName)
        thread.start()
        handlerThread = thread
        handler = Handler(thread.looper)
    }
    /**
     * 持久化数据
     *  * 数据为null，不执行持久化
     *  * handler未初始化，不执行持久化
     *
     *  consumer只在debug模式下数据返回
     */
    fun persist(data: String?,  consumer: Consumer<String?>?) {
        logger {
            "persistData data: $data"
        }
        checkPersistCondition(data).runWhenTrue {
            handler?.safePost {
                logger {
                    "persistData writeToFile filePath: ${persistFile.path}; data: $data"
                }
                data.writeToFile(persistFile.path)
                debugRun {
                    consumer?.accept(persistFile.readContent())
                }
            }
        }
    }


    /**
     * 持久化数据
     *  * 数据为null，不执行持久化
     *  * handler未初始化，不执行持久化
     *
     *  consumer只在debug模式下数据返回
     */
    fun persistAppend(data: String?,  consumer: Consumer<String?>?) {
        logger {
            "persistData data: $data"
        }
        checkPersistCondition(data).runWhenTrue {
            handler?.safePost {
                logger {
                    "persistData writeToFile filePath: ${persistFile.path}; data: $data"
                }
                data.appendToFile(persistFile.path)

                debugRun {
                    consumer?.accept(persistFile.readContent())
                }
            }
        }
    }

    private fun checkPersistCondition(data: String?): Boolean {
        if (data.isNullOrEmpty()) {
            logger { "persist skip null or empty data: $data" }
            return false
        }
        ensureHandlerThread()
        return true
    }

    private fun checkObtainDataCondition(consumer: Consumer<String>?): Boolean {
        if (consumer == null) {
            return false
        }
        ensureHandlerThread()
        return true
    }
    private fun checkObtainDataCondition1(consumer: Consumer<Boolean>?): Boolean {
        if (consumer == null) {
            return false
        }
        ensureHandlerThread()
        return true
    }

    /**
     * 获取持久化的数据,consumer回调中使用
     * 不回调的情况
     *   * handler为初始化
     *   * 读取出现异常
     */
    fun obtainAndEmptyData(consumer: Consumer<String>?) {
        checkObtainDataCondition(consumer).runWhenTrue {
            handler?.safePost {
                persistFile.readAndEmpty()?.let {
                    logger {
                        "obtainAndEmptyData found"
                    }
                    consumer?.accept(it)
                }
            }
        }
    }

    fun obtainData(consumer: Consumer<String>?) {
        checkObtainDataCondition(consumer).runWhenTrue {
            handler?.safePost {
                consumer?.accept(persistFile.readContent())
            }
        }
    }

    /**
     * 清空文件内容
     */
    fun emptyContent(consumer: Consumer<Boolean>?) {
        checkObtainDataCondition1(consumer).runWhenTrue {
            handler?.safePost {
                persistFile.emptyContent().let {
                    consumer?.accept(it)
                }
            }
        }
    }
}