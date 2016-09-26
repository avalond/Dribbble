package com.hunter.dribbble.base.mvp;

import android.os.Bundle;

import com.hunter.dribbble.base.BaseFragment;

public abstract class BaseMVPFragment<P extends BasePresenter, M extends BaseModel> extends BaseFragment {

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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();
    }

    public void showDialog(CharSequence msg) {
    }

    public void onSuccess() {

    }

    public void onError(){

    }

}