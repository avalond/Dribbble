package com.hunter.dribbble.ui.profile.shots;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.shots.detail.ShotsDetailActivity;
import com.hunter.dribbble.utils.glide.GlideUtils;

import java.util.List;

public class ProfileShotsAdapter extends BaseQuickAdapter<ShotsEntity, BaseViewHolder> {

    public ProfileShotsAdapter(List<ShotsEntity> data) {
        super(R.layout.item_shots_small, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final ShotsEntity shotsEntity) {
        ImageView ivPreview = holder.getView(R.id.piv_item_shots_preview);
        if (shotsEntity.isAnimated()) {
            holder.setVisible(R.id.iv_item_shots_gif, true);
            GlideUtils.setGif(mContext, shotsEntity.getImages().getHidpi(), ivPreview);
        } else {
            holder.setVisible(R.id.iv_item_shots_gif, false);
            GlideUtils.setImageWithThumb(mContext, shotsEntity.getImages().getNormal(), ivPreview);
        }

        holder.getView(R.id.item_shots).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShotsDetailActivity.class);
                intent.putExtra(ShotsDetailActivity.EXTRA_SHOTS_ENTITY, shotsEntity);
                intent.putExtra(ShotsDetailActivity.EXTRA_IS_NEED_REQUEST, true);
                mContext.startActivity(intent);
            }
        });
    }
}
