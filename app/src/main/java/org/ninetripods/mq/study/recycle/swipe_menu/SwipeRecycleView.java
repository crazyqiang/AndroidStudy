package org.ninetripods.mq.study.recycle.swipe_menu;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * Created by MQ on 2017/6/9.
 */

public class SwipeRecycleView extends RecyclerView {
    private SwipeMenuLayout mLastMenuLayout;
    private int mLastTouchPosition;
    protected int mScaleTouchSlop;

    public SwipeRecycleView(Context context) {
        this(context, null);
    }

    public SwipeRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private int mLastX, mLastY;
    private int mDownX, mDownY;

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean isIntercepted = super.onInterceptTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getX();
                mLastY = (int) event.getY();
                mDownX = (int) event.getX();
                mDownY = (int) event.getY();
                isIntercepted = false;
                //根据MotionEvent的X Y值得到子View
                View view = findChildViewUnder(mLastX, mLastY);
                if (view == null) return false;
                //点击的子View所在的位置
                final int touchPos = getChildAdapterPosition(view);
                if (touchPos != mLastTouchPosition && mLastMenuLayout != null
                        && mLastMenuLayout.currentState != SwipeMenuLayout.STATE_CLOSED) {
                    if (mLastMenuLayout.isMenuOpen()) {
                        //如果之前的菜单栏处于打开状态，则关闭它
                        mLastMenuLayout.smoothToCloseMenu();
                    }
                    isIntercepted = true;
                } else {
                    //根据点击位置获得相应的子View
                    ViewHolder holder = findViewHolderForAdapterPosition(touchPos);
                    if (holder != null) {
                        View childView = holder.itemView;
                        if (childView != null && childView instanceof SwipeMenuLayout) {
                            mLastMenuLayout = (SwipeMenuLayout) childView;
                            mLastTouchPosition = touchPos;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int dx = (int) (mDownX - event.getX());
                int dy = (int) (mDownY - event.getY());
                if (Math.abs(dx) > mScaleTouchSlop && Math.abs(dx) > Math.abs(dy)
                        || (mLastMenuLayout != null && mLastMenuLayout.currentState != SwipeMenuLayout.STATE_CLOSED)) {
                    //如果X轴偏移量大于Y轴偏移量 或者上一个打开的菜单还没有关闭 则禁止RecycleView滑动 RecycleView不去拦截事件
                    return false;
                }
                break;
        }
        return isIntercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //若某个Item的菜单还没有关闭，则RecycleView不能滑动
                if (!mLastMenuLayout.isMenuClosed()) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                if (mLastMenuLayout != null && mLastMenuLayout.isMenuOpen()) {
                    mLastMenuLayout.smoothToCloseMenu();
                }
                break;
        }
        return super.onTouchEvent(e);
    }

}
