package org.ninetripods.mq.study.recycle.contacts_recycle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.ninetripods.mq.study.util.ColorUtil;


/**
 * Created by MQ on 2017/5/18.
 */

public class IndexBar extends ViewGroup {
    private int mHeight, mWidth;
    private Context mContext;
    private Paint mPaint;
    private float centerY;
    private String tag = "";
    private boolean isShowTag;
    private int position;
    private final float circleRadius = 100;

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //MeasureSpec封装了父View传给子View的布局要求
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (wMode) {
            case MeasureSpec.EXACTLY:
                mWidth = wSize;
                break;
            case MeasureSpec.AT_MOST:
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
                mHeight = hSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private int childWidth;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childNum = getChildCount();
        if (childNum <= 0) return;
        //得到SideBar
        View childView = getChildAt(0);
        childWidth = childView.getMeasuredWidth();
        //把SideBar排列到最右侧
        childView.layout((mWidth - childWidth), 0, mWidth, mHeight);
    }

    /**
     * @param centerY  要绘制的圆的Y坐标
     * @param tag      要绘制的字母Tag
     * @param position 字母Tag所在位置
     */
    public void setDrawData(float centerY, String tag, int position) {
        this.position = position;
        this.centerY = centerY;
        this.tag = tag;
        isShowTag = true;
        invalidate();
    }

    /**
     * 通过标志位来控制是否来显示圆
     *
     * @param isShowTag 是否显示圆
     */
    public void setTagStatus(boolean isShowTag) {
        this.isShowTag = isShowTag;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowTag) {
            //根据位置来不断变换Paint的颜色
            ColorUtil.setPaintColor(mPaint, position);
            //绘制圆和文字
            canvas.drawCircle((mWidth - childWidth) / 2, centerY, circleRadius, mPaint);
            mPaint.setColor(Color.WHITE);
            mPaint.setTextSize(80);
            canvas.drawText(tag, (mWidth - childWidth - mPaint.measureText(tag)) / 2, centerY - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        }
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }
}
