package com.forest.bss.sdk.lifecycles;

import android.app.Application;
import android.content.Context;

/**
 *  用于代理 {@link Application} 的生命周期
 */
public interface AppLifeCycles {
    void attachBaseContext(Context context);

    void onCreate(Application application);

    void onTerminate(Application application);
}
