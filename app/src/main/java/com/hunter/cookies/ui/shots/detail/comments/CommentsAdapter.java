package com.hunter.cookies.ui.shots.detail.comments;

import android.widget.ImageView;
import android.widget.TextView;

import com.hunter.adapter.BaseQuickAdapter;
import com.hunter.adapter.BaseViewHolder;
import com.hunter.cookies.R;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.UserEntity;
import com.hunter.cookies.utils.HtmlFormatUtils;
import com.hunter.cookies.utils.TimeUtils;
import com.hunter.cookies.utils.glide.GlideUtils;

import java.util.List;

public class CommentsAdapter extends BaseQuickAdapter<CommentEntity, BaseViewHolder> {

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
        TextView tvComment = holder.getView(R.id.tv_item_comments_content);
        HtmlFormatUtils.Html2StringNoP(tvComment, commentEntity.getBody());
        holder.setText(R.id.tv_item_comments_time, TimeUtils.getTimeFromISO8601(commentEntity.getUpdatedAt()));
    }
}
