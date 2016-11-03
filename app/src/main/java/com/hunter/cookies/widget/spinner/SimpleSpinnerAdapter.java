package com.hunter.cookies.widget.spinner;

import android.content.Context;

import com.hunter.cookies.R;
import com.hunter.lib.base.BaseAdapter;
import com.hunter.lib.base.BaseViewHolder;

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