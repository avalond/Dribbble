package com.hunter.dribbble.ui.profile.shots;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.ShotsEntity;

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
