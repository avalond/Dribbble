package com.hunter.dribbble.ui.shots.detail.comments;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hunter.dribbble.R;
import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.entity.UserEntity;
import com.hunter.dribbble.utils.HtmlFormatUtils;
import com.hunter.dribbble.utils.TimeUtils;
import com.hunter.dribbble.utils.glide.GlideUtils;

import java.util.List;

public class CommentsAdapter extends BaseQuickAdapter<CommentEntity> {

    public CommentsAdapter(List<CommentEntity> data) {
        super(R.layout.item_shots_detail_comment, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CommentEntity commentEntity) {
        UserEntity userEntity = commentEntity.getUser();
        ImageView ivAvatar = holder.getView(R.id.iv_item_comments_avatar);
        GlideUtils.setAvatar(mContext, userEntity.getAvatarUrl(), ivAvatar);
        holder.setText(R.id.tv_item_comments_user_name, userEntity.getUsername());

        holder.setText(R.id.tv_item_comments_like, commentEntity.getLikesCount() + "");
        holder.setText(R.id.tv_item_comments_content, HtmlFormatUtils.Html2String(commentEntity.getBody()));
        holder.setText(R.id.tv_item_comments_time, TimeUtils.getTimeFromISO8601(commentEntity.getUpdatedAt()));
    }
}
