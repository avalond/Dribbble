package com.hunter.cookies.ui.buckets.list;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.BucketsEntity;

import java.util.List;

import rx.Observable;

public interface BucketsListContract {

    interface Model extends BaseModel {

        Observable<List<BucketsEntity>> getBucketsList();
    }

    interface View extends BaseView {

        void getBucketsListOnSuccess(List<BucketsEntity> datas);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getBucketsList();
    }
}
