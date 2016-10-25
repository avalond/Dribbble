package com.hunter.dribbble.ui.main;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.entity.UserEntity;

import rx.Observable;

public class UserInfoModel implements UserInfoContract.Model {

    @Override
    public Observable<UserEntity> getUserInfo() {
        return ApiClient.getForRest().getUserInfo();
    }
}
