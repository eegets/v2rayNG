package com.forest.bss.sdk.util

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText
import android.widget.TextView

/**
 * Created by wujingyue on 2021/10/11 10:10
 * Desc 输入框的限制
 */



enum class InputRuleType { INT, DOUBLE }

/**
 * limitRules 输入的规则是，一个数组，数组里面的每一个元素
 * 是 Pair 对象，Pair 里面的第一个值是代表类型输入规则类型，
 * 第二个代表限制的值
 */
fun EditText.setLimit(limitRules: List<Pair<InputRuleType, Any>>) {
    val arr = mutableListOf<InputFilter?>()
    limitRules.forEach {
        arr.add(when (it.first) {
            InputRuleType.INT -> if (it.second is Int) limitLength(it.second as Int) else null
            InputRuleType.DOUBLE -> if (it.second is Double) limitDoubleDigit(it.second as Double) else null
        })
    }
    filters = arr.toTypedArray()
}

/**
 * 只限制长度
 * 传入的规则为：5，代表输入的总个数不能超过5位
 */
fun limitLength(inputLimit: Int): InputFilter = InputFilter.LengthFilter(inputLimit)

/**
 * 限制小数，可以限制小数的整数部分和小数部分
 * 传入的规则为：2.5 代表整数部分输入的最大位数是2，小数部分的最大位数5位
 */
fun limitDoubleDigit(digit: Double): InputFilter {
    val digitStr = digit.toString()
    val digitArr = digitStr.split(".")
    return InputFilter { source, start, end, dest, dstart, dend ->
        if (digitArr.isEmpty()) ""
        else if (digitArr.size == 1) {
            InputFilter.LengthFilter(digitArr[0].toInt()).filter(source, start, end, dest, dstart, dend)
        } else {
            val destArr = dest.split(".")
            if (dstart == 0 && source.contains(".")) {
                "0."
            } else if (destArr.size == 1 && !source.contains(".")) {
                InputFilter.LengthFilter(digitArr[0].toInt()).filter(source, start, end, dest, dstart, dend)
            } else {
                val totalLength = try {
                    destArr[0].length + digitArr[1].toInt() + 1
                } catch (e: Exception) {
                    digitArr[0].toInt()
                }
                InputFilter.LengthFilter(totalLength).filter(source, start, end, dest, dstart, dend)
            }
        }
    }
}