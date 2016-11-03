package com.hunter.cookies.ui.buckets;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.BucketsEntity;

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
