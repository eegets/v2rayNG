package com.forest.bss.sdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.forest.bss.sdk.ext.asFragmentActivity
import java.lang.ref.WeakReference

object ApplicationLifeManager {
    private var currentActivityWeakRef: WeakReference<Activity>? = null
    private val activityCallbacks = mutableMapOf<Int, List<Application.ActivityLifecycleCallbacks>>()


    fun observeActivityCallback(activity: Activity, callback: Application.ActivityLifecycleCallbacks) {
        activityCallbacks[activity.hashCode()] = getActivityCallback(activity).plus(callback)
    }

    private fun getActivityCallback(activity: Activity?): List<Application.ActivityLifecycleCallbacks> {
        return if (activity == null) {
            listOf()
        } else {
            activityCallbacks[activity.hashCode()] ?: listOf()
        }
    }

    private fun removeActivityCallbacks(activity: Activity?): Boolean {
        return if (activity == null) {
            false
        } else {
            activityCallbacks.remove(activity.hashCode()) != null
        }
    }

    fun getCurrentActivity(): Activity? = currentActivityWeakRef?.get()

    fun getCurrentFragmentActivity(): FragmentActivity? = getCurrentActivity()?.asFragmentActivity()

    fun registerApplication(application: Application) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                getActivityCallback(activity).forEach {
                    it.onActivityPaused(activity)
                }
            }

            override fun onActivityResumed(activity: Activity) {
                activity?.run {
                    currentActivityWeakRef = WeakReference(activity)
                    getActivityCallback(activity).forEach {
                        it.onActivityResumed(activity)
                    }
                }
            }

            override fun onActivityStarted(activity: Activity) {
                getActivityCallback(activity).forEach {
                    it.onActivityStarted(activity)
                }
                activity?.let(AppStateHelper.getInstance()::activityStarting)
            }

            override fun onActivityDestroyed(activity: Activity) {
                getActivityCallback(activity).forEach {
                    it.onActivityDestroyed(activity)
                }
                removeActivityCallbacks(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                getActivityCallback(activity).forEach {
                    it.onActivitySaveInstanceState(activity, outState)
                }
            }

            override fun onActivityStopped(activity: Activity) {
                getActivityCallback(activity).forEach {
                    it.onActivityStopped(activity)
                }
                activity?.let(AppStateHelper.getInstance()::activityStopping)
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                getActivityCallback(activity).forEach {
                    it.onActivityCreated(activity, savedInstanceState)
                }
            }

        })
    }
}