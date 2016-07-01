package com.hunter.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NORMAL = 2;

    protected List<T> mData;
    protected Context mContext;
    protected int     mLayoutId;

    protected OnItemClickListener mListener;

    protected boolean mIsContainHeader;
    protected boolean mIsContainFooter;

    protected View mHeaderView;
    protected View mFooterView;

    public interface OnItemClickListener<T> {

        void onItemClick(int position, T data);
    }

    public BaseAdapter(Context context, List<T> data, int layoutId) {
        mData = data;
        mContext = context;
        mLayoutId = layoutId;
    }

    protected abstract void onBindItemData(BaseViewHolder holder, int position);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new BaseViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            return new BaseViewHolder(mFooterView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mLayoutId, parent, false);
            return new BaseViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        if (TYPE_NORMAL == getItemViewType(position)) {
            final int realPos = mIsContainHeader ? position - 1 : position;
            onBindItemData(holder, realPos);

            if (mListener != null) {
                holder.mConvertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(realPos, mData.get(realPos));
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + (mIsContainHeader ? 1 : 0) + (mIsContainFooter ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        /**
         * 头布局
         */
        if (mIsContainHeader && position == 0) {
            return TYPE_HEADER;
        }
        /**
         * 底布局
         */
        if (mIsContainFooter && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        /**
         * 普通布局
         */
        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        mIsContainHeader = true;
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public void setFooterView(View footerView) {
        mIsContainFooter = true;
        mFooterView = footerView;
        notifyItemInserted(mData.size());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public T getItemData(int position) {
        return mData.get(position);
    }

    public void add(T data, int position) {
        mData.add(data);
        notifyItemInserted(position);
    }

    public void reloadData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(T data, int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clean() {
        mData.clear();
        notifyDataSetChanged();
    }

}
