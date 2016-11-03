package com.hunter.cookies.ui.buckets;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.BucketsEntity;

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
