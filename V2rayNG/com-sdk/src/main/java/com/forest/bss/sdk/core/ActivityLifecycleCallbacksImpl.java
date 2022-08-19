package com.forest.bss.sdk.core;

import android.app.Activity;
import android.app.Application;

import android.os.Bundle;
import androidx.annotation.NonNull;

import com.forest.bss.sdk.log.LogUtils;

/**
 * Created by wangkai on 2021/05/06 11:04
 * <p>
 * Desc TODO
 */

public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        LogUtils.logger("onActivityCreated");
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        LogUtils.logger("onActivityStarted");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        LogUtils.logger("onActivityResumed");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        LogUtils.logger("onActivityPaused");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        LogUtils.logger("onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        LogUtils.logger("onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        LogUtils.logger("onActivityDestroyed");
    }
}
