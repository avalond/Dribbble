package com.hunter.dribbble.mvp.followers;

import com.hunter.dribbble.entity.FollowerEntity;
import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.lib.rxjava.ApiCallback;
import com.hunter.lib.rxjava.SubscriberCallBack;

import java.util.List;

public class FollowersPresenter extends BasePresenter<FollowersView> {

    public FollowersPresenter(FollowersView view) {
        attachViewForRest(view);
    }

    public void getShots(String id) {

        addSubscription(mApiStores.getFollowers(id), new SubscriberCallBack(new ApiCallback<List<FollowerEntity>>() {

            @Override
            public void onSuccess(List<FollowerEntity> entity) {
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
