package com.hunter.dribbble;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hunter.lib.util.SPUtils;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

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

        initMaterialDrawer();
    }

    private void initMaterialDrawer() {
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.clear(imageView);
            }

            @Override
            public Drawable placeholder(Context context, String tag) {
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(context);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(context).iconText(" ")
                                                       .backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary)
                                                       .sizeDp(56);
                }

                return super.placeholder(context, tag);
            }
        });
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
        return !TextUtils.isEmpty(mToken);
    }

}
