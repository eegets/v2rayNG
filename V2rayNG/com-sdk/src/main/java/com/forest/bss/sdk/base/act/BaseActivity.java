package com.forest.bss.sdk.base.act;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.forest.bss.sdk.R;
//import com.alibaba.android.arouter.launcher.ARouter;

//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity extends BaseKeyBoardActivity {
    public abstract int layoutId();

    public abstract void initView();

    public abstract boolean isEnableViewBinding();

    public abstract View viewBinding();

    public Activity activity;

    public void viewModelObserve() {

    }

    @Override
    public boolean dispatchKeyBoard() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

//    /**
//     * 为了解决此bug
//     * its super classes have no public methods with the @Subscribe annotation
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(BusInfo event) {
//
//    }
//
//    @SuppressLint("MissingPermission")
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(EventLoginOutEntity event) {
//        if (event.getFlag() == EventLoginOutEntityKt.OBSERVER_SUBSCRIBE) {
//            if (!NetworkUtils.isConnected()) {
//                Snackbar.make(findViewById(android.R.id.content), "网络有误", Snackbar.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        this.activity = this;
//        BarUtils.isStatusBarLightMode(this);
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this)
//                .fitsSystemWindows(true)
//                .navigationBarColor(R.color.white)
//                .statusBarColor(R.color.white)
//                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
//                .init();
        if (isEnableViewBinding()) {
            setContentView(viewBinding());
        } else {
            setContentView(layoutId());
        }
//        EventBus.getDefault().register(this);
//        ARouter.getInstance().inject(this);
        initView();
        viewModelObserve();

        if (showDefaultBackgroundColor()) {
            findViewById(android.R.id.content).setBackgroundColor(ActivityCompat.getColor(this, R.color.color_F2F2F2)); //设置默认背景色
        }
    }
    /**
     * 是否显示默认的背景色，背景色为`#F2F2F2`
     */
    public boolean showDefaultBackgroundColor() {
        return false;
    };

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
}
