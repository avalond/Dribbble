package com.hunter.dribbble.widget.loading;

import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class LoadPagerLayout extends FrameLayout {

    private View mContentView;
    private View mLoadingView;
    private View mRetryView;
    private View mEmptyView;

    private LayoutInflater mInflater;

    private OnclickEmptyListener mListener;

    public LoadPagerLayout(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }

    public void showContent() {
        showViewOnMainThread(mContentView);
    }

    public void showLoading() {
        showViewOnMainThread(mLoadingView);
    }

    public void showRetry() {
        showViewOnMainThread(mRetryView);
    }

    public void showEmpty() {
        showViewOnMainThread(mEmptyView);
    }

    private void showViewOnMainThread(final View view) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showView(view);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(view);
                }
            });
        }
    }

    private void showView(View view) {
        if (view == null) return;

        if (view == mLoadingView) {
            mLoadingView.setVisibility(View.VISIBLE);
            if (mRetryView != null) mRetryView.setVisibility(View.GONE);
            if (mContentView != null) mContentView.setVisibility(View.GONE);
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        } else if (view == mRetryView) {
            mRetryView.setVisibility(View.VISIBLE);
            if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
            if (mContentView != null) mContentView.setVisibility(View.GONE);
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        } else if (view == mContentView) {
            mContentView.setVisibility(View.VISIBLE);
            if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null) mRetryView.setVisibility(View.GONE);
            if (mEmptyView != null) mEmptyView.setVisibility(View.GONE);
        } else if (view == mEmptyView) {
            mEmptyView.setVisibility(View.VISIBLE);
            if (mLoadingView != null) mLoadingView.setVisibility(View.GONE);
            if (mRetryView != null) mRetryView.setVisibility(View.GONE);
            if (mContentView != null) mContentView.setVisibility(View.GONE);
        }
    }

    public void setContentView(View view) {
        mContentView = view;
        addView(mContentView);
    }

    public void setLoadingView(int layoutId) {
        mLoadingView = mInflater.inflate(layoutId, this, false);
        addView(mLoadingView);
    }

    public void setEmptyView(int layoutId) {
        mEmptyView = mInflater.inflate(layoutId, this, false);
        addView(mEmptyView);
    }

    public void setRetryView(int layoutId) {
        mRetryView = mInflater.inflate(layoutId, this, false);
        mRetryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) mListener.onRetryClick(mRetryView);
            }
        });
        addView(mRetryView);
    }

    public View getRetryView() {
        return mRetryView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View getContentView() {
        return mContentView;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyListener(OnclickEmptyListener listener) {
        mListener = listener;
    }
}
