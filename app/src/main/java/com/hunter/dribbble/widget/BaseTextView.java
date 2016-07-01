package com.hunter.dribbble.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class BaseTextView extends TextView {

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceCodePro-Regular.ttf"));
    }
}
