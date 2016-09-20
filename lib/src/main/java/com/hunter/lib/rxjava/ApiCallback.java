package com.hunter.lib.rxjava;

public interface ApiCallback<T> {

    void onSuccess(T entity);

    void onFailure(int code, String msg);

    void onCompleted();

}
