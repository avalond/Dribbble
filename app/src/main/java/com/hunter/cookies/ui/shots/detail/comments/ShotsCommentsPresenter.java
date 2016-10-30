package com.hunter.cookies.ui.shots.detail.comments;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.CommentEntity;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

public class ShotsCommentsPresenter extends ShotsCommentsContract.Presenter {

    @Override
    void getComments(int id, int page) {
        subscribe(mModel.getComments(id, page), new BaseSubscriber<List<CommentEntity>>(mView) {
            @Override
            protected void onSuccess(List<CommentEntity> commentEntities) {
                mView.getCommentsOnSuccess(commentEntities);
            }
        });
    }

    @Override
    void putComments(int shotsId, int id, String body) {
        subscribe(mModel.putComments(shotsId, id, body), new BaseSubscriber<ShotsEntity>(mView) {
            @Override
            protected void onSuccess(ShotsEntity shotsEntity) {
                mView.putCommentsOnSuccess(shotsEntity);
            }
        });
    }

    @Override
    void deleteComments(int shotsId, int id) {
        subscribe(mModel.deleteComments(shotsId, id), new BaseSubscriber<String>(mView) {
            @Override
            protected void onSuccess(String s) {
                mView.deleteCommentsOnSuccess();
            }
        });
    }
}
