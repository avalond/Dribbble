package com.hunter.dribbble.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.hunter.lib.util.DensityUtils;

public class FrescoImageProgressBar extends Drawable {

    private static final float OUTER_RADIUS = 192;
    private static final float INNER_RADIUS = 180;
    private static final float STROKE       = 8;

    private static final int TOTAL_PROGRESS = 10000;
    private static final int MIN_PROGRESS   = 0;

    private static final int COLOR = 0xB9B9B9B9;

    private Paint mOuterPaint;
    private Paint mInnerPaint;

    private float mOuterRadius;
    private float mInnerRadius;
    private float mStroke;

    private int mProgress;

    private Context mContext;

    public FrescoImageProgressBar(Context context) {
        mContext = context;

        initAttrs();
        initPaint();
    }

    private void initAttrs() {
        mInnerRadius = DensityUtils.px2dp(mContext, INNER_RADIUS);
        mOuterRadius = DensityUtils.px2dp(mContext, OUTER_RADIUS);
        mStroke = DensityUtils.px2dp(mContext, STROKE);
    }

    private void initPaint() {
        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(COLOR);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(mStroke);

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(COLOR);
        mInnerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bound = getBounds();
        int centerX = bound.centerX();
        int centerY = bound.centerY();
        canvas.drawCircle(centerX, centerY, mOuterRadius, mOuterPaint);

        RectF oval = new RectF();
        oval.left = centerX - mInnerRadius;
        oval.top = centerY - mInnerRadius;
        oval.right = centerX + mInnerRadius;
        oval.bottom = centerY + mInnerRadius;
        canvas.drawArc(oval, -90, (float) mProgress / TOTAL_PROGRESS * 360, true, mInnerPaint);
    }

    @Override
    protected boolean onLevelChange(int level) {
        mProgress = level;
        if (mProgress > MIN_PROGRESS && mProgress < TOTAL_PROGRESS) {
            invalidateSelf();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mInnerPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}