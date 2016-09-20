package com.hunter.dribbble.api;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpAdapter {

    private static final int DEFAULT_TIMEOUT = 30;

    public static <T> void http(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

}
