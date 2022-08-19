package com.forest.bss.sdk.base.adapter.recy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.forest.bss.sdk.R;
import com.forest.bss.sdk.base.adapter.recy.holder.CommonRecyHolder;
import com.forest.bss.sdk.base.adapter.recy.holder.ItemHolder;
import com.forest.bss.sdk.ext.RxUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * Created by llm
 */
public abstract class BaseRecvAdapter<T> extends RecyclerView.Adapter<CommonRecyHolder> implements View.OnClickListener {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;
    private static final int BASE_ITEM_TYPE_EMPTY = 300000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();

    private SparseArrayCompat<View> emptyView = new SparseArrayCompat<>();

    public List<T> list = new ArrayList<T>();
    public OnItemClickListener onItemClickListener;
    protected Context mContext;

    private CommonRecyHolder<T> holder;

    /**
     * 数据上报的position集合，清空的情况：新建adapter、setData、clear
     */
    private Set<Integer> upVisible = new HashSet<>();

    public BaseRecvAdapter(Context context) {
        this.mContext = context;
    }

    public BaseRecvAdapter(Context context, List<T> list) {
        this.mContext = context;
        upVisible.clear();
        if (list != null) {
            this.list = list;
        }
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void setData(List<T> datas) {
        setDataNotNotifyChanged(datas);
        notifyDataSetChanged();
    }

    public void setDatas(List<T> datas) {
        upVisible.clear();
        list.clear();
        if (datas != null) {
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void setDataNotNotifyChanged(List<T> data) {
        upVisible.clear();
        list = data;
    }

    /**
     * 局部刷新
     * @param position 传入对应的下标
     */
    public void runNotifyItemChanged(int position) {
        RxUtil.runOnMainThread(() -> {
            if (mHeaderViews.isEmpty()) {
                notifyItemChanged(position);
            } else {
                notifyItemChanged(position + mHeaderViews.size());
            }
            return null;
        });
    }

    /**
     * 局部刷新 删除某一项
     * @param position 传入对应的下标
     */
    public void runNotifyItemRemoved(int position) {
        RxUtil.runOnMainThread(() -> {
            if (mHeaderViews.isEmpty()) {
                notifyItemRemoved(position);
            } else {
                notifyItemRemoved(position + mHeaderViews.size());
            }
            return null;
        });
    }

    public void setData(List<T> datas, int position) {
        list = datas;
        notifyItemInserted(position);
    }

    public void addData(List<T> datas) {
        if (datas != null && !datas.isEmpty() && list != null) {
            list.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        upVisible.clear();
        if (list != null) {
            list.clear();
        }
        notifyDataSetChanged();
    }



    public void clearList() {
        upVisible.clear();
        if (list != null) {
            list.clear();
        }
    }

    public List<T> getData() {
        if(list == null){
            list = new ArrayList<T>();
        }
        return list;
    }

    @Override
    public CommonRecyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonRecyHolder holder;
        if (mHeaderViews.get(viewType) != null) {
            holder = CommonRecyHolder.createHeaderOrFooterViewHolder(mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            holder = CommonRecyHolder.createHeaderOrFooterViewHolder(mFootViews.get(viewType));
        } else if (emptyView.get(viewType) != null) {
            holder = CommonRecyHolder.createEmptyViewHolder(emptyView.get(viewType));
        } else {
            holder = new CommonRecyHolder(parent, createItemHolder(viewType), this);
        }

        setCommonRecyHolder(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommonRecyHolder holder, int position) {
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            return;
        }

        Log.e("NewPhotoFrameAdapter", "onBindViewHolder: " );
        position = position - getHeadersCount();
        holder.bindView(getItem(position), position);
        if (onItemClickListener != null) {
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isEmptyViewPos(position)) {
            return emptyView.keyAt(0);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return getViewType(position - getHeadersCount());
    }

    /**
     * 获取item的type类型,子类继承的时候要设置多类型的布局，必须重新这个方法不要使用getItemViewType
     *
     * @param position
     * @return
     */
    public int getViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (list != null && !list.isEmpty()) {
            size = list.size();
        }
        return getHeadersCount() + getFootersCount() + getEmptyCount() + size;
    }

    /**
     * 获取item
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        try{
            if (position >= 0 && position < list.size()) {
                return list.get(position);
            }
        } catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Integer) {
            int postion = (int) tag;
            onItemClickListener.onItemClickListener(v, getItem(postion), postion);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected abstract ItemHolder<T> createItemHolder(int viewType);

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }

    private boolean isEmptyViewPos(int position) {
        return position == getHeadersCount();
    }

    public int getRealItemCount() {
        int size = 0;
        if (list != null) {
            size = list.size();
        }
        return size;
    }

    private void setCommonRecyHolder(CommonRecyHolder<T> holder) {
        this.holder = holder;
    }

    public CommonRecyHolder<T> getCommonRecyHolder() {
        return holder;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
        notifyDataSetChanged();
    }


    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
        notifyDataSetChanged();
    }


    public void clearFootView() {
        mFootViews.clear();
        notifyDataSetChanged();
    }

    public void clearHeadView() {
        mHeaderViews.clear();
        notifyDataSetChanged();
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getEmptyCount() {
        return emptyView.size();
    }

    private int getFootersCount() {
        return mFootViews.size();
    }

    public void addEmptyView(View view) {
        emptyView.put(BASE_ITEM_TYPE_EMPTY, view);
    }

    public void removeEmptyView() {
        if (emptyView.size() > 0) {
            emptyView.clear();
        }
    }



    public View getEmptyView(){
       return emptyView.get(BASE_ITEM_TYPE_EMPTY);
    }
}
