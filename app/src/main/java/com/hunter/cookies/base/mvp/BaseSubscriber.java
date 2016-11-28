package com.hunter.cookies.base.mvp;

import rx.Subscriber;

public abstract class BaseSubscriber<T> extends Subscriber<T> {

    private BaseView mView;

    private String mDialogMsg;

    private boolean mIsShowLoading;

    public BaseSubscriber(BaseView view) {
        mView = view;
    }

    public BaseSubscriber(BaseView view, boolean isShowLoading) {
        mView = view;
        mIsShowLoading = isShowLoading;
    }

    @Override
    public void onStart() {
        if (mIsShowLoading && mView != null) mView.showLoading();
    }

    @Override
    public void onCompleted() {
        if (mView != null) {
            mView.onComplete();
            if (mIsShowLoading) mView.showLoading();
        }
        mView = null;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onFail(e.getMessage());
        if (mView != null) {
            mView.onComplete();
            if (e.getMessage().contains("404")) return;
            mView.showError();
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