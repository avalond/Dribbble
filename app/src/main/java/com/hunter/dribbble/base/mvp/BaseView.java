package com.hunter.dribbble.base.mvp;

public interface BaseView {

    void showDialog(String msg);

    void onCompleted();

    void showToast(String msg);
}