package com.v2ray.ang.custom.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.forest.bss.sdk.base.frag.BaseFragment
import com.forest.bss.sdk.ext.finish
import com.v2ray.ang.R
import com.v2ray.ang.custom.activity.BuyActivity
import com.v2ray.ang.custom.activity.LoginActivity
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.dataStore.UserInfoDataStore
import com.v2ray.ang.databinding.CustomFragmentMineBinding
import kotlinx.coroutines.launch

/**
 * Created by wangkai on 2021/05/01 10:53

 * Desc 个人中心
 */

class MineFragment : BaseFragment() {

    private var loginBean: UserInfoBean? = null

    private var binding: CustomFragmentMineBinding? = null

    override fun layoutId(): Int = R.layout.custom_fragment_mine
    override fun viewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CustomFragmentMineBinding.inflate(inflater)
        return binding?.root
    }

    override fun isEnableViewBinding(): Boolean = true
    override fun bindingView(rootView: View?) {
        loginBean = requireActivity().intent?.getSerializableExtra("loginBean") as UserInfoBean?
        binding?.buySubmit?.setOnClickListener {
            lifecycleScope.launch {
                UserInfoDataStore.clear()
                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


        binding?.mineItemFragment?.setOnClickListener {
            val intent = Intent(requireActivity(), BuyActivity::class.java)
            intent.putExtra("loginBean", loginBean)
            startActivity(intent)
        }
    }

    override fun bindViewModelObserve(rootView: View?) {

    }
}