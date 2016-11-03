package com.hunter.cookies.ui.main;

import com.hunter.cookies.base.mvp.BaseModel;
import com.hunter.cookies.base.mvp.BasePresenter;
import com.hunter.cookies.base.mvp.BaseView;
import com.hunter.cookies.entity.UserEntity;

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
