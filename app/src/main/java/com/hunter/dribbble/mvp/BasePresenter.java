package com.hunter.dribbble.mvp;

import com.hunter.dribbble.api.ApiClient;
import com.hunter.dribbble.api.ApiStores;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V> {

    public V mView;

    public ApiStores mApiStores;

    private CompositeSubscription mSubscription;

    public void attachViewForRest(V view) {
        mView = view;
        mApiStores = ApiClient.createForRest();
    }

    public void attachViewForOAuth(V view) {
        mView = view;
        mApiStores = ApiClient.createForOAtuh();
    }

    public void detachView() {
        mView = null;
        onUnSubscribe();
    }

    public void onUnSubscribe() {
        if (mSubscription != null && mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mSubscription == null) {
            mSubscription = new CompositeSubscription();
        }

        mSubscription.add(observable.subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(subscriber));
    }
}
