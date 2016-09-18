package com.hunter.dribbble.base.mvp;

import android.os.Bundle;

import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.utils.SnackbarUtils;

public abstract class BaseMVPActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity {

    public T mPresenter;
    public E mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    public void showDialog(String msg) {
    }

    public void dismissDialog() {
    }

    public void toast(String msg) {
        SnackbarUtils.show(getWindow().getDecorView(), msg, this);
    }
}