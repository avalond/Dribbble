package com.hunter.dribbble.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunter.dribbble.utils.SnackbarUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    protected AppCompatActivity mActivity;

    private CompositeSubscription mSubscription;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();

        init();
    }

    protected abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        onUnSubscribe();
    }

    public void onUnSubscribe() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription subscription) {
        mSubscription = new CompositeSubscription();
        mSubscription.add(subscription);
    }

    public void toast(String text) {
        SnackbarUtils.show(getView(), text, mActivity);
    }
}
