package com.hunter.dribbble.ui.login;

import com.hunter.dribbble.base.mvp.BaseModel;
import com.hunter.dribbble.base.mvp.BasePresenter;
import com.hunter.dribbble.base.mvp.BaseView;
import com.hunter.dribbble.entity.TokenEntity;

import rx.Observable;

public interface LoginContract {

    interface Model extends BaseModel {

        Observable<TokenEntity> getToken(String code);
    }

    interface View extends BaseView {

        void getTokenOnSuccess(TokenEntity entity);

        void getTokenOnFail(String msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        abstract void getToken(String code);
    }
}
