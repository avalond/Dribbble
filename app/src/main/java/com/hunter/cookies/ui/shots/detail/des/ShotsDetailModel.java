package com.hunter.cookies.ui.shots.detail.des;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.entity.CheckLikeEntity;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class ShotsDetailModel implements ShotsDetailContract.Model {

    @Override
    public Observable<ShotsEntity> getShotsDetail(int id) {
        return ApiClient.getForRest().getShotsDetail(id + "");
    }

    @Override
    public Observable<CheckLikeEntity> checkShotsLike(int id) {
        return ApiClient.getForRest().checkShotsLike(id + "");
    }

    @Override
    public Observable<CheckLikeEntity> changeShotsStatus(int id, boolean isLike) {
        if (isLike) return ApiClient.getForRest().likeShots(id + "");
        else return ApiClient.getForRest().unlikeShots(id + "");
    }

    @Override
    public Observable<String> addShotsToBuckets(int bucketsId, int shotsId) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.SHOTS_ID, shotsId + "");
        return ApiClient.getForRest().addShotsToBuckets(bucketsId + "", params);
    }
}
