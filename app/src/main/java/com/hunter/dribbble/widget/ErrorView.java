package com.hunter.dribbble.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hunter.dribbble.R;

public class ErrorView extends FrameLayout {

    private TextView mTvErrorInfo;
    private TextView mTvErrorRetry;

    private OnClickListener mListener;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View errorView = LayoutInflater.from(getContext()).inflate(R.layout.layout_error_view, this);
        mTvErrorInfo = (TextView) errorView.findViewById(R.id.tv_error_view_msg);
        mTvErrorRetry = (TextView) errorView.findViewById(R.id.tv_error_view_retry);
        if (mListener != null) mTvErrorRetry.setOnClickListener(mListener);
    }

    public void setErrorInfo(String text) {
        mTvErrorInfo.setText(text);
    }

    public void setErrorImage(@DrawableRes int resId) {
        Drawable drawableError = ContextCompat.getDrawable(getContext(), resId);
        drawableError.setBounds(0, 0, drawableError.getIntrinsicHeight(), drawableError.getIntrinsicWidth());
        mTvErrorInfo.setCompoundDrawables(null, drawableError, null, null);
    }

    public void setErrorRetry(String text) {
        mTvErrorRetry.setText(text);
    }

    public void setRetryListener(OnClickListener listener) {
        mListener = listener;
    }
}