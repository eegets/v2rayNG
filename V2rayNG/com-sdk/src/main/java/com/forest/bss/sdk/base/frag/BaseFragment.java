package com.forest.bss.sdk.base.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.BarUtils;

//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wangkai on 2021/01/28 18:31
 * <p>
 * Desc 基础Fragment
 */
public abstract class BaseFragment extends BaseVisibleFragment {

    public abstract int layoutId();

    public abstract View viewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract boolean isEnableViewBinding();

    public abstract void bindingView(View rootView);

    public abstract void bindViewModelObserve(View rootView);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (isEnableViewBinding()) {
            return viewBinding(inflater, container, savedInstanceState);
        } else {
            return inflater.inflate(layoutId(), container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView(view);
        bindViewModelObserve(view);
//        EventBus.getDefault().register(this);
    }

    public ConstraintLayout.LayoutParams layoutParams(int height) {
        return new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
    }


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

    /**
     * 为了解决此bug
     * its super classes have no public methods with the @Subscribe annotation
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(BusInfo event) {
//        LogUtils.logger("BaseFragment eventBus entity flag: " + event.getFlag() + ", data: " + event.getData());
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
//    }
}
