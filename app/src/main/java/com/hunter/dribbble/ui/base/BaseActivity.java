package com.hunter.dribbble.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.hunter.dribbble.utils.SnackbarUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity mActivity;

    private CompositeSubscription mSubscription;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
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

    protected void startActivity(Class nextActivity) {
        startActivity(new Intent(this, nextActivity));
    }

    public void toast(String text) {
        SnackbarUtils.show(getWindow().getDecorView(), text, this);
    }

}