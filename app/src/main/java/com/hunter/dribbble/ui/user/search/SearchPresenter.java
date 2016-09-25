package com.hunter.dribbble.ui.user.search;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.ShotsEntity;

import java.util.List;

public class SearchPresenter extends SearchContract.Presenter {

    @Override
    void searchShots(String key, int page) {
        subscribeOn(mModel.searchShots(key, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                mView.searchShotsOnSuccess(shotsEntities);
            }
        });
    }
}
