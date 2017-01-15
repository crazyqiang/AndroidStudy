package org.ninetripods.mq.study.CustomViewGroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.ninetripods.mq.study.R;

/**
 * Created by MQ on 2016/12/17.
 */

public class ColorCircleView extends View {
    private Paint mPaint;
    private int cirlceColor;//圆环颜色
    private int stokeWidth;//圆环宽度

    public ColorCircleView(Context context) {
        this(context, null);
    }

    public ColorCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ColorCircleView);
        cirlceColor = ta.getColor(R.styleable.ColorCircleView_circle_color, Color.RED);
        stokeWidth = ta.getInt(R.styleable.ColorCircleView_stroke_width, 10);
        ta.recycle();
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(cirlceColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(stokeWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = (Math.min(getMeasuredWidth(), getMeasuredHeight()) - mPaint.getStrokeWidth()) / 2;
//        Log.e("TTT", "getMeasuredWidth() is " + getMeasuredWidth() + ",getMeasuredHeight() is " + getMeasuredHeight());
        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, radius, mPaint);
    }
}
