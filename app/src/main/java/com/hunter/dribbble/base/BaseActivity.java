package com.hunter.dribbble.base;

import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;

import com.hunter.dribbble.utils.SnackbarUtils;

public abstract class BaseActivity extends AppCompatActivity {

    public void showToast(CharSequence msg) {
        SnackbarUtils.show(getWindow().getDecorView(), msg, this);
    }

    public void showToastForStrong(String normal, String strong) {
        CharSequence msg = Html.fromHtml(normal + " <strong>「" + strong + "」</strong>");
        SnackbarUtils.show(getWindow().getDecorView(), msg, this);
    }

    public void showToastForStrongWithAction(String normal, String strong, String actionText,
                                             View.OnClickListener listener) {
        CharSequence msg = Html.fromHtml(normal + "<strong>「" + strong + "」</strong>");
        SnackbarUtils.showWithAction(getWindow().getDecorView(), msg, this, actionText, listener);
    }

    public void showToastForStrongWithAction(String normal, String strong, View.OnClickListener listener) {
        CharSequence msg = Html.fromHtml(normal + "<strong>「" + strong + "」</strong>");
        SnackbarUtils.showWithAction(getWindow().getDecorView(), msg, this, listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SnackbarUtils.releaseSnackBar();
    }
}