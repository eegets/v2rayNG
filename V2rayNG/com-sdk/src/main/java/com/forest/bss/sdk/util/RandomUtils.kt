package com.forest.bss.sdk.util

import java.util.*

/**
 * Created by wangkai on 2021/11/23 18:16

 * Desc TODO
 */
object RandomUtils {
    /**
     * 随机`seed`位数的随机数，默认 seed = 2
     */
    fun randomAlphabetic(seed: Int = 2): String {
        var sb = StringBuffer()
         Random().let { random ->
                for (i in 1..seed) {
                    val randNum: Int = random.nextInt(9) + 1
                    val num = randNum.toString() + ""
                    sb = sb.append(num)
            }
            return sb.toString()
        }
    }
}