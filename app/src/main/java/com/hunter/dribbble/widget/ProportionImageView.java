package com.hunter.dribbble.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hunter.dribbble.R;

public class ProportionImageView extends ImageView {

    private float mProportionWH;

    public ProportionImageView(Context context) {
        super(context);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView, defStyle, 0);
        mProportionWH = a.getFloat(R.styleable.ProportionImageView_proportion_w_h, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mProportionWH != 0) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() / mProportionWH) - 2,
                                                            MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
