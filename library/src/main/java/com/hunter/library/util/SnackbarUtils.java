package com.hunter.library.util;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarUtils {

    private static Snackbar mSnackbar;

    public static void show(View container, CharSequence text, int backgroundColor) {
        if (mSnackbar == null) {
            mSnackbar = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
            mSnackbar.getView().setBackgroundColor(backgroundColor);
        } else {
            mSnackbar.setText(text);
            mSnackbar.setDuration(Snackbar.LENGTH_SHORT);
        }
        mSnackbar.show();
    }

}
