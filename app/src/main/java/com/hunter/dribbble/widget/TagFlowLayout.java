package com.hunter.dribbble.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hunter.dribbble.R;

import java.util.ArrayList;
import java.util.List;

public class TagFlowLayout extends ViewGroup {

    private List<String> mTags;

    private List<Integer> mLineMaxHeightList;

    /* 当前ViewGroup的总高度 */
    private int mTotalHeight  = 0;
    /* 所有行中的最大宽度 */
    private int mMaxLineWidth = 0;

    /* 当前行的宽高 */
    private int mCurrentLineHeight;
    private int mCurrentLineWidth;

    /* 每个childView所占用的宽高 */
    private int mChildViewWidthSpace;
    private int mChildViewHeightSpace;

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTags = new ArrayList<>();
        mLineMaxHeightList = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        /* 当前ViewGroup的总高度 */
        int totalHeight = 0;
        /* 所有行中的最大宽度 */
        int maxLineWidth = 0;

        /* 当前行的宽高 */
        int currentLineHeight = 0;
        int currentLineWidth = 0;

        /* 每个childView所占用的宽高 */
        int childViewWidthSpace;
        int childViewHeightSpace;

        int count = getChildCount();
        MarginLayoutParams layoutParams;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                layoutParams = (MarginLayoutParams) child.getLayoutParams();
                childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                /* 超出总的规定宽度，则需要另起一行 */
                if (currentLineWidth + childViewWidthSpace > widthSize) {
                    totalHeight += currentLineHeight;
                    /* 如果行的最长宽度发生了变化，更新保存的最长宽度 */
                    if (maxLineWidth < currentLineWidth) maxLineWidth = currentLineWidth;
                    /* 另起一行后，需要重置当前行宽高 */
                    currentLineWidth = childViewWidthSpace;
                    currentLineHeight = childViewHeightSpace;
                } else {
                    /* 当前行可以继续添加子元素 */
                    currentLineWidth += childViewWidthSpace;
                    if (currentLineHeight < childViewHeightSpace) currentLineHeight = childViewHeightSpace;
                }
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : maxLineWidth,
                             heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int currentLine = 1;
        //存放每一行的最大高度  

        //每个childView所占用的宽度  
        int childViewWidthSpace;
        //每个childView所占用的高度  
        int childViewHeightSpace;

        //当前行的最大高度  
        int lineMaxHeight = 0;
        //当前行的总宽度  
        int currentLineWidth = 0;

        int count = getChildCount();
        MarginLayoutParams layoutParams;

        for (int i = 0; i < count; i++) {
            int cl, ct = 0, cr, cb;
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {//只有当这个View能够显示的时候才去测量

                layoutParams = (MarginLayoutParams) child.getLayoutParams();
                childViewWidthSpace = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
                childViewHeightSpace = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

                if (currentLineWidth + childViewWidthSpace > getWidth()) {//表示如果当前行再加上现在这个子View，就会超出总的规定宽度，需要另起一行
                    mLineMaxHeightList.add(lineMaxHeight);//此时先将这一行的最大高度加入到集合中
                    //新的一行，重置一些参数  
                    currentLine++;
                    currentLineWidth = childViewWidthSpace;
                    lineMaxHeight = childViewHeightSpace;

                    cl = layoutParams.leftMargin;
                    if (currentLine > 1) {
                        for (int j = 0; j < currentLine - 1; j++) {
                            ct += mLineMaxHeightList.get(j);
                        }
                        ct += layoutParams.topMargin;
                    } else {
                        ct = layoutParams.topMargin;
                    }
                } else {//表示当前行可以继续添加子元素
                    cl = currentLineWidth + layoutParams.leftMargin;
                    if (currentLine > 1) {
                        for (int j = 0; j < currentLine - 1; j++) {
                            ct += mLineMaxHeightList.get(j);
                        }
                        ct += layoutParams.topMargin;
                    } else {
                        ct = layoutParams.topMargin;
                    }
                    currentLineWidth += childViewWidthSpace;
                    if (lineMaxHeight < childViewHeightSpace) {
                        lineMaxHeight = childViewHeightSpace;
                    }
                }

                cr = cl + child.getMeasuredWidth();
                cb = ct + child.getMeasuredHeight();

                child.layout(cl, ct, cr, cb);

            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setTags(List<String> tags) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (tags != null && tags.size() != 0) {
            mTags.clear();
            mTags.addAll(tags);
            for (int i = 0; i < mTags.size(); i++) {
                TextView tvTagName = (TextView) inflater.inflate(R.layout.layout_tag_flow, this, false);
                final String content = mTags.get(i);
                tvTagName.setText(content);
                tvTagName.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) mListener.onClick(v, content);
                    }
                });
                addView(tvTagName);
            }
            requestLayout();
        }
    }

    private OnTagItemClickListener mListener;

    public interface OnTagItemClickListener {

        void onClick(View v, String content);
    }

    public void setOnTagItemClickListener(OnTagItemClickListener l) {
        mListener = l;
    }

}  