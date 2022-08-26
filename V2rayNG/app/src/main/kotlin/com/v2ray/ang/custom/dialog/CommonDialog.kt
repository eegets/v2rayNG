package com.v2ray.ang.custom.dialog

import android.os.Bundle
import android.text.SpannableString
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.forest.bss.sdk.ext.showDialogSafely
import com.forest.bss.sdk.ext.textSpan
import com.v2ray.ang.R
import com.v2ray.ang.databinding.CustomCommonDialogBinding

class CommonDialog : AbsDialogFragment() {

    var binding: CustomCommonDialogBinding? = null

    override fun isEnableViewBinding(): Boolean = true

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = CustomCommonDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override val layoutRes: Int
        get() = R.layout.custom_bottom_shell_dialog

    override fun bindView(v: View?) {
        setGravity(Gravity.CENTER)

        val string = arguments?.getString(STRING_DATA)
        val reviseMsg = arguments?.getString(STRING_REVISE)
//        val msg1 = "测俺们仨上课，堪萨斯梦想啊几次阿森纳是你擦是从哪说你擦拭就能擦就是从你家啊就那时大家按时缴纳说"
//        val msg2 = "啊就那时"
        if (reviseMsg?.isNotEmpty() == true) {
            SpannableString(string).let {
                it.textSpan(string!!, reviseMsg, resources.getColor(R.color.colorPingRed))
                it
            }.apply {
                binding?.content?.text = this
            }
        } else {
            binding?.content?.text = string
        }

        binding?.ok?.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val STRING_DATA = "string"
        private const val STRING_REVISE = "reviseMsg"

        fun newInstance(string: String, reviseMsg: String? = null): CommonDialog {
            val fragment = CommonDialog()
            val args = Bundle()
            args.putSerializable(STRING_DATA, string)
            args.putString(STRING_REVISE, reviseMsg)
            fragment.arguments = args
            return fragment
        }
    }
}



