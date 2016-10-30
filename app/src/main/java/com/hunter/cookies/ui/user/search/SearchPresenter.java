package com.hunter.cookies.ui.user.search;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

public class SearchPresenter extends SearchContract.Presenter {

    @Override
    void searchShots(String key, int page) {
        subscribe(mModel.searchShots(key, page), new BaseSubscriber<List<ShotsEntity>>(mView) {
            @Override
            protected void onSuccess(List<ShotsEntity> shotsEntities) {
                mView.searchShotsOnSuccess(shotsEntities);
            }
        });
    }
}
