package org.ninetripods.mq.study.other;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import org.ninetripods.mq.study.util.MyLog;

/**
 * Created by MQ on 2017/6/2.
 */

public class SuspendTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    //    private OverScroller mScroller;
    private Scroller mScroller;

    public SuspendTextView(Context context) {
        this(context, null);
    }

    public SuspendTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuspendTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mScroller = new Scroller(context, new BounceInterpolator(context, attrs));
    }

    public void startScrollTo(int destX, int destY) {
//        mScroller.getCurrX();
//        mScroller.getCurrVelocity();
//        mScroller.getCurrY();
//        mScroller.getFinalX();
//        mScroller.getFinalY();
//        mScroller.getStartX();
//        mScroller.getStartY();
//
//
        mScroller.abortAnimation();
        mScroller.forceFinished(true);
//        mScroller.fling();
//        mScroller.isFinished();
//        mScroller.isOverScrolled();
//        mScroller.forceFinished(true);
//        mScroller.springBack();
//        mScroller.setFriction();
        int scrollX = getScrollX();
        MyLog.e("TTT", "scrollX is " + scrollX);
        int deltaX = destX - scrollX;
//        startScroll(int startX, int startY, int dx, int dy)
        mScroller.startScroll(0, 0, 200, 0, 5000);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            // Get current x and y positions
            int currX = mScroller.getCurrX();
            int currY = mScroller.getCurrY();
            setX(currX);
            setY(currY);
//            scrollTo(currX, currY);
            invalidate();
        }
    }

    float mMoveX, mMoveY;
    float mLastX, mLastY;
    float mDownX, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMoveX = event.getRawX();
        mMoveY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float translateX = getTranslationX() + mMoveX - mLastX;
                float translateY = getTranslationY() + mMoveY - mLastY;
                setTranslationX(translateX);
                setTranslationY(translateY);
                break;
            case MotionEvent.ACTION_UP:
//                scrollTo((int) (mMoveX - mDownX), (int) (mMoveY - mDownY));
                MyLog.e("TTT", "getY() is " + getY());
                MyLog.e("TTT", "getTranslationY() is " + getTranslationY());
                MyLog.e("TTT", "getCurrY() is " + mScroller.getCurrY());
                mScroller.startScroll((int) getX(), (int) getY(),
                        -(int) (mMoveX - mDownX), -(int) (mMoveY - mDownY), 500);
                invalidate();
                break;

        }
        mLastX = mMoveX;
        mLastY = mMoveY;
        return true;

    }
}
