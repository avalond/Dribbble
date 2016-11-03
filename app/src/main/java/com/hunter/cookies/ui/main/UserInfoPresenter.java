package com.hunter.cookies.ui.main;

import com.hunter.cookies.base.mvp.BaseSubscriber;
import com.hunter.cookies.entity.UserEntity;

public class UserInfoPresenter extends UserInfoContract.Presenter {

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
