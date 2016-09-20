package com.hunter.dribbble;

import android.app.Application;

import com.hunter.dribbble.widget.loading.LoadPagerManager;
import com.hunter.lib.util.FrescoUtils;
import com.hunter.lib.util.SPUtils;

public class App extends Application {

    private static App sApp;

    private String sToken;

    private int mLayoutType;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        initDate();

        FrescoUtils.getInstance().init(this);

        LoadPagerManager.RETRY_LAYOUT_ID = R.layout.load_pager_retry;
        LoadPagerManager.LOADING_LAYOUT_ID = R.layout.load_pager_loading;
        LoadPagerManager.EMPTY_LAYOUT_ID = R.layout.load_pager_empty;
    }

    public static App getInstance() {
        return sApp;
    }

    private void initDate() {
        mLayoutType = (int) SPUtils.get(this, AppConstants.SP_VIEW_MODE, AppConstants.VIEW_MODE_SMALL_WITH_INFO);
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
