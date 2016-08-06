package com.hunter.dribbble.widget.loading;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

public class LoadPagerManager {

    public static final int NO_LAYOUT_ID = 0;

    public static int LOADING_LAYOUT_ID = NO_LAYOUT_ID;
    public static int RETRY_LAYOUT_ID   = NO_LAYOUT_ID;
    public static int EMPTY_LAYOUT_ID   = NO_LAYOUT_ID;

    public LoadPagerLayout mLoadPagerLayout;

    private ViewGroup mContentParent;
    private View      mContentView;
    private Context   mContext;

    private int mIndex;

    public LoadPagerManager(Activity activity, OnclickEmptyListener loadPagerListener) {
        mContext = activity;
        mContentParent = (ViewGroup) activity.findViewById(android.R.id.content);
        mContentView = mContentParent.getChildAt(0);

        init(loadPagerListener);
    }

    public LoadPagerManager(Fragment fragment, OnclickEmptyListener loadPagerListener) {
        mContext = fragment.getActivity();
        mContentParent = ((ViewGroup) (fragment.getView().getParent()));
        mContentView = mContentParent.getChildAt(0);

        init(loadPagerListener);
    }

    public LoadPagerManager(View view, OnclickEmptyListener loadPagerListener) {
        mContext = view.getContext();
        mContentParent = (ViewGroup) (view.getParent());

        mContentView = view;
        int childCount = mContentParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (mContentParent.getChildAt(i) == mContentView) {
                mIndex = i;
                break;
            }
        }

        init(loadPagerListener);
    }

    private void init(OnclickEmptyListener listener) {
        mLoadPagerLayout = new LoadPagerLayout(mContext);
        mContentParent.removeView(mContentView);
        mContentParent.addView(mLoadPagerLayout, mIndex, mContentView.getLayoutParams());

        mLoadPagerLayout.setEmptyListener(listener);

        mLoadPagerLayout.setContentView(mContentView);
        mLoadPagerLayout.setEmptyView(EMPTY_LAYOUT_ID);
        mLoadPagerLayout.setLoadingView(LOADING_LAYOUT_ID);
        mLoadPagerLayout.setRetryView(RETRY_LAYOUT_ID);
    }

    public void showLoading() {
        mLoadPagerLayout.showLoading();
    }

    public void showRetry() {
        mLoadPagerLayout.showRetry();
    }

    public void showContent() {
        mLoadPagerLayout.showContent();
    }

    public void showEmpty() {
        mLoadPagerLayout.showEmpty();
    }

}
