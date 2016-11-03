package com.hunter.cookies.ui.shots.list;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

public class ShotsListPresenter extends ShotsListContract.Presenter {

    @Override
    void getShots(int type, int sort, int timeFrame, int page) {
        subscribe(mModel.getShots(type, sort, timeFrame, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                mView.getShotsOnSuccess(shotsEntities);
            }
        });
    }
}
