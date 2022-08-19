package com.v2ray.ang.custom.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.v2ray.ang.R

/**
 * Created by wangkai on 2021/05/01 10:27

 * Desc TODO
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置窗体为没有标题的模式
        setContentView(R.layout.custom_activity_main)
        val tabMonitor = TabMonitor(this)
        tabMonitor.setup()
    }
}