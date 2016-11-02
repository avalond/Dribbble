package com.hunter.dribbble.ui.buckets;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.BucketsEntity;

import java.util.List;

import rx.Observable;

public interface BucketsContract {

    interface Model extends BaseModel {

        Observable<List<BucketsEntity>> getBuckets();
    }

    interface View extends BaseView {

        void getBucketsOnSuccess(List<BucketsEntity> datas);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getBuckets();
    }
}
