package com.hunter.dribbble;

import android.app.Application;
import android.text.TextUtils;

import com.hunter.lib.util.SPUtils;

public class App extends Application {

    private static App sApp;

    private String mToken;

    private int mLayoutType;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        mToken = (String) SPUtils.get(this, AppConstants.SP_KEE_ACCESS_TOKEN, "");
        mLayoutType = (int) SPUtils.get(this, AppConstants.SP_VIEW_MODE, AppConstants.VIEW_MODE_SMALL_WITH_INFO);
    }

    public static App getInstance() {
        return sApp;
    }

    public int getViewMode() {
        return mLayoutType;
    }

    public void setViewMode(int layoutType) {
        mLayoutType = layoutType;
        SPUtils.put(this, AppConstants.SP_VIEW_MODE, layoutType);
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public boolean isLogin() {
        return TextUtils.isEmpty(mToken);
    }

}
