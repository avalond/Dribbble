package com.hunter.dribbble.mvp.token;

import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.TokenEntity;
import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.library.rxjava.ApiCallback;
import com.hunter.library.rxjava.SubscriberCallBack;

import java.util.LinkedHashMap;
import java.util.Map;

public class TokenPresenter extends BasePresenter<TokenView> {

    public TokenPresenter(TokenView view) {
        attachViewForOAuth(view);
    }

    public void getToken(String code) {
        mView.onStarted();

        Map<String, String> params = new LinkedHashMap<>();
        params.put(ApiConstants.ParamKey.CLIENT_ID, ApiConstants.ParamValue.CLIENT_ID);
        params.put(ApiConstants.ParamKey.CLIENT_SECRET, ApiConstants.ParamValue.CLIENT_SECRET);
        params.put(ApiConstants.ParamKey.REDIRECT_URI, ApiConstants.ParamValue.REDIRECT_URI);
        params.put(ApiConstants.ParamKey.CODE, code);

        addSubscription(mApiStores.getToken(params), new SubscriberCallBack<>(new ApiCallback<TokenEntity>() {
            @Override
            public void onSuccess(TokenEntity entity) {
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
