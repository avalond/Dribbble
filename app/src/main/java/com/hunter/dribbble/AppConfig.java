package com.hunter.dribbble;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.hunter.lib.util.SPUtils;

public class AppConfig {

    private String mToken;

    private int mViewMode;

    private boolean mShowGIF;

    private boolean mIsUseWIFI;

    private SharedPreferences mPreferences;

    public AppConfig(Context context) {
        mPreferences = context.getSharedPreferences(SPUtils.FILE_NAME, Context.MODE_PRIVATE);

        mToken = (String) SPUtils.get(AppConstants.SP_ACCESS_TOKEN, "", mPreferences);
        mViewMode = (int) SPUtils.get(AppConstants.SP_VIEW_MODE, AppConstants.VIEW_MODE_SMALL_WITH_INFO, mPreferences);

        mShowGIF = (boolean) SPUtils.get(AppConstants.SP_SHOW_GIF, true, mPreferences);
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(mToken);
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
        SPUtils.put(AppConstants.SP_ACCESS_TOKEN, token, mPreferences);
    }

    public boolean getShowGIFValue() {
        return mShowGIF;
    }

    public void setShowGIF(boolean showGIF) {
        mShowGIF = showGIF;
        SPUtils.put(AppConstants.SP_SHOW_GIF, showGIF, mPreferences);
    }

    public boolean isShowGIF() {
        return mShowGIF && !mIsUseWIFI;
    }

    public int getViewMode() {
        return mViewMode;
    }

    public void setViewMode(int viewMode) {
        mViewMode = viewMode;
        SPUtils.put(AppConstants.SP_VIEW_MODE, viewMode, mPreferences);
    }

    public void setUseWIFI(boolean useWIFI) {
        mIsUseWIFI = useWIFI;
    }
}