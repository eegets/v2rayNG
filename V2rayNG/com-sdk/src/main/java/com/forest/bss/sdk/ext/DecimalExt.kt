package com.forest.bss.sdk.ext

import android.nfc.FormatException
import com.forest.bss.sdk.BuildConfig
import com.forest.bss.sdk.log.logger
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by wangkai on 2021/03/09 13:38

 * Desc 加、减、乘、除 高精度计算工具类
 */

// 需要精确至小数点后几位
const val DECIMAL_POINT_NUMBER:Int = 2

// 加法运算
fun add(d1:Double,d2:Double):Double = BigDecimal(d1).add(BigDecimal(d2)).setScale(DECIMAL_POINT_NUMBER,BigDecimal.ROUND_DOWN).toDouble()

// 减法运算
fun sub(d1:Double,d2: Double):Double = BigDecimal(d1).subtract(BigDecimal(d2)).setScale(DECIMAL_POINT_NUMBER,BigDecimal.ROUND_DOWN).toDouble()

// 乘法运算
fun mul(d1:Double,d2: Double,decimalPoint:Int):Double = BigDecimal(d1).multiply(BigDecimal(d2)).setScale(decimalPoint,BigDecimal.ROUND_DOWN).toDouble()

// 除法运算
fun div(d1:Double,d2: Double):Double = BigDecimal(d1).divide(BigDecimal(d2)).setScale(DECIMAL_POINT_NUMBER,BigDecimal.ROUND_DOWN).toDouble()

/**
 * 校验String是否是Double类型
 */
fun String.verifyIsDouble(): Double {
    return if (this.matches("-?\\d+(\\.\\d+)?".toRegex())) {
        logger {
            "Decimal string is number"
        }
        this.toDouble()
    } else {
        logger {
            "Decimal string is not number"
        }
        if (BuildConfig.DEBUG) {
            throw FormatException("pattern convert is not number")
        }
        0.0
    }
}

/**
 * 小数格式化
 * [patternStr]
 */
fun String.format(patternStr: String = "0.00", roundingMode: RoundingMode = RoundingMode.HALF_UP): String? {
    if (this.matches("-?\\d+(\\.\\d+)?".toRegex())) {
        logger {
            "Decimal string is number"
        }
        val decimalFormat = DecimalFormat(patternStr).apply {
            this.roundingMode = roundingMode
        }
        return getValueSafely {
            decimalFormat.format(this.toDouble())
        }
    } else {
        logger {
            "Decimal string is not number"
        }
        if (BuildConfig.DEBUG) {
            throw FormatException("pattern convert is not number, str: $this")
        }
        return null
    }
}

fun String.format(): String? {
    if (this.matches("-?\\d+(\\.\\d+)?".toRegex())) {
        logger {
            "Decimal string is number"
        }
        return if (!this.contains(".")) {
            getValueSafely {
                this
            }
        } else {
            val splitString = this.split(".")
            if (splitString[1].toInt() <= 0) {
                return getValueSafely {
                    splitString[0]
                }
            } else {
                this
            }
        }
    } else {
        logger {
            "Decimal string is not number"
        }
        if (BuildConfig.DEBUG) {
            throw FormatException("pattern convert is not number")
        }
        return null
    }
}