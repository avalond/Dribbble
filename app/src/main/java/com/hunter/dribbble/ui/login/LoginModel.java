package com.hunter.dribbble.ui.login;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.api.ApiConstants;
import com.hunter.dribbble.entity.TokenEntity;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<TokenEntity> getToken(String code) {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.ParamKey.CLIENT_ID, ApiConstants.ParamValue.CLIENT_ID);
        params.put(ApiConstants.ParamKey.CLIENT_SECRET, ApiConstants.ParamValue.CLIENT_SECRET);
        params.put(ApiConstants.ParamKey.REDIRECT_URI, ApiConstants.ParamValue.REDIRECT_URI);
        params.put(ApiConstants.ParamKey.CODE, code);

        return ApiClient.createForOAtuh().getToken(params);
    }
}
