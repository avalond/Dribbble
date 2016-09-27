package com.hunter.dribbble.widget;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

public class LoadFrameLayout extends FrameLayout {

    private static final int SHOW_CONTENT_VIEW = 0;
    private static final int SHOW_ERROR_VEW = 1;

    private View mErrorView;
    private View mContentView;

    private int mShowIndex;

    public LoadFrameLayout(Context context) {
        this(context, null);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentView = getChildAt(0);
    }

    public void setErrorView(View errorView) {
        if (this.mErrorView != errorView) {
            if (this.mErrorView != null) {
                removeView(this.mErrorView);
            }
            this.mErrorView = errorView;
            addView(errorView);
            this.mErrorView.setVisibility(GONE);
        }
    }

    public void setContentView(View contentView) {
        if (mContentView != contentView) {
            if (mContentView != null) {
                removeView(mContentView);
            }
            mContentView = contentView;
            addView(contentView);
        }
    }

    public void setErrorView(@LayoutRes int errorViewResId) {
        View view = LayoutInflater.from(getContext()).inflate(errorViewResId, this, false);
        setErrorView(view);
    }

    public void setContentView(@LayoutRes int contentViewResId) {
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(contentViewResId, this, false);
        setContentView(view);
    }

    public void showErrorView() {
        if (mShowIndex == SHOW_ERROR_VEW) return;
        showSingleView(mErrorView);
        mShowIndex = SHOW_ERROR_VEW;
    }

    public void showContentView() {
        if (mShowIndex == SHOW_CONTENT_VIEW) return;
        showSingleView(mContentView);
        mShowIndex = SHOW_CONTENT_VIEW;
    }

    private void showSingleView(View specialView) {
        if (specialView == null) return;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child == specialView) {
                child.setVisibility(VISIBLE);
            } else {
                child.setVisibility(GONE);
            }
        }
    }

}