package com.v2ray.ang.custom.activity

import android.os.Bundle
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.forest.bss.sdk.ext.viewModel
import com.v2ray.ang.R
import com.v2ray.ang.custom.data.entity.UserInfoBean
import com.v2ray.ang.custom.data.model.BuyModel

/**
 * Created by wangkai on 2021/05/31 14:11

 * Desc TODO
 */


class BuyActivity : AppCompatActivity() {

    private val buySubmit by lazy {
        findViewById<TextView>(R.id.buySubmit)
    }

    private val buyEditText by lazy {
        findViewById<TextView>(R.id.buyEditText)
    }

    private val buyAccount by lazy {
        findViewById<TextView>(R.id.buyAccount)
    }

    private val buyEndDate by lazy {
        findViewById<TextView>(R.id.buyEndDate)
    }

    private val model: BuyModel by lazy {
        viewModel(BuyModel::class.java)
    }

    private var loginBean: UserInfoBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_activity_buy)

        loginBean = intent?.getSerializableExtra("loginBean") as UserInfoBean?

        buyAccount?.text = "账户：${loginBean?.username}"

        buyEndDate?.text = "VIP会员 于${loginBean?.end_date}日到期"

        buySubmit?.setOnClickListener {
            val buyEditTextString = buyEditText?.text?.toString()
            if (buyEditTextString.isNullOrEmpty()) {
                Toast.makeText(this, "请输入卡号", Toast.LENGTH_LONG).show()
            } else {
                model.buy("", "", buyEditTextString)
            }
        }

//        model.buyBean.observe(this, Observer {
//            Log.d("TAG", "buyEntitiy success: $it")
//            if (it?.code == 200) {
//                //跳转到首页
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("buyBean", it)
//                startActivity(intent)
//            } else {
//                Toast.makeText(this, it?.msg, Toast.LENGTH_LONG).show()
//            }
//        })
//
//        model.buyError.observe(this, Observer {
//            Log.d("TAG", "buyEntitiy error: $it")
//            Toast.makeText(this, it?.message, Toast.LENGTH_LONG).show()
//        })
    }
}
