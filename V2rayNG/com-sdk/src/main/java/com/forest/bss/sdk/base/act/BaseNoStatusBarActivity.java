package com.forest.bss.sdk.base.act;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

//import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.BarUtils;
import com.forest.bss.sdk.R;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseNoStatusBarActivity extends BaseKeyBoardActivity {
    public abstract int layoutId();

    public abstract void initView();

    public abstract boolean isEnableViewBinding();

    public abstract View viewBinding();

    public FragmentActivity activity;

    public void viewModelObserve() {

    }

    @Override
    public boolean dispatchKeyBoard() {
        return false;
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 为了解决此bug
     * its super classes have no public methods with the @Subscribe annotation
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(BusInfo event) {
//
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        activity = this;
//        ARouter.getInstance().inject(this);
        BarUtils.isStatusBarLightMode(this);
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this)
                .navigationBarColor(R.color.white)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .init();
        if (isEnableViewBinding()) {
            setContentView(viewBinding());
        } else {
            setContentView(layoutId());
        }
//        EventBus.getDefault().register(this);
        initView();
        viewModelObserve();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }

    public void setTotalBarLayoutParams(View viewStatusBar, View viewContainer) {
        int statusBarHeight = BarUtils.getStatusBarHeight();
        if (viewStatusBar != null) {
            ConstraintLayout.LayoutParams paramStatusBar = layoutParams(statusBarHeight);
            viewStatusBar.setLayoutParams(paramStatusBar);
        }
        if (viewContainer != null) {
            ConstraintLayout.LayoutParams paramsContainer = layoutParams(ViewGroup.LayoutParams.MATCH_PARENT);
            paramsContainer.topMargin = statusBarHeight;
            viewContainer.setLayoutParams(paramsContainer);
        }
    }

    public ConstraintLayout.LayoutParams layoutParams(int height) {
        return new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }

}
