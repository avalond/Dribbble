package com.hunter.dribbble.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.hunter.dribbble.utils.SnackbarUtils;

public abstract class BaseActivity extends AppCompatActivity {

    public void showToast(CharSequence msg) {
        SnackbarUtils.show(getWindow().getDecorView(), msg, this);
    }
}