package com.hunter.dribbble.base.mvp;

public interface BaseView {

    void showDialog(CharSequence msg);

    void onSuccess();

    void onError();

    void showToast(CharSequence msg);
}