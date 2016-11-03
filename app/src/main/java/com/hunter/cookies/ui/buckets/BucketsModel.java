package com.hunter.cookies.ui.buckets;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.entity.BucketsEntity;

import java.util.List;

import rx.Observable;

public class BucketsModel implements BucketsContract.Model {

    @Override
    public Observable<List<BucketsEntity>> getBuckets() {
        return ApiClient.getForRest().getUserBuckets();
    }
}
