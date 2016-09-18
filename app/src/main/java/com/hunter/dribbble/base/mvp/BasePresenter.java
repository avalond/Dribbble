package com.hunter.dribbble.base.mvp;

import android.content.Context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasePresenter<M, V> {

    public  Context      context;
    private Reference<V> mViewRef;
    public  M            mModel;
    public  V            mView;

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
}
