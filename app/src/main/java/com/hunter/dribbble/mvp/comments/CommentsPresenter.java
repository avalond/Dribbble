package com.hunter.dribbble.mvp.comments;

import com.hunter.dribbble.entity.CommentEntity;
import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.library.rxjava.ApiCallback;
import com.hunter.library.rxjava.SubscriberCallBack;

import java.util.List;

public class CommentsPresenter extends BasePresenter<CommentsView> {

    public CommentsPresenter(CommentsView view) {
        attachViewForRest(view);
    }

    public void getShots(String id) {
        mView.onStarted();

        addSubscription(mApiStores.getComments(id), new SubscriberCallBack(new ApiCallback<List<CommentEntity>>() {

            @Override
            public void onSuccess(List<CommentEntity> entity) {
                mView.onSuccess(entity);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.onFailed(msg);
            }

            @Override
            public void onCompleted() {
                mView.onFinished();
            }
        }));
    }

}
