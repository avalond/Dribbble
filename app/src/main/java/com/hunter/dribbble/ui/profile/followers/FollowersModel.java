package com.hunter.dribbble.ui.profile.followers;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.entity.FollowerEntity;

import java.util.List;

import rx.Observable;

public class FollowersModel implements FollowersContract.Model {

    @Override
    public Observable<List<FollowerEntity>> getFollowers(String id) {
        return ApiClient.getForRest().getFollowers(id);
    }
}
