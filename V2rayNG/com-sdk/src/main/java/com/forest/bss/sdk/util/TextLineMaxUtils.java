package com.forest.bss.sdk.util;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

/**
 * Created by wangkai on 2021/04/19 15:43
 * <p>
 * Desc TODO
 */
public class TextLineMaxUtils {
    /**
     * 获取textview一行最大能显示几个字(需要在TextView测量完成之后)
     *
     * @param text     文本内容
     * @param paint    textview.getPaint()
     * @param maxWidth textview.getMaxWidth()/或者是指定的数值,如200dp
     */
    public static int getLineMaxNumber(String text, TextPaint paint, int maxWidth) {
        if (null == text || "".equals(text)) {
            return 0;
        }
        StaticLayout staticLayout = new StaticLayout(text, paint, maxWidth, Layout.Alignment.ALIGN_NORMAL
                , 1.0f, 0, false);
        //获取第一行最后显示的字符下标
        return staticLayout.getLineEnd(0);
    }
}
