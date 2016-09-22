package com.hunter.dribbble.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hunter.dribbble.R;

public class ProportionImageView extends ImageView {

    private int mProportionHeight;
    private int mProportionWidth;

    public ProportionImageView(Context context) {
        super(context);
    }

    public ProportionImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProportionImageView, defStyle, 0);
        mProportionHeight = a.getInt(R.styleable.ProportionImageView_proportion_width, 0);
        mProportionWidth = a.getInt(R.styleable.ProportionImageView_proportion_height, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mProportionHeight != 0 && mProportionWidth != 0) {
            setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
            int childWidthSize = getMeasuredWidth();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize * mProportionWidth / mProportionHeight,
                                                            MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
