package com.hunter.dribbble.ui.main;

import com.hunter.dribbble.api.BaseSubscriber;
import com.hunter.dribbble.entity.UserEntity;

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
