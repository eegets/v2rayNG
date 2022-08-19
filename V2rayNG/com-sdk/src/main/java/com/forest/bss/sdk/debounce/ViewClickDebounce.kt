package com.forest.bss.sdk.debounce
import android.view.View
import com.forest.bss.sdk.ext.alsoWithLog
import com.forest.bss.sdk.log.logger
import java.util.*

/**
 * View防快速点击处理
 */
object ViewClickDebounce: View.OnAttachStateChangeListener {
    private const val THRESHOLD_DEBOUNCE_IN_MILLS = 500L
    private val viewClickDebounceMap: WeakHashMap<View, Debounce<View>> = WeakHashMap()

    /**
     * 获取View快速点击控制
     */
    private fun getViewDebounce(view: View?, thresholdInMills: Long? = THRESHOLD_DEBOUNCE_IN_MILLS): Debounce<View>? {
        view ?: return null

        return viewClickDebounceMap[view] ?: onCreateViewDebounce(view, thresholdInMills).also {
            viewClickDebounceMap[view] = it
        }
    }

    fun setupViewDebounceTimeout(view: View?, debounceTimeOutInMills: Long?) {
        view ?: return
        viewClickDebounceMap.remove(view)
        getViewDebounce(view, debounceTimeOutInMills)
    }

    private fun onCreateViewDebounce(view: View, thresholdInMills: Long?): Debounce<View> {
        view.addOnAttachStateChangeListener(this)

        val finalThreshold = thresholdInMills ?: THRESHOLD_DEBOUNCE_IN_MILLS
        return Debounce<View>(finalThreshold).alsoWithLog(this) {
            "createViewDebounce view=$view; finalThreshold=$finalThreshold"
        }
    }

    /**
     * 是否是快速点击
     */
    fun isFastClick(view: View?): Boolean {
        view ?: return false.alsoWithLog(this) {
            "isTooFast view is null;return false"
        }

        val debounce = getViewDebounce(view)
        debounce ?: return false.alsoWithLog(this) {
            "isTooFast debounce is null;return false"
        }

        return debounce.updateValue(view).not().alsoWithLog(this) {
            "isTooFast return $it;view=$view"
        }
    }

    override fun onViewDetachedFromWindow(view: View?) {
        logger {
            "onViewDetachedFromWindow view=$view"
        }
        view ?: return
        view.removeOnAttachStateChangeListener(this)
        viewClickDebounceMap.remove(view)
    }

    override fun onViewAttachedToWindow(view: View?) {
        logger {
            "onViewAttachedToWindow view=$view"
        }
    }
}