package com.hunter.cookies.base.mvp;

public interface BaseView {

    void showLoading();

    void onComplete();

    void showToast(CharSequence msg);

    void showError(CharSequence msg);
}