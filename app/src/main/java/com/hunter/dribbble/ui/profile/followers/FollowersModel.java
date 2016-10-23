package com.hunter.dribbble.ui.profile.followers;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.FollowerEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class FollowersModel implements FollowersContract.Model {

    @Override
    public Observable<List<FollowerEntity>> getFollowers(String id, int page) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.PAGE, page + "");
        params.put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");
        return ApiClient.getForRest().getFollowers(id, params);
    }
}
