package com.forest.bss.sdk.ext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.ImageSpan
import androidx.core.content.ContextCompat

/**
 * Created by wangkai on 2021/04/19 17:01

 * Desc Spannable扩展
 */


/**
 * [msg] 全部文本内容
 * [reviseMsg] 待修改文本
 * [reviseColor] 待修改文本颜色
 *
 * eg:  SpannableString(msg1).let {
            it.textSpan(msg1, reviseMsg1, resources.getColor(R.color.public_2D74DB))
            it.textSpan(msg1, reviseMsg1_1, resources.getColor(R.color.public_2D74DB))
            it
        }.apply {
            binding?.wechatText2?.text = this
        }
 */
fun SpannableString.textSpan(msg: String, reviseMsg: String, reviseColor: Int): SpannableString? {
    if (msg.noEmpty() && reviseMsg.noEmpty()) {
        var start = msg.indexOf(reviseMsg)
        var end = start + reviseMsg.length

        this.setSpan(ForegroundColorSpan(reviseColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }
    return null
}

fun SpannableString.textSpan(msg: String, textSize: Float, vararg reviseMsg: String):SpannableString? {
    if (msg.noEmpty() && reviseMsg.isNotEmpty()) {
        reviseMsg.forEach {
            var start = msg.indexOf(it)
            var end = start + it.length
            this.setSpan(AbsoluteSizeSpan(sp2px(textSize)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return this
    }
    return null
}

/**
 * 添加icon
 */
@SuppressLint("UseCompatLoadingForDrawables")
fun SpannableString.startIconSpan(context: Context, msg: String, reviseMsg: String, reviseColor: Int, icon: Int): SpannableString? {

    if (msg.noEmpty()) {
        var start = msg.indexOf(reviseMsg)
        var end = start + reviseMsg.length

        val image: Drawable? = ContextCompat.getDrawable(context, icon) // 获取图片资源
        image?.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)

        val imageSpan = ImageSpan(image!!) // 图片样式

        this.setSpan(ForegroundColorSpan(reviseColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        this.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return this
    }
    return null
}


