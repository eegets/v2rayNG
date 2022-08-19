package com.v2ray.ang.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import com.forest.bss.sdk.base.adapter.recy.BaseRecvAdapter
import com.forest.bss.sdk.base.adapter.recy.holder.ItemBindingHolder
import com.forest.bss.sdk.base.adapter.recy.holder.ItemHolder
import com.v2ray.ang.custom.data.entity.HomeBean
import com.v2ray.ang.databinding.CustomBottomShellDialogItemBinding

class BottomShellDialogAdapter(private val context: Context) : BaseRecvAdapter<HomeBean.Host>(context) {
    override fun createItemHolder(viewType: Int): ItemHolder<HomeBean.Host> {
        return Holder(context)
    }

    class Holder(val context: Context): ItemBindingHolder<HomeBean.Host, CustomBottomShellDialogItemBinding>(context) {
        override fun setViewBinding(inflater: LayoutInflater): CustomBottomShellDialogItemBinding {
            return CustomBottomShellDialogItemBinding.inflate(inflater, parent, false)
        }

        override fun bindView(data: HomeBean.Host?, position: Int) {
            binding?.selectModeItem?.text = data?.title
        }

    }
}