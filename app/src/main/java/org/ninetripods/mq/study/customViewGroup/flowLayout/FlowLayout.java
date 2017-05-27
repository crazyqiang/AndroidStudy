package org.ninetripods.mq.study.customViewGroup.flowLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 流式布局
 * Created by MQ on 2017/5/24.
 */

public class FlowLayout extends ViewGroup {

    private int mWidth, mHeight;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int curWidthLength = 0;
        int childWidth, childHeight;
        int maxWidthLength = 0, maxHeightLength = 0, totalHeight = 0;
        MarginLayoutParams marginParams;
        //获得子View的数量
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //如果gone，不测量了
            if (View.GONE == childView.getVisibility()) {
                continue;
            }
            marginParams = (MarginLayoutParams) childView.getLayoutParams();
            //触发测量子View
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //每个ChildView的总长度(包括左右Margin)
            childWidth = childView.getMeasuredWidth() + marginParams.leftMargin + marginParams.rightMargin;
            //每个ChildView的总高度(包括上下Margin)
            childHeight = childView.getMeasuredHeight() + marginParams.topMargin + marginParams.bottomMargin;

            //如果子View再向右排一个就超过父View的最大宽度，那么换行
            if (curWidthLength + childWidth > wSize - getPaddingLeft() - getPaddingRight()) {
                maxWidthLength = Math.max(maxWidthLength, curWidthLength);
                totalHeight += maxHeightLength;
                maxHeightLength = childHeight;
                curWidthLength = childWidth;
            } else {
                curWidthLength += childWidth;
                maxHeightLength = Math.max(maxHeightLength, childHeight);
            }
            if (i == childCount - 1) {
                //最后一条数据
                maxWidthLength = Math.max(maxWidthLength, curWidthLength);
                totalHeight += maxHeightLength;
            }
        }
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                mWidth = maxWidthLength + getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                mHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                mHeight = totalHeight + getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        int curWidthLength = 0;
        int childWidth, childHeight;
        int maxHeightLength = 0, totalHeight = 0;
        MarginLayoutParams marginParams;

        int mLeft, mTop, mRight, mBottom;

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (View.GONE == childView.getVisibility()) {
                continue;
            }

            marginParams = (MarginLayoutParams) childView.getLayoutParams();
            //每个ChildView的总长度(包括左右Margin)
            childWidth = childView.getMeasuredWidth() + marginParams.leftMargin + marginParams.rightMargin;
            //每个ChildView的总高度(包括上下Margin)
            childHeight = childView.getMeasuredHeight() + marginParams.topMargin + marginParams.bottomMargin;


            //如果子View再向右排一个就超过父View的最大宽度，那么换行
            if (curWidthLength + childWidth > getWidth() - getPaddingLeft() - getPaddingRight()) {

                curWidthLength = childWidth;
                totalHeight += maxHeightLength;
                maxHeightLength = 0;

                mLeft = getPaddingLeft() + marginParams.leftMargin;
                mTop = totalHeight + getPaddingTop() + marginParams.topMargin;
                mRight = mLeft + childView.getMeasuredWidth();
                mBottom = mTop + childView.getMeasuredHeight();

            } else {
                mLeft = curWidthLength + getPaddingLeft() + marginParams.leftMargin;
                mTop = totalHeight + getPaddingTop() + marginParams.topMargin;
                mRight = mLeft + childView.getMeasuredWidth();
                mBottom = mTop + childView.getMeasuredHeight();

                curWidthLength += childWidth;
                maxHeightLength = Math.max(maxHeightLength, childHeight);
            }
            childView.layout(mLeft, mTop, mRight, mBottom);
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

}
