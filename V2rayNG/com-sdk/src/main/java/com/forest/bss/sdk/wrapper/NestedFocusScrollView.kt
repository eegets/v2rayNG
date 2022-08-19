package com.forest.bss.sdk.wrapper

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView

/**
 * Created by wangkai on 2021/04/13 15:55

 * Desc 解决EditText在ScrollView中获取焦点自动滚动问题
 * https://blog.csdn.net/ZYJWR/article/details/108386309
 */

class NestedFocusScrollView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {
    override fun computeScrollDeltaToGetChildRectOnScreen(rect: Rect?): Int {
        return 0
    }
}