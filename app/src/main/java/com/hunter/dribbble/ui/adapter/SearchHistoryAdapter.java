package com.hunter.dribbble.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hunter.dribbble.R;
import com.hunter.library.base.BaseAdapter;
import com.hunter.library.base.BaseViewHolder;

import java.util.List;

public class SearchHistoryAdapter extends BaseAdapter<String> {

    private OnItemHistoryClickListener  mClickListener;
    private OnItemHistoryDeleteListener mDeleteListener;

    private View mViewClean;

    public interface OnItemHistoryClickListener {

        void onItemClick(String data, int position);
    }

    public interface OnItemHistoryDeleteListener {

        void onItemDelete(String data, int position);
    }

    public SearchHistoryAdapter(Context context, List<String> data, View viewClean) {
        super(context, data, R.layout.item_search);
        mViewClean = viewClean;
    }

    @Override
    protected void onBindItemData(BaseViewHolder holder, final int position) {
        final String data = mData.get(position);

        /**
         * 删除单项历史记录
         */
        ImageButton ibtnDeleteItem = holder.getView(R.id.ibtn_item_delete);
        ibtnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteListener != null) {
                    mDeleteListener.onItemDelete(data, position);
                }
            }
        });

        /**
         * 点击历史记录搜索
         */
        TextView tvHistory = holder.getView(R.id.tv_item_search_history);
        tvHistory.setText(data);
        tvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(data, position);
                }
            }
        });
    }

    public void setHistoryClickListener(OnItemHistoryClickListener clickListener) {
        mClickListener = clickListener;
    }

    public void setHistoryDeleteListener(OnItemHistoryDeleteListener deleteListener) {
        mDeleteListener = deleteListener;
    }

    public void remove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
        if (mData.size() == 0) mViewClean.setVisibility(View.GONE);
    }

    public void orderChange(int position, String data) {
        mData.remove(position);
        mData.add(0, data);
        notifyDataSetChanged();
    }

    public void clean() {
        int size = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, size);
        mViewClean.setVisibility(View.GONE);
    }
}
