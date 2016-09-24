package com.hunter.dribbble.ui.shots.list;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.ShotsEntity;

import java.util.List;

public class ShotsListPresenter extends ShotsListContract.Presenter {

    @Override
    void getShots(int type, int sort, int timeFrame, int page) {
        subscribeOn(mModel.getShots(type, sort, timeFrame, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                mView.getShotsOnSuccess(shotsEntities);
            }
        });
    }
}
