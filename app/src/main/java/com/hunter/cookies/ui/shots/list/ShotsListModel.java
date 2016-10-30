package com.hunter.cookies.ui.shots.list;

import com.hunter.cookies.api.ApiClient;
import com.hunter.cookies.api.ApiConstants;
import com.hunter.cookies.entity.ShotsEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;

public class ShotsListModel implements ShotsListContract.Model {

    @Override
    public Observable<List<ShotsEntity>> getShots(int type, int sort, int timeFrame, int page) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.LIST, ApiConstants.ParamValue.LIST_VALUES[type]);
        params.put(ApiConstants.ParamKey.SORT, ApiConstants.ParamValue.SORT_VALUES[sort]);
        params.put(ApiConstants.ParamKey.TIME_FRAME, ApiConstants.ParamValue.TIME_VALUES[timeFrame]);
        params.put(ApiConstants.ParamKey.PAGE, page + "");
        params.put(ApiConstants.ParamKey.PER_PAGE, ApiConstants.ParamValue.PAGE_SIZE + "");

        return ApiClient.getForRest().getShotsList(params);
    }
}
