package com.hunter.dribbble.ui.base;

import android.os.Bundle;

import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.dribbble.mvp.BaseView;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {

    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onStarted() {
    }

    @Override
    public void onFinished() {
    }

    @Override
    public void onFailed(String msg) {
        toast(msg);
    }
}
