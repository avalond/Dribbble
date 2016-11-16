package com.hunter.cookies.utils;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class IntentUtils {

    public static void startActivity(Activity activity, View v, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity, v, "shareTransition").toBundle();
            activity.startActivity(intent, bundle);
        } else {
            activity.startActivity(intent);
        }
    }

    public static void startActivityToBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        Intent chooserIntent = Intent.createChooser(intent, "选择一个应用打开该链接");
        if (chooserIntent == null) return;
        context.startActivity(chooserIntent);
    }
}
