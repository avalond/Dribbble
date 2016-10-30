package com.hunter.cookies.widget.spinner;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hunter.cookies.R;
import com.hunter.lib.base.BaseAdapter;

import java.util.Arrays;
import java.util.TimerTask;

public class MaterialSpinner extends TextView implements View.OnClickListener {

    private Context mContext;

    private RecyclerView mRecyclerView;
    private PopupWindow mPopDropDown;
    private Drawable mArrowDrawable;

    private SimpleSpinnerAdapter mAdapter;

    private int mIconPadding;
    private Rect mRect;
    private int mDrawableWidth;

    public interface OnItemSelectedListener<T> {

        void onItemSelected(View view, int position, T item);
    }

    public MaterialSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialSpinner);
        mIconPadding = typedArray.getDimensionPixelSize(R.styleable.MaterialSpinner_icon_padding, 0);
        typedArray.recycle();

        mRect = new Rect();
        setIconPadding(mIconPadding);

        init();
    }

    private void init() {
        mRecyclerView = new RecyclerView(mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mPopDropDown = new PopupWindow(mContext);
        mPopDropDown.setContentView(mRecyclerView);
        mPopDropDown.setOutsideTouchable(true);

        ObjectAnimator rotateAnim = ObjectAnimator.ofInt(mArrowDrawable, "level", 0, 0);
        rotateAnim.setDuration(200);
        rotateAnim.setInterpolator(new DecelerateInterpolator());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopDropDown.setElevation(10);
        }
        mPopDropDown.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_spinner));

        setOnClickListener(this);
    }

    public void setIconPadding(int padding) {
        mIconPadding = padding;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mPopDropDown.setWidth(MeasureSpec.getSize(widthMeasureSpec));
        mPopDropDown.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), mRect);

        int textWidth = mRect.width();
        int contentWidth = mDrawableWidth + mIconPadding * 2 + textWidth;
        int horizontalPadding = (int) ((getWidth() / 2.0) - (contentWidth / 2.0));

        setCompoundDrawablePadding(-horizontalPadding + mIconPadding);

        setPadding(0, getPaddingTop(), horizontalPadding, getPaddingBottom());
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        if (right != null) {
            mDrawableWidth = right.getIntrinsicWidth();
            mArrowDrawable = right;
        }
        requestLayout();
    }

    @Override
    public void onClick(View v) {
        if (!mPopDropDown.isShowing()) mPopDropDown.showAsDropDown(this);
    }

    public void setItems(final OnItemSelectedListener listener, String... items) {
        mAdapter = new SimpleSpinnerAdapter(mContext, Arrays.asList(items));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(int position, final String data) {
                listener.onItemSelected(MaterialSpinner.this, position, mAdapter.getItemData(position));
                post(new TimerTask() {
                    @Override
                    public void run() {
                        mPopDropDown.dismiss();
                        setText(data);
                        requestLayout();
                    }
                });
            }
        });
    }

}
