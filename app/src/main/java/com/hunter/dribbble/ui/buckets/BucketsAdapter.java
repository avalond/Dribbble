package com.hunter.dribbble.ui.buckets;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.BucketsEntity;
import com.hunter.dribbble.utils.HtmlFormatUtils;
import com.hunter.dribbble.utils.TimeUtils;

import java.util.List;

public class BucketsAdapter extends BaseQuickAdapter<BucketsEntity, BaseViewHolder> {

    public BucketsAdapter(List<BucketsEntity> data) {
        super(R.layout.item_buckets, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BucketsEntity entity) {
        holder.setText(R.id.tv_item_buckets_name, entity.getName());
        holder.setText(R.id.tv_item_buckets_count, HtmlFormatUtils.setupBold(entity.getShotsCount(), " 作品"));
        holder.setText(R.id.tv_item_buckets_create, "创建时间：" + TimeUtils.getTimeFromISO8601(entity.getCreatedAt()));
        holder.setText(R.id.tv_item_buckets_update, "修改时间：" + TimeUtils.getTimeFromISO8601(entity.getUpdatedAt()));

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
