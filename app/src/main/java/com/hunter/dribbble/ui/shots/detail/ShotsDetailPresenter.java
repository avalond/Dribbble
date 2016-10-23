package com.hunter.dribbble.ui.shots.detail;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.ShotsEntity;

public class ShotsDetailPresenter extends ShotsDetailContract.Presenter {

    @Override
    void getShotsDetail(int id) {
        subscribe(mModel.getShotsDetail(id), new BaseSubscriber<ShotsEntity>(mView) {
            @Override
            protected void onSuccess(ShotsEntity shotsEntity) {
                mView.getShotsDetailOnSuccess(shotsEntity);
            }
        });
    }
}
