package com.hunter.cookies.ui.shots.detail.des;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.CheckLikeEntity;
import com.hunter.cookies.entity.ShotsEntity;

import rx.Observable;

public interface ShotsDetailContract {

    interface Model extends BaseModel {

        Observable<ShotsEntity> getShotsDetail(int id);

        Observable<CheckLikeEntity> checkShotsLike(int id);

        Observable<CheckLikeEntity> changeShotsStatus(int id, boolean isLike);

        Observable<String> addShotsToBuckets(int bucketsId, int shotsId);
    }

    interface View extends BaseView {

        void getShotsDetailOnSuccess(ShotsEntity data);

        void checkShotsLikeOnSuccess(boolean isLiked);

        void addShotsToBucketsOnSuccess();
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getShotsDetail(int id);

        abstract void checkShotsLike(int id);

        abstract void changeShotsStatus(int id, boolean isLike);

        abstract void addShotsToBuckets(int bucketsId, int shotsId);

    }
}
