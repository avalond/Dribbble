package com.hunter.cookies.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunter.cookies.utils.SnackbarUtils;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected Context mContext;

    protected View mRootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view, savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void init(View view, Bundle savedInstanceState);

    public void showToast(CharSequence msg) {
        SnackbarUtils.show(mActivity.getWindow().getDecorView(), msg, mContext);
    }

    public void showToastForStrong(String normal, String strong) {
        CharSequence msg = Html.fromHtml(normal + "<strong>「" + strong + "」</strong>");
        SnackbarUtils.show(mActivity.getWindow().getDecorView(), msg, mContext);
    }

    public void showToastForStrongWithAction(String normal, String strong, String actionText,
                                             View.OnClickListener listener) {
        CharSequence msg = Html.fromHtml(normal + "<strong>「" + strong + "」</strong>");
        SnackbarUtils.showWithAction(mActivity.getWindow().getDecorView(), msg, mContext, actionText, listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        SnackbarUtils.releaseSnackBar();
    }
}

