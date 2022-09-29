package com.v2ray.ang.custom.dialog

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.forest.bss.sdk.ext.asType
import com.forest.bss.sdk.ext.makeGone
import com.forest.bss.sdk.ext.makeVisible
import com.forest.bss.sdk.toast.SnackBarExt
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

        if (intentData?.update_type == "强制更新") {
            binding?.cancel?.makeGone()
        } else {
            binding?.cancel?.makeVisible()
        }

        binding?.cancel?.setOnClickListener {
            dismissAllowingStateLoss()
        }

        binding?.down?.setOnClickListener {
            openBrowser(requireContext(), intentData?.download_url)
        }
    }

    /**
     * 调用第三方浏览器打开
     * @param context
     * @param url 要浏览的资源地址
     */
    fun openBrowser(context: Context, url: String?) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        if (intent.resolveActivity(context.packageManager) != null) {
            val componentName: ComponentName = intent.resolveActivity(context.packageManager)
            // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"))
        } else {
            binding?.content?.let { SnackBarExt.show(it, "请先下载浏览器") }
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



