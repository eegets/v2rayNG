package com.forest.bss.sdk.ext

import androidx.appcompat.app.AppCompatActivity
import cc.shinichi.library.ImagePreview
import cc.shinichi.library.bean.ImageInfo

/**
 * Created by wangkai on 2021/04/08 19:03

 * Desc 大图预览
 */

private fun String.addImageInfo(): ImageInfo {
    return ImageInfo().let {
        it.originUrl = this
        it.thumbnailUrl = this
        it
    }
}

/**
 * 加载单张大图
 *
 * [showDownButton] 是否显示下载按钮
 */
fun String.imagePreview(activity: AppCompatActivity, showDownButton: Boolean = false) {

    val imageList = arrayListOf(addImageInfo())
    if (imageList.size > 0) {
        ImagePreview
                .getInstance()
                .setContext(activity)
                .setIndex(0)
                .setShowDownButton(showDownButton)
                .setImageInfoList(imageList)
                .start()
    }
}

/**
 * 加载多张大图
 *
 * [index] 默认显示第几个，默认显示第一个
 * [showDownButton] 是否显示下载按钮
 */
fun List<String>.imagePreview(activity: AppCompatActivity, index: Int = 0, showDownButton: Boolean = false) {

    val imageList = arrayListOf<ImageInfo>()
    forEach {
        imageList.add(it.addImageInfo())
    }
    if (imageList.size > 0 && index < imageList.size) {
        ImagePreview
                .getInstance()
                .setContext(activity)
                .setIndex(index)
                .setShowDownButton(showDownButton)
                .setImageInfoList(imageList)
                .start()
    }
}

