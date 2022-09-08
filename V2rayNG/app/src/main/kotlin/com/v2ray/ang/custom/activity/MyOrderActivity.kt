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
                    binding?.empty?.makeVisible()
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