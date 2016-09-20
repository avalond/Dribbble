package com.hunter.dribbble.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.utils.ImageUrlUtils;
import com.hunter.dribbble.utils.TimeUtils;
import com.hunter.lib.base.BaseAdapter;
import com.hunter.lib.base.BaseViewHolder;

import java.util.List;

public class ShotsAdapter extends BaseAdapter<ShotsEntity> {

    private int mViewMode;

    private OnItemClickUserInfoListener mUserInfoListener;

    public interface OnItemClickUserInfoListener {

        void onItemClickUserInfo(UserEntity entity);

    }

    public ShotsAdapter(Context context, List<ShotsEntity> data, int viewMode) {
        super(context, data, 0);
        setLayoutId(viewMode);
    }

    public void setLayoutId(int viewMode) {
        mViewMode = viewMode;

        switch (mViewMode) {
            case AppConstants.VIEW_MODE_SMALL_WITH_INFO:
            case AppConstants.VIEW_MODE_SMALL:
                mLayoutId = R.layout.item_shots_home_small;
                break;

            case AppConstants.VIEW_MODE_LARGE_WITH_INFO:
            case AppConstants.VIEW_MODE_LARGE:
                mLayoutId = R.layout.item_shots_home_large;
                break;
        }
    }

    public void setUserInfoListener(OnItemClickUserInfoListener userInfoListener) {
        mUserInfoListener = userInfoListener;
    }

    @Override
    protected void onBindItemData(BaseViewHolder holder, int position) {
        ShotsEntity entity = mData.get(position);
        final UserEntity userEntity = entity.getUser();

        switch (mViewMode) {
            /**
             * 小图与简略信息，填充操作栏数据
             */
            case AppConstants.VIEW_MODE_SMALL_WITH_INFO:
                holder.setVisibility(R.id.item_shots_options_small, View.VISIBLE);
                holder.setTvText(R.id.tv_item_shots_comment, entity.getCommentsCount() + "");
                holder.setTvText(R.id.tv_item_shots_like, entity.getLikesCount() + "");
                break;
            /**
             * 仅小图，隐藏操作栏
             */
            case AppConstants.VIEW_MODE_SMALL:
                holder.setVisibility(R.id.item_shots_options_small, View.GONE);
                break;
            /**
             * 大图与详细信息，填充操作栏与描述信息数据
             */
            case AppConstants.VIEW_MODE_LARGE_WITH_INFO:
                LinearLayout linearLayout = holder.getView(R.id.item_shots_header_large);
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mUserInfoListener != null) {
                            mUserInfoListener.onItemClickUserInfo(userEntity);
                        }
                    }
                });
                linearLayout.setVisibility(View.VISIBLE);
                holder.setTvText(R.id.tv_item_shots_title, entity.getTitle());
                holder.setDraweeImg(R.id.drawee_item_shots_avatar, userEntity.getAvatarUrl());
                holder.setTvText(R.id.tv_item_shots_user_name, userEntity.getUsername());
                holder.setTvText(R.id.tv_item_shots_time, TimeUtils.getTimeFromISO8601(entity.getUpdatedAt()));

                holder.setVisibility(R.id.item_shots_options_large, View.VISIBLE);
                holder.setTvText(R.id.tv_item_shots_comment, entity.getCommentsCount() + "");
                holder.setTvText(R.id.tv_item_shots_like, entity.getLikesCount() + "");
                holder.setTvText(R.id.tv_item_shots_view, entity.getViewsCount() + "");
                break;
            /**
             * 仅大图，隐藏操作栏与描述信息
             */
            case AppConstants.VIEW_MODE_LARGE:
                holder.setVisibility(R.id.item_shots_header_large, View.GONE);
                holder.setVisibility(R.id.item_shots_options_large, View.GONE);
        }

        /**
         * 是否为动画
         */
        if (entity.isAnimated()) {
            holder.setVisibility(R.id.iv_item_shots_gif, View.VISIBLE);
            holder.setDraweeGif(R.id.drawee_item_shots_preview, ImageUrlUtils.getImageUrl(entity.getImages()));
        } else {
            holder.setVisibility(R.id.iv_item_shots_gif, View.GONE);
            holder.setDraweeImg(R.id.drawee_item_shots_preview, entity.getImages().getNormal());
        }

        holder.setDraweeImg(R.id.drawee_item_shots_preview, entity.getImages().getNormal());
    }

}
