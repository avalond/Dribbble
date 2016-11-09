package com.hunter.adapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class ScaleAlphaAnimation implements BaseAnimation {

    private static final float DEFAULT_SCALE_FROM = .9f;
    private final float mFrom;

    public ScaleAlphaAnimation() {
        this(DEFAULT_SCALE_FROM);
    }

    public ScaleAlphaAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1f);
        return new ObjectAnimator[]{alpha, scaleX, scaleY};
    }
}