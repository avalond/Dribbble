package com.hunter.dribbble.ui.shots.detail.comments;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.CommentEntity;

import java.util.List;

public class ShotsCommentsPresenter extends ShotsCommentsContract.Presenter {

    @Override
    void getComments(String id) {
        subscribeOn(mModel.getComments(id), new BaseSubscriber<List<CommentEntity>>(mView) {
            @Override
            protected void onSuccess(List<CommentEntity> commentEntities) {
                mView.getCommentsOnSuccess(commentEntities);
            }
        });
    }
}
