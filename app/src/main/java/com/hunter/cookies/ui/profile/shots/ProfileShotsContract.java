package com.hunter.cookies.ui.profile.shots;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.List;

import rx.Observable;

public interface ProfileShotsContract {

    interface Model extends BaseModel {

        Observable<List<ShotsEntity>> getUserShots(String id, int page);
    }

    interface View extends BaseView {

        void getUserShotsOnSuccess(List<ShotsEntity> datas);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getUserShots(String id, int page);
    }

}
