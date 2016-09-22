package com.hunter.dribbble.ui.shots.list;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class ShotsListModel implements ShotsListContract.Model {

    @Override
    public Observable<List<ShotsEntity>> getShots(int type, int sort, int timeFrame) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.LIST, ApiConstants.ParamValue.LIST_VALUES[type]);
        params.put(ApiConstants.ParamKey.SORT, ApiConstants.ParamValue.SORT_VALUES[sort]);
        params.put(ApiConstants.ParamKey.TIME_FRAME, ApiConstants.ParamValue.TIME_VALUES[timeFrame]);
        return ApiClient.getForRest().getShots(params);
    }
}
