package com.hunter.cookies.ui.buckets;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hunter.cookies.R;
import com.hunter.cookies.entity.BucketsEntity;
import com.hunter.cookies.utils.HtmlFormatUtils;

import java.util.List;

public class BucketsAdapter extends BaseQuickAdapter<BucketsEntity, BaseViewHolder> {

    public BucketsAdapter(List<BucketsEntity> data) {
        super(R.layout.item_buckets, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BucketsEntity entity) {
        holder.setText(R.id.tv_item_buckets_name, entity.getName());
        holder.setText(R.id.tv_item_buckets_count, HtmlFormatUtils.setupBold(entity.getShotsCount(), " 作品"));

        TextView tvDes = holder.getView(R.id.tv_item_buckets_des);
        String des = entity.getDescription();
        if (TextUtils.isEmpty(des)) {
            tvDes.setVisibility(View.GONE);
        } else {
            tvDes.setVisibility(View.VISIBLE);
            tvDes.setText(des);
        }
    }
}
