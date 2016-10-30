package com.hunter.cookies;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

public class App extends Application {

    private static AppConfig sAppConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppConfig = new AppConfig(this);

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

    public static AppConfig getAppConfig() {
        return sAppConfig;
    }

}
