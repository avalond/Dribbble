package com.hunter.dribbble.ui.buckets;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.entity.BucketsEntity;

import java.util.List;

import rx.Observable;

public class BucketsModel implements BucketsContract.Model {

    @Override
    public Observable<List<BucketsEntity>> getBuckets() {
        return ApiClient.getForRest().getUserBuckets();
    }
}
