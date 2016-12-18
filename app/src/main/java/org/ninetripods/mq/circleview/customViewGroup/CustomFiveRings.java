package org.ninetripods.mq.circleview.customViewGroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import org.ninetripods.mq.circleview.DpUtil;

/**
 * Created by MQ on 2016/12/16.
 */

public class CustomFiveRings extends ViewGroup {
    private Context mContext;
    private TextPaint mPaint;
    private int startX;//圆环起始X轴
    private int startY;//圆环起始Y轴
    private int mWidth;//ViewGroup的宽
    private int mHeight;//ViewGroup的高

    public CustomFiveRings(Context context) {
        this(context, null);
    }

    public CustomFiveRings(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFiveRings(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        mContext = context;
        mPaint = new TextPaint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(50);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //触发所有子View的onMeasure函数去测量宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传递给子View的布局要求
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
                //这里应该先计算所有子view的宽度，暂时先写死
                mWidth = wSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        switch (hMode) {
            case MeasureSpec.EXACTLY:
                mHeight = hSize;
                break;
            case MeasureSpec.AT_MOST:
                //这里应该先计算所有子view的高度，暂时先写死
                mHeight = hSize;
                // mHeight = getCircleHeight() / 2 * 3;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        startX = startY = 0;
        int gap = 10;//同一行圆圈之间的间隔
        int screenWidth = DpUtil.getScreenSizeWidth(mContext);
        int firstTotalWidth = 0;//第一行子View的总宽度
        int secondTotalWidth = 0;//第二行子View的总宽度
        for (int i = 0; i < childNum; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            if (i <= 2) {
                //前三个总宽度
                firstTotalWidth += childWidth;
            } else {
                //后两个总宽度
                secondTotalWidth += childWidth;
            }
        }
        int leftFMargin = (screenWidth - firstTotalWidth - gap * 2) / 2;
        int leftSMargin = (screenWidth - secondTotalWidth - gap) / 2;
        for (int i = 0; i < childNum; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            if (i <= 2) {
                //排列前三个圆圈
                if (i == 0) {
                    childView.layout(leftFMargin + startX, startY, leftFMargin + startX + childWidth, startY + childHeight);
                    startX += childWidth;
                } else {
                    childView.layout(leftFMargin + startX + gap, startY, leftFMargin + startX + gap + childWidth, startY + childHeight);
                    startX += (childWidth + gap);
                }
                if (i == 2) {
                    startX = 0;
                    startY += childHeight / 2;
                }
            } else {
                //排列后两个圆圈
                if (i == 3) {
                    childView.layout(leftSMargin + startX, startY, leftSMargin + startX + childWidth, startY + childHeight);
                    startX += childWidth;
                } else {
                    childView.layout(leftSMargin + startX + gap, startY, leftSMargin + startX + gap + childWidth, startY + childHeight);
                    startX += (childWidth + gap);
                }
            }

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int screenWidth = DpUtil.getScreenSizeWidth(mContext);
        String upStr = "同一个世界，同一个梦想";
        String downStr = "One World,One Dream";
        canvas.drawText(upStr, (screenWidth - mPaint.measureText(upStr)) / 2, getCircleHeight() / 2 * 3 + 60, mPaint);
        canvas.drawText(downStr, (screenWidth - mPaint.measureText(downStr)) / 2, getCircleHeight() / 2 * 3 + 120, mPaint);
    }

    /**
     * 获得圆环高度
     *
     * @return 圆环高度
     */
    private int getCircleHeight() {
        //5个圆环大小是一样的，这里就直接取第一个了
        View childView = getChildAt(0);
        return childView.getMeasuredHeight();
    }
}
