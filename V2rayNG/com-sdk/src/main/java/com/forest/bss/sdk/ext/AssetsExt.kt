package com.forest.bss.sdk.ext

import com.forest.bss.sdk.log.logger
import java.io.BufferedInputStream

/**
 * Created by wangkai on 2021/02/20 16:09

 * Desc 读取Asset文件中内容
 */

fun <R> readTextAssets(fileName: String, callback: (String?) -> R): R {
    var text: String? = null
    try {
        appContext.assets.open(fileName).use { ins ->
            BufferedInputStream(ins).use { bis ->
                text = bis.reader().readText()
            }
        }
    } finally {
//        logger {
//            "Assets readTextAssets resultText:$text"
//        }
        return callback(text)
    }
}

inline fun <reified T> String.readTextAssetsToList(): MutableList<T>? {
    var list: MutableList<T>? = mutableListOf()
    safeRun {
        readTextAssets(this) {
            list = it?.json2List<T>() as MutableList<T>?
//            logger {
//                "Assets readTextAssetsToList list: $list"
//            }
        }
    }
    return list
}