package com.v2ray.ang.custom.adapter

import android.content.Context
import android.view.LayoutInflater
import com.forest.bss.sdk.base.adapter.recy.BaseRecvAdapter
import com.forest.bss.sdk.base.adapter.recy.holder.ItemBindingHolder
import com.forest.bss.sdk.base.adapter.recy.holder.ItemHolder
import com.v2ray.ang.custom.data.entity.OrderBean
import com.v2ray.ang.databinding.CustomActivityOrderItemBinding

class OrderAdapter(private val context: Context) : BaseRecvAdapter<OrderBean>(context) {
    override fun createItemHolder(viewType: Int): ItemHolder<OrderBean> {
        return Holder(context)
    }

    inner class Holder(val context: Context): ItemBindingHolder<OrderBean, CustomActivityOrderItemBinding>(context) {
        override fun setViewBinding(inflater: LayoutInflater): CustomActivityOrderItemBinding {
            return CustomActivityOrderItemBinding.inflate(inflater, parent, false)
        }

        override fun bindView(data: OrderBean, position: Int) {
            binding?.numberText?.text = data.card_no
            binding?.orderStatus?.text = "订单状态：${data.status}"
            binding?.numberStartTime?.text = "使用时间：${data.create_time}"
            binding?.numberStartEnd?.text = "到期日期：${data.end_date}"
            binding?.orderExceedDay?.text = "增加天数：${data.exday}"

        }
    }
}