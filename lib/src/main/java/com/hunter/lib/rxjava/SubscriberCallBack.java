package com.hunter.library.rxjava;

import com.hunter.library.util.LogUtils;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public class SubscriberCallBack<T> extends Subscriber<T> {

    private ApiCallback<T> mCallback;

    public SubscriberCallBack(ApiCallback<T> apiCallback) {
        mCallback = apiCallback;
    }

    @Override
    public void onCompleted() {
        mCallback.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        LogUtils.d(e.getMessage());

        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            mCallback.onFailure(code, msg);
        } else {
            mCallback.onFailure(0, e.getMessage());
        }
        mCallback.onCompleted();
    }

    @Override
    public void onNext(T t) {
        mCallback.onSuccess(t);
    }
}
