package com.hunter.dribbble.base.mvp;

import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BasePresenter<M, V> {

    public Context context;
    private Reference<V> mViewRef;
    public M mModel;
    public V mView;

    public void setVM(V v, M m) {
        mViewRef = new WeakReference<>(v);
        mView = mViewRef.get();
        mModel = m;
        onStart();
    }

    public void onStart() {

    }

    public void onDestroy() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
