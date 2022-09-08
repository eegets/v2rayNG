package com.v2ray.ang.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.CompoundButton
import com.forest.bss.sdk.base.adapter.recy.BaseRecvAdapter
import com.forest.bss.sdk.base.adapter.recy.holder.ItemBindingHolder
import com.forest.bss.sdk.base.adapter.recy.holder.ItemHolder
import com.v2ray.ang.custom.data.entity.AppInfoBean
import com.v2ray.ang.databinding.CustomActivityAppInfoItemBinding

class AppInfoAdapter(private val context: Context, val onSelectedListener: ((String, Boolean)-> Unit)) : BaseRecvAdapter<AppInfoBean>(context) {
    override fun createItemHolder(viewType: Int): ItemHolder<AppInfoBean> {
        return Holder(context)
    }



    inner class Holder(val context: Context): ItemBindingHolder<AppInfoBean, CustomActivityAppInfoItemBinding>(context) {
        override fun setViewBinding(inflater: LayoutInflater): CustomActivityAppInfoItemBinding {
            return CustomActivityAppInfoItemBinding.inflate(inflater, parent, false)
        }

        override fun bindView(data: AppInfoBean, position: Int) {
            binding?.appPackageName?.text = data.packageName
            binding?.appName?.text = data.appName
            binding?.appIcon?.setImageDrawable(data.appIcon)

            binding?.appSelect?.isChecked = data.isChecked

            binding?.appSelect?.setOnCheckedChangeListener { compoundButton, checked ->
                onSelectedListener.invoke(data.packageName, checked)

            }
        }
    }
}