package com.hunter.cookies.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.hunter.cookies.App;
import com.hunter.cookies.AppConstants;
import com.hunter.cookies.R;
import com.hunter.lib.util.SPUtils;

public class ViewModelUtils {

    public static final int VIEW_MODE_TITLE_RES[] = {
            R.string.menu_view_mode_small_info,
            R.string.menu_view_mode_small,
            R.string.menu_view_mode_large_info,
            R.string.menu_view_mode_large
    };

    public static void changeLayoutManager(RecyclerView rv, int viewMode) {
        switch (viewMode) {
            case AppConstants.VIEW_MODE_SMALL_WITH_INFO:
            case AppConstants.VIEW_MODE_SMALL:
                rv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            case AppConstants.VIEW_MODE_LARGE_WITH_INFO:
            case AppConstants.VIEW_MODE_LARGE:
                rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
                break;
        }

        SPUtils.put(rv.getContext(), AppConstants.SP_VIEW_MODE, viewMode);
        App.getAppConfig().setViewMode(viewMode);
    }
}