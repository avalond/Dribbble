package com.hunter.dribbble.ui.profile.shots;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.ShotsEntity;

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
