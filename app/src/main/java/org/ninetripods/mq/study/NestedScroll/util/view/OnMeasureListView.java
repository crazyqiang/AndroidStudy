package org.ninetripods.mq.study.NestedScroll.util.view;

import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by mq on 2018/6/9 下午3:18
 * mqcoder90@gmail.com
 */

public class OnMeasureListView extends ListView implements NestedScrollingChild {
    private NestedScrollingChildHelper mChildHelper;

    public OnMeasureListView(Context context) {
        this(context, null);
    }

    public OnMeasureListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnMeasureListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mChildHelper = new NestedScrollingChildHelper(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        Log.e("TTT", "setNestedScrollingEnabled");
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.e("TTT", "isNestedScrollingEnabled");
        return mChildHelper.isNestedScrollingEnabled();
    }

    /**
     * @param axes ViewCompat.SCROLL_AXIS_HORIZONTAL
     *             ViewCompat.SCROLL_AXIS_VERTICAL
     * @return 如果有可以滑动的Parent返回true
     */
    @Override
    public boolean startNestedScroll(int axes) {
        Log.e("TTT", "startNestedScroll " + axes);
        //告诉Parent自己要滑动
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.e("TTT", "stopNestedScroll");
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.e("TTT", "hasNestedScrollingParent");
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        //Parent是否需要继续滑动你剩下的距离
        Log.e("TTT", "dispatchNestedScroll");
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        //询问Parent是否需要滑动
        Log.e("TTT", "dispatchNestedPreScroll");
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.e("TTT", "dispatchNestedFling");
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.e("TTT", "dispatchNestedPreFling");
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }
}
