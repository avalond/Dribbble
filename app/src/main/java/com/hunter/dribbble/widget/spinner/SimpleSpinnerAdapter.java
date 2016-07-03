package com.hunter.dribbble.widget.spinner;

import android.content.Context;

import com.hunter.dribbble.R;
import com.hunter.library.base.BaseAdapter;
import com.hunter.library.base.BaseViewHolder;

import java.util.List;

public class SimpleSpinnerAdapter extends BaseAdapter<String> {

    public SimpleSpinnerAdapter(Context context, List<String> data) {
        super(context, data, R.layout.item_spinner_home);
    }

    @Override
    protected void onBindItemData(BaseViewHolder holder, int position) {
        holder.setTvText(R.id.tv_item_spinner, mData.get(position));
    }
}