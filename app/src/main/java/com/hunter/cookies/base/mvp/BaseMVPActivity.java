package com.hunter.cookies.base.mvp;

import android.os.Bundle;

import com.hunter.cookies.base.BaseActivity;

public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity {

    public P mPresenter;
    public M mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    public void showLoading() {

    }

    public void showError(CharSequence errorMsg) {

    }

    public void onComplete() {

    }

}