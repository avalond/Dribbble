package com.hunter.dribbble.mvp.comments;

import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.mvp.BaseView;

import java.util.List;

public interface CommentsView extends BaseView {

    void onSuccess(List<CommentEntity> data);
}
