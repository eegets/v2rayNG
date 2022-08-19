package com.forest.bss.sdk.countdown

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.forest.bss.sdk.ext.doOnDestroy
import com.forest.bss.sdk.ext.onDestroy
import com.forest.bss.sdk.log.logger

fun View?.applyCountDownTimer(millisInFuture: Long?, countDownIntervalInMills: Long?,
                              timerCallback: CountDownTimerCallback?) {
    this ?: return
    CountDownTimerProvider.renewCountDownTimer(this, millisInFuture, countDownIntervalInMills,
            timerCallback)?.start()

    applyLifecycleForViewCountDownTimer(this)
}

fun View?.cancelCountDownTimer() {
    CountDownTimerProvider.cancelCountDownTimer(this)
}

private fun applyLifecycleForViewCountDownTimer(target: View) {
    target.addOnAttachStateChangeListener(object: View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(view: View?) {
            logger {
                "CountDownExt onViewDetachedFromWindow view=$view"
            }
            CountDownTimerProvider.cancelCountDownTimer(view)
            view?.removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(view: View?) {
            logger {
                "CountDownExt onViewAttachedToWindow view=$view"
            }
        }

    })
}

fun Activity?.applyCountDownTimer(millisInFuture: Long?, countDownIntervalInMills: Long?,
                                  timerCallback: CountDownTimerCallback?) {
    this ?: return
    CountDownTimerProvider.renewCountDownTimer(this, millisInFuture, countDownIntervalInMills, timerCallback)
            ?.start()
    this.onDestroy {
        CountDownTimerProvider.cancelCountDownTimer(this)
    }
}

fun Activity?.cancelCountDownTimer() {
    CountDownTimerProvider.cancelCountDownTimer(this)
}

fun Fragment?.applyCountDownTimer(millisInFuture: Long?, countDownIntervalInMills: Long?,
                                  timerCallback: CountDownTimerCallback?) {
    this ?: return
    CountDownTimerProvider.renewCountDownTimer(this, millisInFuture, countDownIntervalInMills, timerCallback)
            ?.start()
    this.doOnDestroy {
        CountDownTimerProvider.cancelCountDownTimer(this)
    }
}

fun Fragment?.cancelCountDownTimer() {
    CountDownTimerProvider.cancelCountDownTimer(this)
}