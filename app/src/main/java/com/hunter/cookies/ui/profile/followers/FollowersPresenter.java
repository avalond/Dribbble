package com.hunter.cookies.ui.profile.followers;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.FollowerEntity;

import java.util.List;

public class FollowersPresenter extends FollowersContract.Presenter {

    @Override
    void getFollowers(String id, int page) {
        subscribe(mModel.getFollowers(id, page), new BaseSubscriber<List<FollowerEntity>>(mView) {
            @Override
            protected void onSuccess(List<FollowerEntity> followerEntities) {
                mView.getFollowersOnSuccess(followerEntities);
            }
        });
    }
}
