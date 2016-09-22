package com.hunter.dribbble.base.mvp;

import android.os.Bundle;

import com.hunter.dribbble.base.BaseFragment;
import com.hunter.dribbble.utils.SnackbarUtils;

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

    public void showDialog(String msg) {
    }

    public void onCompleted() {

    }

    public void showToast(String msg) {
        SnackbarUtils.show(mActivity.getWindow().getDecorView(), msg, mContext);
    }
}