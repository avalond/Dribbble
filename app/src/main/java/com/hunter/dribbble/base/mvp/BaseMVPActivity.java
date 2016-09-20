package com.hunter.dribbble.base.mvp;

import android.os.Bundle;

import com.hunter.dribbble.base.BaseActivity;
import com.hunter.dribbble.utils.SnackbarUtils;

public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends BaseActivity {

    public P mPresenter;
    public M mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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

    public void showDialog(String msg) {

    }

    public void onCompleted() {

    }

    public void showToast(String msg) {
        SnackbarUtils.show(getWindow().getDecorView(), msg, this);
    }

}