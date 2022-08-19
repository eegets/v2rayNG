package com.forest.bss.sdk.listener

import android.text.Editable
import android.text.TextWatcher
import com.forest.bss.sdk.log.logger

/**
 * Created by wangkai on 2021/01/30 10:31

 * Desc TextView事件监听
 */

open class YqTextChangeListener : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        logger {
            "afterTextChanged s=$s"
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        logger {
            "beforeTextChanged s=$s; start=$start; after=$after; count=$count"
        }
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        logger {
            "onTextChanged s=$s; start=$start; before=$before; count=$count"
        }
    }

}