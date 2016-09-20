package com.hunter.dribbble.mvp.shots;

import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.lib.rxjava.ApiCallback;
import com.hunter.lib.rxjava.SubscriberCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShotsPresenter extends BasePresenter<ShotsView> {

    public ShotsPresenter(ShotsView view) {
        attachViewForRest(view);
    }

    public void getShots(String list, String timeFrame, String sort) {
        mView.onStarted();

        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.LIST, list);
        params.put(ApiConstants.ParamKey.TIME_FRAME, timeFrame);
        params.put(ApiConstants.ParamKey.SORT, sort);

        addSubscription(mApiStores.getShotsList(params), new SubscriberCallBack(new ApiCallback<List<ShotsEntity>>() {

            @Override
            public void onSuccess(List<ShotsEntity> entity) {
                mView.onSuccess(entity);
            }

            @Override
            public void onFailure(int code, String msg) {
                mView.onFailed(msg);
            }

            @Override
            public void onCompleted() {
                mView.onFinished();
            }
        }));
    }

}
