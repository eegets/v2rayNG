package com.forest.bss.sdk.util

import java.lang.Exception
import kotlin.math.roundToInt

/**
 * Created by wujingyue on 2021/05/29 17:49

 * Desc TODO
 */
class Number {
    companion object {
        fun doubleToPercentage(d: Double?): String {
            return try {
                val dd = ((d ?: 0.0) * 1000).roundToInt() / 10.0
                val tempD = dd.toString()
                val splitD = tempD.split(".")
                if (splitD[1] == "0") {
                    splitD[0] + "%"
                } else {
                    "$tempD%"
                }
            } catch (e: Exception) {
                "100%"
            }
        }

        fun stringToShowDouble(s: String): String {
            return try {
                val splitD = s.split(".")
                if (splitD[1] == "0") {
                    splitD[0]
                } else {
                    s
                }
            } catch (e:Exception) {
                "0"
            }
        }
    }
}