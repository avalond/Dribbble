package com.hunter.dribbble.base.mvp;

public interface BaseView {

    void showDialog(String msg);

    void dismissDialog();

    void showToast(String msg);
}