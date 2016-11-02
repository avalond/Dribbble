package com.hunter.dribbble.ui.buckets;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.BucketsEntity;

import java.util.List;

public class BucketsPresenter extends BucketsContract.Presenter {

    @Override
    void getBuckets() {
        subscribe(mModel.getBuckets(), new BaseSubscriber<List<BucketsEntity>>(mView) {
            @Override
            protected void onSuccess(List<BucketsEntity> datas) {
                mView.getBucketsOnSuccess(datas);

            }
        });
    }
}
