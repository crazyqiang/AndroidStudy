package org.ninetripods.mq.study.path;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by MQ on 2017/2/10.
 */

public class PathMeasureView extends View {
    private Paint mPaint;
    private Path mPath;
    private PathMeasure pathMeasure;
    private int mCenterX, mCenterY;
    private RectF mRectF;
    private int halfWidth = 200;
    private float[] pos = new float[2];
    private float[] tan = new float[2];

    public PathMeasureView(Context context) {
        this(context, null);
    }

    public PathMeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化Paint
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.BLACK);
        //初始化Path
        mPath = new Path();
        //初始化PathMeasure
        pathMeasure = new PathMeasure();
        //初始化RectF
        mRectF = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
        mRectF.set(mCenterX - halfWidth, mCenterY - halfWidth, mCenterX + halfWidth, mCenterY + halfWidth);
        pos[0] = mCenterX - halfWidth;
        pos[1] = mCenterY - halfWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制矩形
        mPath.reset();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath.addRect(mRectF, Path.Direction.CW);
        pathMeasure.setPath(mPath, false);
        canvas.drawPath(mPath, mPaint);
        //绘制圆
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(pos[0], pos[1], 12, mPaint);
    }

    public void startMove() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        animator.setDuration(3 * 1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float distance = (float) animation.getAnimatedValue();
                //tan[0]是邻边 tan[1]是对边
                pathMeasure.getPosTan(distance, pos, tan);
                postInvalidate();
            }
        });
        animator.start();
    }
}
