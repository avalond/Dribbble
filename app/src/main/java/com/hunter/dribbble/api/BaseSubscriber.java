package com.hunter.dribbble.api;

import android.text.TextUtils;

import com.hunter.dribbble.base.mvp.BaseView;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseView mView;

    private String mDialogMsg;

    public BaseSubscriber(BaseView view) {
        mView = view;
    }

    public BaseSubscriber(BaseView view, String dialogMsg) {
        mView = view;
        mDialogMsg = dialogMsg;
    }

    @Override
    public void onStart() {
        if (!TextUtils.isEmpty(mDialogMsg) && mView != null) mView.showDialog(mDialogMsg);
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.onCompleted();
            if (!TextUtils.isEmpty(mDialogMsg)) mView.showDialog(mDialogMsg);
        }
        mView = null;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFail(e.getMessage());
        onCompleted();
    }

    @Override
    public void onNext(T entity) {
        onSuccess(entity);
    }

    protected abstract void onSuccess(T t);

    protected void onFail(String msg) {
    }

}