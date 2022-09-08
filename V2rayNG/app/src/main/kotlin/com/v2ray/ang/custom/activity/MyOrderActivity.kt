package com.v2ray.ang.custom.activity

import com.forest.bss.sdk.base.act.BaseViewBindingActivity
import com.forest.bss.sdk.ext.makeGone
import com.forest.bss.sdk.ext.makeVisible
import com.forest.bss.sdk.ext.viewModel
import com.forest.net.data.success
import com.v2ray.ang.custom.adapter.OrderAdapter
import com.v2ray.ang.custom.data.entity.OrderBean
import com.v2ray.ang.custom.data.model.OrderModel
import com.v2ray.ang.custom.dialog.LoadingUtils
import com.v2ray.ang.databinding.CustomActivityOrderBinding

class MyOrderActivity : BaseViewBindingActivity<CustomActivityOrderBinding>(){

    private val model: OrderModel by lazy {
        viewModel(OrderModel::class.java)
    }
    private val loading: LoadingUtils by lazy { LoadingUtils() }

    override fun initView() {
        binding?.back?.setOnClickListener {
            finish()
        }
        loading.show(this)
        model.queryOrderLists()

    }

    override fun viewModelObserve() {
        super.viewModelObserve()
        model.liveDataOrderLists.observe(this) {
            loading.hide()
            if (it.success()) {
                if (it.getOrNull()?.results?.size!! > 0) {
                    binding?.empty?.makeGone()
                    val adapter = OrderAdapter(this)
                    binding?.recyclerView?.adapter = adapter
                    adapter.data = it.getOrNull()?.results
                } else {
//                    binding?.empty?.makeVisible()
                    val aaa: MutableList<OrderBean> = mutableListOf()
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "2121321", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "2121312321", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "324342", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "32432423", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    aaa.add(OrderBean(1, card_no = "10292922", create_time = "2011-12-12", end_date = "2011-12-30", exday = "30", status = "已付费"))
                    binding?.empty?.makeGone()
                    val adapter = OrderAdapter(this)
                    binding?.recyclerView?.adapter = adapter
                    adapter.data = aaa
                }
            } else {
              binding?.empty?.makeVisible()
            }
        }
    }

    override fun bindingLayout(): CustomActivityOrderBinding {
        return CustomActivityOrderBinding.inflate(layoutInflater)
    }
}