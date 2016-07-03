package com.hunter.dribbble.widget.spinner;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hunter.dribbble.R;
import com.hunter.library.base.BaseAdapter;

import java.util.Arrays;
import java.util.TimerTask;

public class MaterialSpinner extends TextView {

    private Context mContext;

    private OnItemSelectedListener mListener;

    private RecyclerView mRecyclerView;
    private PopupWindow  mPopDropDown;
    private Drawable     mArrowDrawable;

    private SimpleSpinnerAdapter mAdapter;

    private int  mIconPadding;
    private Rect mRect;
    private int  mDrawableWidth;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopDropDown.setElevation(10);
        }
        mPopDropDown.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.bg_spinner));
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
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isEnabled() && isClickable()) {
                if (!mPopDropDown.isShowing()) expand();
                else collapse();
            }
        }
        return super.onTouchEvent(event);
    }

    public void expand() {
        animateArrow(true);
        mPopDropDown.showAsDropDown(this);
    }

    public void collapse() {
        animateArrow(false);
        mPopDropDown.dismiss();
    }

    private void animateArrow(boolean isDrop) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mArrowDrawable, "rotation", isDrop ? 180 : 0, isDrop ? 0 : 180);
        animator.setDuration(200);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public void setItems(String... items) {
        mAdapter = new SimpleSpinnerAdapter(mContext, Arrays.asList(items));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(int position, final String data) {
                if (mListener != null) {
                    mListener.onItemSelected(MaterialSpinner.this, position, mAdapter.getItemData(position));
                }
                postDelayed(new TimerTask() {
                    @Override
                    public void run() {
                        collapse();
                        setText(data);
                        requestLayout();
                    }
                }, 250);
            }
        });
    }

    public void setOnItemClickListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

}
