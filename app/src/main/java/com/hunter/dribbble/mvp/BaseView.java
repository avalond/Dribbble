package com.hunter.dribbble.mvp;

public interface BaseView {

    void onStarted();

    void onFinished();

    void onFailed(String msg);
}
