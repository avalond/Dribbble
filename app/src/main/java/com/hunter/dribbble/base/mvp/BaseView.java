package com.hunter.dribbble.base.mvp;

public interface BaseView {

    void showDialog(CharSequence msg);

    void onCompleted();

    void showToast(CharSequence msg);
}