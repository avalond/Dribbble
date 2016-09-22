package com.hunter.dribbble.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hunter.dribbble.AppConstants;
import com.hunter.lib.util.SPUtils;

public class LayoutManagerUtils {

    public static void changeLayoutManager(RecyclerView rv, Context context, int viewMode) {
        switch (viewMode) {
            case AppConstants.VIEW_MODE_SMALL_WITH_INFO:
            case AppConstants.VIEW_MODE_SMALL:
                rv.setLayoutManager(new GridLayoutManager(context, 2));
                break;

            case AppConstants.VIEW_MODE_LARGE_WITH_INFO:
            case AppConstants.VIEW_MODE_LARGE:
                rv.setLayoutManager(new LinearLayoutManager(context));
                break;
        }

        SPUtils.put(context, AppConstants.SP_VIEW_MODE, viewMode);
    }
}