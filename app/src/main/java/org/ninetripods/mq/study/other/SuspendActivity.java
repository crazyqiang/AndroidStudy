package org.ninetripods.mq.study.other;


import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.TextView;

import org.ninetripods.mq.study.BaseActivity;
import org.ninetripods.mq.study.R;

public class SuspendActivity extends BaseActivity {
//    private SuspendTextView tv_word;

    private VelocityTracker mVelocityTracker;
    private int mPointerId;
    private int mMaxVelocity;
    private TextView tv_show;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_suspend);
    }

    @Override
    public void initViews() {
//        tv_word = (SuspendTextView) findViewById(R.id.tv_word);
        tv_show = (TextView) findViewById(R.id.tv_show);
        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
    }

    int posCount;

    public void btn_start(View view) {
        posCount++;
//        tv_word.startScrollTo(-50 * posCount, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        final VelocityTracker verTracker = mVelocityTracker;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = event.getPointerId(0);
                break;

            case MotionEvent.ACTION_MOVE:
                //求伪瞬时速度
                verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = verTracker.getXVelocity(mPointerId);
                final float velocityY = verTracker.getYVelocity(mPointerId);
                recodeInfo(velocityX, velocityY);
                break;

            case MotionEvent.ACTION_UP:
                releaseVelocityTracker();
                break;

            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    //释放VelocityTracker

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
//            mVelocityTracker.clear();
//            mVelocityTracker.recycle();
//            mVelocityTracker = null;
        }
    }

    private static final String sFormatStr = "velocityX=%f\nvelocityY=%f";

    /**
     * 记录当前速度
     *
     * @param velocityX x轴速度
     * @param velocityY y轴速度
     */
    private void recodeInfo(final float velocityX, final float velocityY) {
        final String info = String.format(sFormatStr, velocityX, velocityY);
        tv_show.setText(info);
    }
}
