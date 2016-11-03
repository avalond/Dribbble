package com.hunter.cookies.ui.main;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.entity.UserEntity;

import rx.Observable;

public class UserInfoModel implements UserInfoContract.Model {

    @Override
    public Observable<UserEntity> getUserInfo() {
        return ApiClient.getForRest().getUserInfo();
    }
}
