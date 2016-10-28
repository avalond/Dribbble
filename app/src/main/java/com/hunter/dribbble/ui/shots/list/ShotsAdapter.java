package com.hunter.dribbble.ui.shots.list;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.ui.profile.ProfileActivity;
import com.hunter.dribbble.ui.shots.detail.ShotsDetailActivity;
import com.hunter.dribbble.utils.IntentUtils;
import com.hunter.dribbble.utils.TimeUtils;
import com.hunter.dribbble.utils.glide.GlideUtils;

import java.util.List;

public class ShotsAdapter extends BaseMultiItemQuickAdapter<ShotsEntity, BaseViewHolder> {

    private Activity mActivity;

    public ShotsAdapter(Activity activity, List<ShotsEntity> data) {
        super(data);
        mActivity = activity;

        addItemType(AppConstants.VIEW_MODE_LARGE, R.layout.item_shots_large);
        addItemType(AppConstants.VIEW_MODE_LARGE_WITH_INFO, R.layout.item_shots_large_with_info);
        addItemType(AppConstants.VIEW_MODE_SMALL, R.layout.item_shots_small);
        addItemType(AppConstants.VIEW_MODE_SMALL_WITH_INFO, R.layout.item_shots_small_with_info);

        openLoadAnimation();
    }

    @Override
    protected void convert(BaseViewHolder holder, final ShotsEntity shotsEntity) {
        final UserEntity userEntity = shotsEntity.getUser();

        switch (holder.getItemViewType()) {
            case AppConstants.VIEW_MODE_LARGE_WITH_INFO:
                holder.addOnClickListener(R.id.item_shots_header_large);
                holder.setText(R.id.tv_item_shots_title, shotsEntity.getTitle());
                holder.setText(R.id.tv_item_shots_user_name, userEntity.getUsername());
                String updateAt = shotsEntity.getCreatedAt();
                if (!updateAt.isEmpty())
                    holder.setText(R.id.tv_item_shots_time, TimeUtils.getTimeFromISO8601(shotsEntity.getUpdatedAt()));
                holder.setText(R.id.tv_item_shots_comment, shotsEntity.getCommentsCount() + "");
                holder.setText(R.id.tv_item_shots_like, shotsEntity.getLikesCount() + "");
                holder.setText(R.id.tv_item_shots_view, shotsEntity.getViewsCount() + "");

                ImageView ivAvatar = holder.getView(R.id.iv_item_shots_avatar);
                initAvatar(userEntity, ivAvatar);
                break;
            case AppConstants.VIEW_MODE_SMALL_WITH_INFO:
                holder.setText(R.id.tv_item_shots_comment, shotsEntity.getCommentsCount() + "");
                holder.setText(R.id.tv_item_shots_like, shotsEntity.getLikesCount() + "");
                break;
        }

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
                intent.putExtra(ShotsDetailActivity.EXTRA_IS_FROM_SEARCH, true);
                mContext.startActivity(intent);
            }
        });
    }

    private void initAvatar(final UserEntity userEntity, final ImageView ivAvatar) {
        GlideUtils.setAvatar(mContext, userEntity.getAvatarUrl(), ivAvatar);
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, userEntity);
                IntentUtils.startActivity(mActivity, v, intent);
            }
        });
    }

}
