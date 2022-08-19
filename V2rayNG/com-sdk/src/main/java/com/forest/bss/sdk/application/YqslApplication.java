package com.forest.bss.sdk.application;

import android.app.Activity;
import android.content.Context;

import com.forest.bss.sdk.AppStateHelper;
import com.forest.bss.sdk.BuildConfig;
import com.forest.bss.sdk.Observer;
import com.forest.bss.sdk.delegate.AppDelegate;
import com.forest.bss.sdk.log.LogUtils;
import com.forest.net.app.NetApplication;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class YqslApplication extends NetApplication {

    private AppDelegate appDelegate;

    private boolean isBackground = true;  //应用是否在后台，默认在后台

    @Override

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (appDelegate == null) {
            appDelegate = new AppDelegate();
        }
        appDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (appDelegate != null) {
            appDelegate.onCreate(this);
        }
        try {
            registerAppStateCallback();
            registerCustomCrashHandler();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

    /**
     * 应用切换到前后台
     */
    private void registerAppStateCallback() {
        AppStateHelper.getInstance().addObserver(new Observer<AppStateHelper, AppStateHelper.Message, Activity>() {
            @Override
            public void notify(AppStateHelper observable, AppStateHelper.Message msg, Activity arg) {
                LogUtils.logger("registerAppStateCallback msg=" + msg + ";activity=" + arg);
                if (msg == AppStateHelper.Message.BACKGROUNDED) {
                    isBackground = true;
                } else if (msg == AppStateHelper.Message.FOREGROUNDED) {
                    isBackground = false;
                }
            }
        });
    }


    /**
     * 无感知处理后台崩溃
     */
    private void registerCustomCrashHandler() {
        Thread.UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                if (isBackground) { //应用如果在后台,则在后台无感知的杀掉当前应用
                    if (!BuildConfig.DEBUG) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } else {
                        defaultHandler.uncaughtException(thread, throwable);
                    }
                } else {
                    defaultHandler.uncaughtException(thread, throwable);
                }
            }
        });
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (appDelegate != null) {
            appDelegate.onTerminate(this);
        }
    }

}
