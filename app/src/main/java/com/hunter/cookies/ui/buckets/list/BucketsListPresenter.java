package com.hunter.cookies.ui.buckets.list;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.BucketsEntity;

import java.util.List;

public class BucketsListPresenter extends BucketsListContract.Presenter {

    @Override
    void getBucketsList() {
        subscribe(mModel.getBucketsList(), new BaseSubscriber<List<BucketsEntity>>(mView) {
            @Override
            protected void onSuccess(List<BucketsEntity> datas) {
                mView.getBucketsListOnSuccess(datas);

            }
        });
    }
}
