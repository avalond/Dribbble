package com.hunter.dribbble.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.hunter.dribbble.R;
import com.hunter.library.util.SnackbarUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        SnackbarUtils.show(getWindow().getDecorView(), text, ContextCompat.getColor(this, R.color.colorPrimary));
    }

}