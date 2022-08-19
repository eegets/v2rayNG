package com.forest.bss.sdk.base.act

import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.KeyboardUtils

/**
 * Created by wangkai on 2021/03/09 15:39

 * Desc 事件拦截基础类
 */
abstract class BaseKeyBoardActivity : AppCompatActivity() {

    /**
     * 拦截事件，当键盘弹出时，点击空白区域是否隐藏键盘
     */
    abstract fun dispatchKeyBoard() : Boolean

    /**
     * 获取点击事件
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!dispatchKeyBoard()) {
            return super.dispatchTouchEvent(ev)
        }
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isShouldHideKeyBoard(view, ev)) {
                KeyboardUtils.hideSoftInput(this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 判定当前是否需要隐藏
     */
    private fun isShouldHideKeyBoard(v: View?, ev: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom)
        }
        return false
    }
}