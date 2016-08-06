package com.hunter.dribbble.ui.base;

import android.os.Bundle;

import com.hunter.dribbble.mvp.BasePresenter;
import com.hunter.dribbble.mvp.BaseView;
import com.hunter.dribbble.widget.loading.OnclickEmptyListener;
import com.hunter.dribbble.widget.loading.LoadPagerManager;

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView,
                                                                                               OnclickEmptyListener {

    protected P mPresenter;

    protected LoadPagerManager mLoadPagerManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
    }

    protected void init() {
//        mLoadPagerManager = new LoadPagerManager(this, this);
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
//        mLoadPagerManager.showLoading();
    }

    @Override
    public void onFinished() {
//        mLoadPagerManager.showContent();
    }

    @Override
    public void onFailed(String msg) {
        toast(msg);
    }
}
