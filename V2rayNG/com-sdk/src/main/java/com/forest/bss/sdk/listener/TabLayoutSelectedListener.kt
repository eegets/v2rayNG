package com.forest.bss.sdk.listener

import com.forest.bss.sdk.log.logger
import com.google.android.material.tabs.TabLayout

/**
 * Created by wangkai on 2021/01/29 10:43

 * Desc TabLayout选择提供监听器
 */

open class TabLayoutSelectedListener : TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) {
        logger {
            "onTabReselected tab=$tab; position=${tab?.position}"
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        logger {
            "onTabUnselected tab=$tab; position=${tab?.position}"
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        logger {
            "onTabSelected tab=$tab; position=${tab?.position}"
        }
    }
}