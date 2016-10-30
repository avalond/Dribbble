package com.hunter.cookies.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hunter.cookies.R;

public class SnackbarUtils {

    private static Snackbar mSnackbar;
    private static Snackbar mSnackbarWithAction;

    public static void show(View container, CharSequence text, Context context) {
        if (mSnackbar == null) {
            mSnackbar = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
            View content = mSnackbar.getView();
            content.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            TextView textView = (TextView) content.findViewById(R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(context, R.color.text_white));
        }
        mSnackbar.setText(text);
        mSnackbar.setDuration(Snackbar.LENGTH_LONG);
        mSnackbar.show();
    }

    public static void showWithAction(View container, CharSequence text, Context context,
                                      View.OnClickListener listener) {
        showWithAction(container, text, context, "", listener);
    }

    public static void showWithAction(View container, CharSequence text, Context context, String actionText,
                                      View.OnClickListener listener) {
        if (mSnackbarWithAction == null) {
            mSnackbarWithAction = Snackbar.make(container, text, Snackbar.LENGTH_LONG);
            View content = mSnackbarWithAction.getView();
            content.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            TextView textView = (TextView) content.findViewById(R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(context, R.color.text_white));
            mSnackbarWithAction.setDuration(Snackbar.LENGTH_LONG);
        }
        mSnackbarWithAction.setText(text);
        if (TextUtils.isEmpty(actionText)) actionText = "确定";
        mSnackbarWithAction.setAction(actionText, listener);
        mSnackbarWithAction.show();
    }

    public static void releaseSnackBar() {
        if (mSnackbarWithAction != null) mSnackbarWithAction = null;
        if (mSnackbar != null) mSnackbar = null;
    }
}
