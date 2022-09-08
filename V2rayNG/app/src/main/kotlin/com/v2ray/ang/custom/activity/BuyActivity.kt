package com.v2ray.ang.custom.activity

import android.widget.Toast
import com.forest.bss.sdk.base.act.BaseViewBindingActivity
import com.forest.bss.sdk.ext.viewModel
import com.forest.bss.sdk.toast.ToastExt
import com.forest.net.data.success
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.data.model.BuyModel
import com.v2ray.ang.custom.dialog.LoadingUtils
import com.v2ray.ang.databinding.CustomActivityBuyBinding

/**
 * Created by wangkai on 2021/05/31 14:11

 * Desc 续费
 */


class BuyActivity : BaseViewBindingActivity<CustomActivityBuyBinding>() {

    private var loginBean: UserInfoBean? = null

    private val model: BuyModel by lazy {
        viewModel(BuyModel::class.java)
    }
    private val buyLoading: LoadingUtils by lazy { LoadingUtils() }

    override fun initView() {
        loginBean = intent?.getSerializableExtra("userInfo") as UserInfoBean?

        binding?.buyAccount?.text = "账户：${loginBean?.username}"

        binding?.buyEndDate?.text = "VIP会员 于${loginBean?.end_date}日到期"

        binding?.back?.setOnClickListener {
            finish()
        }

        binding?.buySubmit?.setOnClickListener {
            val buyEditTextString = binding?.buyEditText?.text?.toString()
            if (buyEditTextString.isNullOrEmpty()) {
                Toast.makeText(this, "请输入卡号", Toast.LENGTH_LONG).show()
            } else {
                buyLoading.show(this)
                model.buy(buyEditTextString)
            }
        }
    }

    override fun viewModelObserve() {
        super.viewModelObserve()
        model.liveDataBuy.observe(this) {
            buyLoading.hide()
            if (it.success()) {
                ToastExt.show("已成功续费")
            } else {
                it.getOrNull()?.msg?.let { it1 -> ToastExt.show(it1) }
            }
        }
    }

    override fun bindingLayout(): CustomActivityBuyBinding {
        return CustomActivityBuyBinding.inflate(layoutInflater)
    }
}
