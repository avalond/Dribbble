package com.hunter.cookies.base.mvp;

import android.text.TextUtils;

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
        if (!TextUtils.isEmpty(mDialogMsg) && mView != null) mView.showLoading(mDialogMsg);
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.onComplete();
            if (!TextUtils.isEmpty(mDialogMsg)) mView.showLoading(mDialogMsg);
        }
        mView = null;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFail(e.getMessage());
        if (mView != null) {
            mView.onComplete();
            mView.showToast(e.getMessage());
        }
    }

    @Override
    public void onNext(T entity) {
        onSuccess(entity);
    }

    protected abstract void onSuccess(T t);

    protected void onFail(String msg) {
    }

}