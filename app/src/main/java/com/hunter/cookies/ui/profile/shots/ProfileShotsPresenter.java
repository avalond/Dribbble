package com.hunter.cookies.ui.profile.shots;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

public class ProfileShotsPresenter extends ProfileShotsContract.Presenter {

    @Override
    void getUserShots(String id, int page) {
        subscribe(mModel.getUserShots(id, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                mView.getUserShotsOnSuccess(shotsEntities);
            }
        });
    }
}
