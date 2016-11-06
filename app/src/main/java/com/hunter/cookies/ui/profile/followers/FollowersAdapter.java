package com.hunter.cookies.ui.profile.followers;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.adapter.BaseQuickAdapter;
import com.hunter.adapter.BaseViewHolder;
import com.hunter.cookies.R;
import com.hunter.cookies.entity.FollowerEntity;
import com.hunter.cookies.entity.UserEntity;
import com.hunter.cookies.ui.profile.ProfileActivity;
import com.hunter.cookies.utils.glide.GlideUtils;

import java.util.List;

public class FollowersAdapter extends BaseQuickAdapter<FollowerEntity, BaseViewHolder> {

    public FollowersAdapter(List<FollowerEntity> data) {
        super(R.layout.item_profile_followers, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, FollowerEntity entity) {
        final UserEntity userEntity = entity.getFollower();
        ImageView ivAvatar = holder.getView(R.id.iv_item_follower_avatar);
        GlideUtils.setAvatar(mContext, userEntity.getAvatarUrl(), ivAvatar);
        holder.setText(R.id.tv_item_follower_user_name, userEntity.getUsername());
        holder.setText(R.id.tv_item_follower_shots, userEntity.getShotsCount() + " 作品");

        TextView tvLocation = holder.getView(R.id.tv_item_follower_location);
        String location = userEntity.getLocation();
        if (TextUtils.isEmpty(location)) {
            tvLocation.setVisibility(View.GONE);
        } else {
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText(location);
        }

        holder.getView(R.id.item_profile_followers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_USER_ENTITY, userEntity);
                mContext.startActivity(intent);
            }
        });
    }
}
