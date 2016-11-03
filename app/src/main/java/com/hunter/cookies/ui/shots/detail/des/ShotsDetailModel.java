package com.hunter.cookies.ui.shots.detail.des;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.entity.CheckLikeEntity;
import com.hunter.cookies.entity.ShotsEntity;

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
}
