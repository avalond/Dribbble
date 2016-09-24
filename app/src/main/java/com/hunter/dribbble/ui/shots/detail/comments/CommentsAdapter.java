package com.hunter.dribbble.ui.shots.detail.comments;

import android.content.Context;
import android.widget.ImageView;

import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.utils.HtmlFormatUtils;
import com.hunter.dribbble.utils.TimeUtils;
import com.hunter.dribbble.utils.glide.GlideUtils;
import com.hunter.lib.base.BaseAdapter;
import com.hunter.lib.base.BaseViewHolder;

import java.util.List;

public class CommentsAdapter extends BaseAdapter<CommentEntity> {

    public CommentsAdapter(Context context, List<CommentEntity> data) {
        super(context, data, R.layout.item_shots_detail_comment);
    }

    @Override
    protected void onBindItemData(BaseViewHolder holder, int position) {
        CommentEntity entity = mData.get(position);
        UserEntity userEntity = entity.getUser();

        ImageView ivAvatar = holder.getView(R.id.iv_item_comments_avatar);
        GlideUtils.setAvatar(mContext, userEntity.getAvatarUrl(), ivAvatar);
        holder.setTvText(R.id.tv_item_comments_user_name, userEntity.getUsername());

        holder.setTvText(R.id.tv_item_comments_like, entity.getLikesCount() + "");
        holder.setTvText(R.id.tv_item_comments_content, HtmlFormatUtils.Html2String(entity.getBody()));
        holder.setTvText(R.id.tv_item_comments_time, TimeUtils.getTimeFromISO8601(entity.getUpdatedAt()));
    }
}
