package com.forest.bss.sdk.countdown

import com.forest.bss.sdk.countdown.os.CountDownTimer
import com.forest.bss.sdk.log.logger
import java.util.*

object CountDownTimerProvider {
    private val timerMap = WeakHashMap<Any, CountDownTimer>()

    fun getCountDownTimer(key: Any?, millisInFuture: Long?, countDownIntervalInMills: Long?,
                          timerCallback: CountDownTimerCallback?): CountDownTimer? {
        if (key == null || millisInFuture == null || countDownIntervalInMills == null || timerCallback == null) {
            return null
        }

        return timerMap[key] ?: createCountDownTimer(millisInFuture, countDownIntervalInMills, timerCallback).also {
            timerMap[key] = it
        }
    }

    fun renewCountDownTimer(key: Any?, millisInFuture: Long?, countDownIntervalInMills: Long?,
                            timerCallback: CountDownTimerCallback?): CountDownTimer? {
        cancelCountDownTimer(key)
        return getCountDownTimer(key, millisInFuture, countDownIntervalInMills, timerCallback)
    }

    private fun createCountDownTimer(millisInFuture: Long, countDownIntervalInMills: Long,
                                     timerCallback: CountDownTimerCallback): CountDownTimer {

        return object : CountDownTimer(millisInFuture, countDownIntervalInMills) {
            override fun onFinish() {
                timerCallback.onFinish()
                logger {
                    "onFinish"
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                logger {
                    "onTick millisUntilFinished : $millisUntilFinished"
                }
                timerCallback.onTick(millisUntilFinished)
            }
        }
    }

    fun cancelCountDownTimer(key: Any?) {
        logger {
            "cancelCountDownTimer by key=$key"
        }
        key ?: return
        timerMap[key]?.cancel()
        timerMap.remove(key)
    }
}