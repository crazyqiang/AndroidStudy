package org.ninetripods.mq.study.customView.alipayView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.ninetripods.mq.study.R;

import static org.ninetripods.mq.study.customView.alipayView.AlipayView.State.FINISH;
import static org.ninetripods.mq.study.customView.alipayView.AlipayView.State.IDLE;

/**
 * Created by MQ on 2017/1/20.
 */

public class AlipayView extends View {
    private Paint mPaint;
    private RectF mRectF;
    private int mCenterX, mCenterY;
    private State state = IDLE;
    private int firstProgress;//第一个圆弧进度
    private int secondProgress;//第二个圆弧进度
    private Path mPath;
    private boolean isEndPay;//是否支付完成
    private Line firstLine;//对勾第一条线
    private Line secondLine;//对勾第二条线
    private float lineProgress;//打对勾的进度
    private int sweepLength;//正在支付状态的圆弧长度
    private float xDis3to1, xDis2to1;//xDis3to1为对勾的横向长度 xDis2to1为第二个坐标到第一个坐标的横向长度
    private float x1, y1, x2, y2, x3, y3;//构成对勾的3个坐标(x1,y1)(x2,y2)(x3,y3)

    private int radius = 150;//旋转圆的半径(修改此值可以改变圆的大小)
    private float increaseDis = 10f;//对勾增长速率(修改此值可以改变打对勾的速率)
    private float circleIncRate = 10f;//圆圈增长速率(修改此值可以改变画圆的速率)
    private int sweepMaxAngle = 200;//正在支付状态的最大圆弧长度(修改此值可以改变最大圆弧的长度)

    public AlipayView(Context context) {
        this(context, null);
    }

    public AlipayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlipayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10f);
        mPaint.setColor(getResources().getColor(R.color.holo_blue_light));
        //绘制范围
        mRectF = new RectF();
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2;
        mCenterY = h / 2;
        x1 = mCenterX - radius / 3 * 2;
        y1 = mCenterY + radius / 8;
        x2 = mCenterX - radius / 5;
        y2 = mCenterY + radius / 3 * 2;
        x3 = mCenterX + radius / 4 * 3;
        y3 = mCenterY - radius / 4;
        firstLine = new Line(new PointF(x1, y1), new PointF(x2, y2));
        secondLine = new Line(new PointF(x2, y2), new PointF(x3, y3));
        xDis3to1 = x3 - x1;
        xDis2to1 = x2 - x1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRectF.set(mCenterX - radius, mCenterY - radius, mCenterX + radius, mCenterY + radius);
        switch (state) {
            case IDLE:
                canvas.drawArc(mRectF, -90f, 360f, false, mPaint);
                mPath.moveTo(firstLine.startPoint.x, firstLine.startPoint.y);
                mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                mPath.lineTo(secondLine.endPoint.x, secondLine.endPoint.y);
                canvas.drawPath(mPath, mPaint);
                break;
            case PROGRESS:
                //支付中
                canvas.drawArc(mRectF, -90f + firstProgress, sweepLength, false, mPaint);
                secondProgress += circleIncRate;
                if (secondProgress < sweepMaxAngle) {
                    firstProgress = 0;
                    sweepLength += circleIncRate;
                } else if (secondProgress >= sweepMaxAngle && secondProgress <= 360) {
                    firstProgress = secondProgress - sweepMaxAngle;
                    sweepLength = sweepMaxAngle;
                } else if (secondProgress > 360) {
                    if (sweepLength > 0) {
                        firstProgress += circleIncRate;
                        sweepLength -= circleIncRate;
                    } else {
//                        canvas.drawArc(mRectF, -89f, 1f, false, mPaint);
                        reset();
                        if (isEndPay) {
                            state = FINISH;
                        }
                        postInvalidateDelayed(200);
                        break;
                    }
                }
                invalidate();
                break;
            case FINISH:
                //支付完成
                mPath.reset();
                if (secondProgress < 360) {
                    float sweepAngle = secondProgress;
                    canvas.drawArc(mRectF, -90f, sweepAngle, false, mPaint);
                    secondProgress += circleIncRate;
                    invalidate();
                } else {
                    canvas.drawArc(mRectF, -90f, 360f, false, mPaint);
                    mPath.moveTo(firstLine.startPoint.x, firstLine.startPoint.y);
                    float lineX = x1 + lineProgress;
                    if (lineProgress < xDis2to1) {
                        //绘制第一条线
                        mPath.lineTo(lineX, firstLine.getY(lineX));
                        invalidate();
                    } else if (lineProgress >= xDis2to1 && lineProgress < xDis3to1) {
                        //绘制第二条线
                        mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                        mPath.lineTo(lineX, secondLine.getY(lineX));
                        invalidate();
                    } else {
                        //全部画完
                        mPath.lineTo(firstLine.endPoint.x, firstLine.endPoint.y);
                        mPath.lineTo(secondLine.endPoint.x, secondLine.endPoint.y);
                    }
                    lineProgress += increaseDis;
                    canvas.drawPath(mPath, mPaint);
                }
                break;
        }
    }

    private void reset() {
        firstProgress = 0;
        secondProgress = 0;
        sweepLength = 0;
        lineProgress = 0;
    }

    enum State {
        IDLE,
        PROGRESS,
        FINISH
    }

    public void setState(State state) {
        this.state = state;
        invalidate();
    }

    public void setOverPay(boolean endPay) {
        isEndPay = endPay;
    }

    class Line {
        PointF startPoint;
        PointF endPoint;

        float k;//比例系数
        float b;//常数

        Line(PointF startPoint, PointF endPoint) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;

            k = (endPoint.y - startPoint.y) / (endPoint.x - startPoint.x);
            b = startPoint.y - k * startPoint.x;
        }

        float getY(float x) {
            return k * x + b;
        }
    }
}
