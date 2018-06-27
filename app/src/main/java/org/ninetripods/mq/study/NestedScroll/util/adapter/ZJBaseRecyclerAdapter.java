package org.ninetripods.mq.study.NestedScroll.util.adapter;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mq on 2018/2/8 下午5:02
 * mqcoder90@gmail.com
 */

public abstract class ZJBaseRecyclerAdapter<T>
        extends RecyclerView.Adapter<ZJViewHolder> implements ListAdapter {

    private final int mLayoutId;
    private final List<T> mList;
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private AdapterView.OnItemClickListener mListener;

    public ZJBaseRecyclerAdapter(@LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>();
        this.mLayoutId = layoutId;
    }

    public ZJBaseRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId) {
        setHasStableIds(false);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }

    public ZJBaseRecyclerAdapter(Collection<T> collection, @LayoutRes int layoutId, AdapterView.OnItemClickListener mListener) {
        setHasStableIds(false);
        setOnItemClickListener(mListener);
        this.mList = new ArrayList<>(collection);
        this.mLayoutId = layoutId;
    }

    private void addAnimate(ZJViewHolder holder, int position) {
        if (mOpenAnimationEnable && mLastPosition < position) {
            holder.itemView.setAlpha(0);
            holder.itemView.animate().alpha(1).start();
            mLastPosition = position;
        }
    }

    @Override
    public ZJViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZJViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(mLayoutId, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(ZJViewHolder holder, int position) {
        onBindViewHolder(holder, position < mList.size() ? mList.get(position) : null, position);
    }

    protected abstract void onBindViewHolder(ZJViewHolder holder, T model, int position);

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onViewAttachedToWindow(ZJViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimate(holder, holder.getLayoutPosition());
    }

    public void setOpenAnimationEnable(boolean enable) {
        this.mOpenAnimationEnable = enable;
    }

    public ZJBaseRecyclerAdapter<T> setOnItemClickListener(AdapterView.OnItemClickListener mListener) {
        this.mListener = mListener;
        return this;
    }

    public ZJBaseRecyclerAdapter<T> refresh(Collection<T> collection) {
        mList.clear();
        if(collection != null)
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        mLastPosition = -1;
        return this;
    }

    public ZJBaseRecyclerAdapter<T> loadMore(Collection<T> collection) {
        mList.addAll(collection);
        notifyDataSetChanged();
        notifyListDataSetChanged();
        return this;
    }

    private final DataSetObservable observable = new DataSetObservable();

    public void registerDataSetObserver(DataSetObserver observer) {
        observable.registerObserver(observer);
    }

    public void unRegisterDataSetObserver(DataSetObserver observer) {
        observable.unregisterObserver(observer);
    }

    public void notifyListDataSetChanged() {
        observable.notifyChanged();
    }

    public void notifyDataSetInvalidated() {
        observable.notifyInvalidated();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ZJViewHolder holder;
        if (convertView != null) {
            holder = (ZJViewHolder) convertView.getTag();
        } else {
            holder = onCreateViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        }
        onBindViewHolder(holder, position);
        addAnimate(holder, position);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return getCount() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }
}
