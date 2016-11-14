package com.hunter.adapter.listener;

import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;

import com.hunter.adapter.BaseQuickAdapter;
import com.hunter.adapter.BaseViewHolder;

import java.util.Set;

public abstract class SimpleClickListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;

    private RecyclerView mRecyclerView;
    protected BaseQuickAdapter mAdapter;

    private boolean mIsPrePressed;
    private boolean mIsShowPress;
    private View mPressedView;

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mRecyclerView == null) {
            mRecyclerView = rv;
            mAdapter = (BaseQuickAdapter) mRecyclerView.getAdapter();
            mGestureDetector = new GestureDetectorCompat(mRecyclerView.getContext(),
                    new ItemTouchHelperGestureListener(mRecyclerView));
        }
        if (!mGestureDetector.onTouchEvent(e) && e.getActionMasked() == MotionEvent.ACTION_UP && mIsShowPress) {
            if (mPressedView != null) {
                mPressedView.setPressed(false);
                mPressedView = null;
            }
            mIsShowPress = false;
            mIsPrePressed = false;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView mRv;

        public ItemTouchHelperGestureListener(RecyclerView rv) {
            mRv = rv;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mIsPrePressed = true;
            mPressedView = mRv.findChildViewUnder(e.getX(), e.getY());
            super.onDown(e);
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if (mIsPrePressed && mPressedView != null) mIsShowPress = true;
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mIsPrePressed && mPressedView != null) {

                final View pressedView = mPressedView;
                BaseViewHolder vh = (BaseViewHolder) mRv.getChildViewHolder(pressedView);

                if (isHeaderOrFooterPosition(vh.getLayoutPosition())) return false;

                Set<Integer> childClickViewIds = vh.getItemChildClickViewIds();

                if (childClickViewIds != null && childClickViewIds.size() > 0) {
                    for (Integer childClickViewId1 : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId1);
                        if (inRangeOfView(childView, e) && childView.isEnabled()) {
                            setPressViewHotSpot(e, childView);
                            childView.setPressed(true);
                            onItemChildClick(mAdapter, childView,
                                    vh.getLayoutPosition() - mAdapter.getHeaderViewCount());
                            resetPressedView(childView);
                            return true;
                        } else {
                            childView.setPressed(false);
                        }
                    }
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        childView.setPressed(false);
                    }
                    onItemClick(mAdapter, pressedView, vh.getLayoutPosition() - mAdapter.getHeaderViewCount());
                } else {
                    setPressViewHotSpot(e, pressedView);
                    mPressedView.setPressed(true);
                    for (Integer childClickViewId : childClickViewIds) {
                        View childView = pressedView.findViewById(childClickViewId);
                        childView.setPressed(false);
                    }
                    onItemClick(mAdapter, pressedView, vh.getLayoutPosition() - mAdapter.getHeaderViewCount());
                }
                resetPressedView(pressedView);

            }
            return true;
        }

        private void resetPressedView(final View pressedView) {
            if (pressedView != null) {
                pressedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (pressedView != null) {
                            pressedView.setPressed(false);
                        }

                    }
                }, 100);
            }

            mIsPrePressed = false;
            mPressedView = null;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            boolean isChildLongClick = false;
            if (mIsPrePressed && mPressedView != null) {
                mPressedView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                BaseViewHolder vh = (BaseViewHolder) mRv.getChildViewHolder(mPressedView);
                if (!isHeaderOrFooterPosition(vh.getLayoutPosition())) {
                    Set<Integer> longClickViewIds = vh.getItemChildLongClickViewIds();
                    if (longClickViewIds != null && longClickViewIds.size() > 0) {
                        for (Integer longClickViewId : longClickViewIds) {
                            View childView = mPressedView.findViewById(longClickViewId);
                            if (inRangeOfView(childView, e) && childView.isEnabled()) {
                                setPressViewHotSpot(e, childView);
                                onItemChildLongClick(mAdapter, childView,
                                        vh.getLayoutPosition() - mAdapter.getHeaderViewCount());
                                childView.setPressed(true);
                                mIsShowPress = true;
                                isChildLongClick = true;
                                break;
                            }
                        }
                    }
                    if (!isChildLongClick) {
                        onItemLongClick(mAdapter, mPressedView, vh.getLayoutPosition() - mAdapter.getHeaderViewCount());
                        setPressViewHotSpot(e, mPressedView);
                        mPressedView.setPressed(true);
                        for (Integer longClickViewId : longClickViewIds) {
                            View childView = mPressedView.findViewById(longClickViewId);
                            childView.setPressed(false);
                        }
                        mIsShowPress = true;
                    }
                }
            }
        }
    }

    private void setPressViewHotSpot(final MotionEvent e, final View mPressedView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mPressedView != null && mPressedView.getBackground() != null) {
                mPressedView.getBackground().setHotspot(e.getRawX(), e.getY() - mPressedView.getY());
            }
        }
    }

    public abstract void onItemClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onItemLongClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onItemChildClick(BaseQuickAdapter adapter, View view, int position);

    public abstract void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position);

    public boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        if (view.getVisibility() != View.VISIBLE) return false;

        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        return !(ev.getRawX() < x ||
                ev.getRawX() > (x + view.getWidth()) ||
                ev.getRawY() < y ||
                ev.getRawY() > (y + view.getHeight()));
    }

    private boolean isHeaderOrFooterPosition(int position) {
        if (mAdapter == null) {
            if (mRecyclerView != null) mAdapter = (BaseQuickAdapter) mRecyclerView.getAdapter();
            else return false;
        }
        int type = mAdapter.getItemViewType(position);
        return type == BaseQuickAdapter.HEADER_VIEW ||
                type == BaseQuickAdapter.FOOTER_VIEW ||
                type == BaseQuickAdapter.EMPTY_VIEW ||
                type == BaseQuickAdapter.ERROR_VIEW ||
                type == BaseQuickAdapter.LOADING_VIEW ||
                type == BaseQuickAdapter.LOAD_MORE_FAIL_VIEW ||
                type == BaseQuickAdapter.LOAD_NO_MORE_VIEW;
    }

}


