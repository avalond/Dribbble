package com.hunter.dribbble;

import android.app.Application;

import com.hunter.lib.util.SPUtils;

public class App extends Application {

    private static App sApp;

    private String sToken;

    private int mLayoutType;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

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
        return sToken;
    }

    public void setToken(String token) {
        sToken = token;
    }
}
