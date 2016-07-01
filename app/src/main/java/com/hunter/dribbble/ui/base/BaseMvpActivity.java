package com.hunter.dribbble.ui.base;

import android.os.Bundle;

import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.dribbble.mvp.BaseView;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    protected abstract P createPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
