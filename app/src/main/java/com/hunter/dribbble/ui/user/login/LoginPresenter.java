package com.hunter.dribbble.ui.user.login;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.entity.UserEntity;

public class LoginPresenter extends LoginContract.Presenter {

    public void getToken(String code) {
        subscribe(mModel.getToken(code), new BaseSubscriber<TokenEntity>(mView) {
            @Override
            public void onSuccess(TokenEntity tokenEntity) {
                mView.getTokenOnSuccess(tokenEntity);
            }

            @Override
            protected void onFail(String msg) {
                mView.getTokenOnFail(msg);
                mView.showToast(msg);
            }
        });
    }

    @Override
    void getUserInfo() {
        subscribe(mModel.getUserInfo(), new BaseSubscriber<UserEntity>(mView) {
            @Override
            protected void onSuccess(UserEntity entity) {
                mView.getUserInfoOnSuccess(entity);
            }
        });
    }
}
