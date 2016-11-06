package com.hunter.cookies.ui.buckets.list;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.entity.BucketsEntity;

import java.util.List;

import rx.Observable;

public class BucketsListModel implements BucketsListContract.Model {

    @Override
    public Observable<List<BucketsEntity>> getBucketsList() {
        return ApiClient.getForRest().getBucketsList();
    }
}
