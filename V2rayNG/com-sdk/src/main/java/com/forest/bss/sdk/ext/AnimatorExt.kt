package com.forest.bss.sdk.ext

import android.animation.Animator
import com.forest.bss.sdk.impl.AnimatorListenerImpl

fun Animator.withListener(listener: Animator.AnimatorListener): Animator {
    return this.also {
        it.addListener(listener)
    }
}

fun Animator.doOnAnimationEnd(block: (() -> Unit)?): Animator {
    return this.withListener(object: AnimatorListenerImpl() {
        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
           block?.let(::safeRun)
        }
    })
}