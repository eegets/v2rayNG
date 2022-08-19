@file: JvmName("FragmentUtil")

package com.forest.bss.sdk.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.forest.bss.sdk.log.logger
import com.forest.bss.sdk.type_alias.FragmentToUnit

inline fun <reified VM : ViewModel> Fragment?.viewModel(): VM? {
    return this.getViewModel(VM::class.java)
}

/**
 * 方便java使用
 */
fun <VM : ViewModel> Fragment?.getViewModel(clazz: Class<VM>): VM? {
    return when {
        this == null -> null
        this.activity == null -> null
        else -> {
            getValueSafely {
                ViewModelProvider(this).get(clazz)
            }
        }
    }
}

/**
 * 将Fragment实例从其对应的FragmentManager中移除。
 */
fun Fragment.remove() {
    fragmentManager?.beginTransaction()?.remove(this)?.commitAllowingStateLoss()
}

fun Fragment.show(fragmentManager: FragmentManager?, tag: String = this::class.java.simpleName): Fragment {
    return this.apply {
        safeRun {
            fragmentManager?.beginTransaction()?.add(this, tag)?.commitAllowingStateLoss()
        }
    }
}

fun Fragment.show(fragmentManager: FragmentManager?, viewId: Int): Fragment {
    return this.apply {
        safeRun {
            fragmentManager?.beginTransaction()?.add(viewId, this)?.commitNow()
        }
    }
}

fun Fragment.remove(fragmentManager: FragmentManager?): Fragment {
    return this.apply {
        safeRun {
            fragmentManager?.beginTransaction()?.remove(this)?.commitNow()
        }
    }
}

/**
 * 安全添加fragment,如果已经添加了，则不执行操作，避免异常出现
 */
fun Fragment.showSafely(fragmentManager: FragmentManager, tag: String = this::class.java.simpleName) {
    safeRun {
        if (!isAdded && fragmentManager.isFragmentNotAdded(this)) {
            show(fragmentManager, tag)
            fragmentManager.executePendingTransactions()
            logger { "Fragment.showSafely fragment=$this;tag=$tag" }
        }
    }
}

/**
 * 判断Fragment是否已经添加到fragmentManager
 */
fun FragmentManager.isFragmentAdded(fragment: Fragment?): Boolean {
    return fragments.contains(fragment)
}

/**
 * 判断Fragment是否没有添加到fragmentManager
 */
fun FragmentManager.isFragmentNotAdded(fragment: Fragment?): Boolean = !isFragmentAdded(fragment)

fun Fragment.onDestroy(action: (Fragment) -> Unit) {
    this.fragmentManager?.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            if (f == this@onDestroy) {
                f.let(action)
                //移除回调，已经不再需要（仅限自己的fragment销毁时处理）
                fm?.unregisterFragmentLifecycleCallbacks(this)
            }
        }
    }, false)
}

fun Fragment.finish() {
    activity?.onBackPressed()
}

fun Fragment?.doOnDestroy(runnable: FragmentToUnit? = null) {
    this ?: return
    runnable ?: return
    this.fragmentManager?.registerFragmentLifecycleCallbacks(object: FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            super.onFragmentDestroyed(fm, f)
            if (f == this@doOnDestroy) {
                runnable.invoke(f)
                fm.unregisterFragmentLifecycleCallbacks(this)
            }
        }
    }, false)
}