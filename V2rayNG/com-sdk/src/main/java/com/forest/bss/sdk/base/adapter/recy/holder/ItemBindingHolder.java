package com.forest.bss.sdk.base.adapter.recy.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.forest.bss.sdk.base.adapter.recy.BaseRecvAdapter;

/**
 * Created by llm
 */
public abstract class ItemBindingHolder<T, D extends ViewBinding> extends ItemHolder<T> {
    protected D binding;

    public ItemBindingHolder(Context context) {
        super(context);
    }

    protected abstract D setViewBinding(LayoutInflater inflater);

    @Override
    protected int getLayoutId() {
        return -1;
    }

    /**
     * 获取每个holder的视图
     *
     * @param parent
     * @return
     */
    @Override
    protected View getView(ViewGroup parent, BaseRecvAdapter adapter) {
        this.adapter = adapter;
        this.parent = parent;
        binding = setViewBinding(LayoutInflater.from(mContext));
        return binding.getRoot();
    }
}
