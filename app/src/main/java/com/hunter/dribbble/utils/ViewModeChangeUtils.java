package com.hunter.dribbble.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hunter.dribbble.AppConstants;
import com.hunter.dribbble.entity.ShotsEntity;
import com.hunter.dribbble.ui.adapter.ShotsHomeAdapter;
import com.hunter.library.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class ViewModeChangeUtils {

    public static void changeViewMode(RecyclerView rv, Context context, ShotsHomeAdapter adapter, int viewMode) {
        rv.getRecycledViewPool().clear();
        setLayoutManager(rv, context, adapter, viewMode);
        List<ShotsEntity> data = new ArrayList<>(adapter.getData());
        adapter.reloadData(data);
        SPUtils.put(context, AppConstants.SP_VIEW_MODE, viewMode);
    }

    public static void setLayoutManager(RecyclerView rv, Context context, ShotsHomeAdapter adapter, int viewMode) {
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

        adapter.setLayoutId(viewMode);
        rv.setRecycledViewPool(new RecyclerView.RecycledViewPool());
    }
}
