package com.v2ray.ang.custom.dialog

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.forest.bss.sdk.log.logger
import com.forest.net.ext.safeRun
import com.v2ray.ang.R

/**
 * 通用基础类弹框
 */
abstract class AbsDialogFragment : DialogFragment() {
    private var dismissListener: DismissListener? = null
    private var onDismissListener: OnDismissListener?= null
    private var gravity = Gravity.BOTTOM
    private var style = 0
    private var cancelOutside = true
    private var height = 0
    private var width = 0
    private var isDialogClick = false //是否是遮照空白处的点击

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val dialogFragmentWindow: Window? = dialog?.window
        val params = dialogFragmentWindow?.attributes.let {
            it?.dimAmount = dimAmount
            it?.gravity = gravity
            it
        }
        dialogFragmentWindow?.attributes = params
        dialogFragmentWindow?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(cancelOutside)
        isCancelable = true

        if (isClearStatusBarWhite()) {
            /**
             * 问题描述：
             * 当 Activity 设置状态栏白色背景，黑色字体时，
             * 弹出 dialog 默认时有一个半透明的黑色蒙层，
             * 此时手机默认把顶部状态栏的图标和文字颜色修改为了白色，
             * 但是当 dialog 设置为全透明的蒙层时，经测试某些手机默认同样会把顶部状态栏改为白色，
             * 此时看顶部状态栏的文字和图标消失了。
             */
            dialog?.window?.apply {
                clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
        }

        if (0 >= style) setStyle(STYLE_NO_TITLE, R.style.public_Bottom_Dialog) else dialogFragmentWindow?.setWindowAnimations(style)
        return if (isEnableViewBinding()) {
            viewBinding(inflater, container)
        } else {
            inflater.inflate(layoutRes, container, false)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        logger("Dialog onViewStateRestored savedInstanceState: $savedInstanceState")
        if (savedInstanceState != null) {
            dismissAllowingStateLoss()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView(view)
    }

    override fun onResume() {
        super.onResume()
        width = if (width > 0) width else WindowManager.LayoutParams.MATCH_PARENT
        height = if (height > 0) height else WindowManager.LayoutParams.WRAP_CONTENT
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setGravity(gravity)
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    override fun onDestroy() {
        super.onDestroy()
        safeRun {
            dismissAllowingStateLoss()
        }
    }


    abstract fun isEnableViewBinding(): Boolean

    abstract fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): View?

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun bindView(v: View?)


    fun setStyle(style: Int) {
        this.style = style
    }

    fun setGravity(gravity: Int) {
        this.gravity = gravity
    }

    fun setHeight(height: Int){
        this.height = height
    }

    fun setWidth(width: Int){
        this.width = width
    }
    /**
     * 点击外部区域是否消失弹框
     */
    fun setCancelOutsides(isCancelOutside: Boolean) {
        cancelOutside = isCancelOutside
    }

    fun show(fragmentManager: FragmentManager) {
        if (!this.isAdded && !this.isVisible && !this.isRemoving) {
            val ft = fragmentManager.beginTransaction()
            ft.add(this, fragmentTag)
            ft.commitAllowingStateLoss()
            isDialogClick = false
        }
    }


    fun setShadeClick(isDialogClick:Boolean) {
        this.isDialogClick = isDialogClick
    }

    /**
     * 监听弹框消失
     *
     * @param dismissListener
     */
    fun setOnDismissListener(dismissListener: DismissListener?): AbsDialogFragment {
        this.dismissListener = dismissListener
        return this
    }
    /**
     * 监听弹框消失
     *
     * @param dismissListener
     */
    fun setOnDismissListener(onDismissListener: OnDismissListener?): AbsDialogFragment {
        this.onDismissListener = onDismissListener
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.onDismiss()
        onDismissListener?.onDismiss(isDialogClick)

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismissListener?.onDismiss()
        onDismissListener?.onDismiss(isDialogClick)
    }

    /**
     * 当设置为 true 的时候就可以解决状态栏白板的问题
     */
    open fun isClearStatusBarWhite() = false

    interface OnDismissListener {
        fun onDismiss(isDialogClick:Boolean)
    }

    interface DismissListener {
        fun onDismiss()
    }

    companion object {
        const val fragmentTag = "base_bottom_dialog"
        const val dimAmount = 0.2f
    }
}
