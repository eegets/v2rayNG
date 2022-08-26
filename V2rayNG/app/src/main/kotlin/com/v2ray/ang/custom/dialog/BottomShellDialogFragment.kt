package com.v2ray.ang.custom.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.forest.bss.sdk.ext.appContext
import com.forest.bss.sdk.ext.asType
import com.v2ray.ang.R
import com.v2ray.ang.custom.adapter.BottomShellDialogAdapter
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.databinding.CustomBottomShellDialogBinding

/**
 * Created by wangkai on 2021/07/20 18:06

 * Desc 顶部弹出框
 */
class BottomShellDialogFragment : AbsDialogFragment() {

    var binding: CustomBottomShellDialogBinding? = null

    private var adapter: BottomShellDialogAdapter? = null

    var selectSModeListener: ((Int, String) -> Unit)? = null

    override fun isEnableViewBinding(): Boolean = true

    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?): View? {
        binding = CustomBottomShellDialogBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override val layoutRes: Int
        get() = R.layout.custom_bottom_shell_dialog

    override fun bindView(v: View?) {
        setGravity(Gravity.BOTTOM)

        adapter = BottomShellDialogAdapter(context ?: appContext)

        binding?.recyclerView?.adapter = adapter

        arguments?.getSerializable(LIST_DATA)?.asType<List<HomeBean.Host>>().apply {
            adapter?.data = this
        }

        adapter?.setOnItemClickListener { v, data, position ->
            val bean = data.asType<HomeBean.Host>()
            if (bean != null) {
                selectSModeListener?.invoke(bean.id, bean.title)
            }
            dismissAllowingStateLoss()
        }

        binding?.cancel?.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val LIST_DATA = "listData"

        fun newInstance(listData: List<HomeBean.Host>? = mutableListOf()): BottomShellDialogFragment {
            val fragment = BottomShellDialogFragment()
            val args = Bundle()
            args.putSerializable(LIST_DATA, listData?.asType())
            fragment.arguments = args
            return fragment
        }
    }
}



