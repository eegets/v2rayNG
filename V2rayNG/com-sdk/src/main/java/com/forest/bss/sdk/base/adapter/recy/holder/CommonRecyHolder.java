package com.forest.bss.sdk.base.adapter.recy.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.forest.bss.sdk.base.adapter.recy.BaseRecvAdapter;

public class CommonRecyHolder<T> extends RecyclerView.ViewHolder {

    private ItemHolder<T> item;

    public CommonRecyHolder(ViewGroup parent, ItemHolder<T> itemView, BaseRecvAdapter adapter) {
        super(itemView.getView(parent,adapter));
        this.item = itemView;
    }

    public CommonRecyHolder(View itemView) {
        super(itemView);
    }

    public void bindView(T t, int position) {
        if (item != null) {
            item.bindView(t, position);
        }
    }

    public ItemHolder<T> getItemHolder() {
        return item;
    }

    /**
     * 主要是为recyclelerview添加头或者脚
     *
     * @param itemView
     * @return
     */
    public static CommonRecyHolder createHeaderOrFooterViewHolder(View itemView) {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(params);
        CommonRecyHolder holder = new CommonRecyHolder(itemView);
        return holder;
    }

    /**
     * 主要是为recyclelerview添加空白页面
     *
     * @param itemView
     * @return
     */
    public static CommonRecyHolder createEmptyViewHolder(View itemView) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(params);

        CommonRecyHolder holder = new CommonRecyHolder(itemView);
        return holder;
    }
}
