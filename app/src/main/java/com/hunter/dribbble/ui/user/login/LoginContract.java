package com.hunter.dribbble.ui.user.login;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.entity.UserEntity;

import rx.Observable;

public interface LoginContract {

    interface Model extends BaseModel {

        Observable<UserEntity> getUserInfo();

        Observable<TokenEntity> getToken(String code);
    }

    interface View extends BaseView {

        void getTokenOnSuccess(TokenEntity entity);

        void getTokenOnFail(String msg);

        void getUserInfoOnSuccess(UserEntity entity);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getToken(String code);

        abstract void getUserInfo();
    }
}
