package com.hunter.dribbble.ui.main;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.UserEntity;

import rx.Observable;

public interface UserInfoContract {

    interface Model extends BaseModel {

        Observable<UserEntity> getUserInfo();
    }

    interface View extends BaseView {

        void getUserInfoOnSuccess(UserEntity entity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getUserInfo();
    }

}
