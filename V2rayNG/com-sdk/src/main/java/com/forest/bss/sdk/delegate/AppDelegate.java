package com.forest.bss.sdk.delegate;

import android.app.Application;
import android.content.Context;

//import com.alibaba.android.arouter.launcher.ARouter;
import com.forest.bss.sdk.ApplicationLifeManager;
import com.forest.bss.sdk.BuildConfig;
import com.forest.bss.sdk.EnvironmentConfig;
import com.forest.bss.sdk.lifecycles.AppLifeCycles;
import com.forest.bss.sdk.log.LogUtils;
import com.forest.net.app.App;

import java.lang.ref.WeakReference;

/**
 * Application生命周期代理
 */
public class AppDelegate implements AppLifeCycles {

    private static WeakReference<Context> appContext;

    public static Context getAppBaseContext() {
        if (appContext == null)
            return null;
        return appContext.get();
    }

    @Override
    public void attachBaseContext(Context context) {
        LogUtils.isLogDebug(true);
        appContext = new WeakReference<>(context);
        App.INSTANCE.baseUrl = EnvironmentConfig.INSTANCE.getBaseUrl(context);
        LogUtils.logger("AppDelegate attachBaseContext");
    }

    @Override
    public void onCreate(Application application) {
        // 必须在初始化ARouter之前配置
//        if (BuildConfig.DEBUG) {
//            // 日志开启
//            ARouter.openLog();
//            // 调试模式开启，如果在install run模式下运行，则必须开启调试模式
//            ARouter.openDebug();
//        }
//        ARouter.init(application);
        ApplicationLifeManager.INSTANCE.registerApplication(application);
    }

    @Override
    public void onTerminate(Application application) {
    }
}
