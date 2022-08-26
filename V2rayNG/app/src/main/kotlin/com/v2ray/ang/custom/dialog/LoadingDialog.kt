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
import com.v2ray.ang.databinding.CustomLoadingDialogBinding

class LoadingDialog : AbsDialogFragment() {

    var binding: CustomLoadingDialogBinding? = null

    override fun isEnableViewBinding(): Boolean = true

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = CustomLoadingDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override val layoutRes: Int
        get() = R.layout.custom_bottom_shell_dialog

    override fun bindView(v: View?) {
        setGravity(Gravity.CENTER)

        arguments?.getString(STRING_DATA)?.apply {
            binding?.loading?.text = this
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val STRING_DATA = "string"

        fun newInstance(string: String = ""): LoadingDialog {
            val fragment = LoadingDialog()
            val args = Bundle()
            args.putSerializable(STRING_DATA, string)
            fragment.arguments = args
            return fragment
        }
    }
}



