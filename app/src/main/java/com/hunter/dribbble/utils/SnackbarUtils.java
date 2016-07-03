package com.hunter.dribbble.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.hunter.dribbble.R;

public class SnackbarUtils {

    private static Snackbar mSnackbar;

    public static void show(View container, CharSequence text, Context context) {
        if (mSnackbar == null) {
            mSnackbar = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
            View content = mSnackbar.getView();
            content.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            ((TextView) content.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(context,
                                                                                                      R.color.text_white));
        } else {
            mSnackbar.setText(text);
            mSnackbar.setDuration(Snackbar.LENGTH_SHORT);
        }
        mSnackbar.show();
    }

}
