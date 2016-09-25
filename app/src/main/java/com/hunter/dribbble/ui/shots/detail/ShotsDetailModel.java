package com.hunter.dribbble.ui.shots.detail;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.entity.ShotsEntity;

import rx.Observable;

public class ShotsDetailModel implements ShotsDetailContract.Model {

    @Override
    public Observable<ShotsEntity> getShotsDetail(int id) {
        return ApiClient.getForRest().getShotsDetail(id + "");
    }
}
