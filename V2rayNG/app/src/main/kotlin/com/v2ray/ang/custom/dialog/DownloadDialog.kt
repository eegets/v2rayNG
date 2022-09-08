package com.v2ray.ang.custom.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.forest.bss.sdk.ext.asType
import com.forest.bss.sdk.toast.ToastExt
import com.v2ray.ang.R
import com.v2ray.ang.custom.data.entity.UpdateBean
import com.v2ray.ang.databinding.CustomDownloadDialogBinding

class DownloadDialog : AbsDialogFragment() {

    var binding: CustomDownloadDialogBinding? = null

    override fun isEnableViewBinding(): Boolean = true

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = CustomDownloadDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override val layoutRes: Int
        get() = R.layout.custom_bottom_shell_dialog

    override fun bindView(v: View?) {
        setGravity(Gravity.CENTER)

        val intentData = arguments?.getString(INTENT_DATA)?.asType<UpdateBean>()
        binding?.content?.text = intentData?.update_detail

        binding?.cancel?.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding?.down?.setOnClickListener {
            ToastExt.show("下载")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val INTENT_DATA = "intentData"

        fun newInstance(intentData: UpdateBean): DownloadDialog {
            val fragment = DownloadDialog()
            val args = Bundle()
            args.putSerializable(INTENT_DATA, intentData)
            fragment.arguments = args
            return fragment
        }
    }
}



