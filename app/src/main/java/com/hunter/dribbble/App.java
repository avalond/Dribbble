package com.hunter.dribbble;

import android.app.Application;

import com.hunter.library.util.FrescoUtils;
import com.hunter.library.util.SPUtils;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath(
                "fonts/SourceCodePro-Regular.ttf").setFontAttrId(R.attr.fontPath).build());
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
