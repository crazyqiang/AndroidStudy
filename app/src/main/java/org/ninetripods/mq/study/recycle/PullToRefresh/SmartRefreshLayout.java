package org.ninetripods.mq.study.recycle.PullToRefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import org.ninetripods.mq.study.R;
import org.ninetripods.mq.study.util.DpUtil;


public class SmartRefreshLayout extends ViewGroup {
    // 事件监听接口
    private onRefreshListener mListener;

    public void setOnRefreshListener(onRefreshListener listener) {
        mListener = listener;
    }

    public void setEnabledPullUp(boolean enabledPullUp) {
        this.mEnablePullUp = enabledPullUp;
        if (!mEnablePullUp) {
            if (mLayoutFooter != null && mLayoutFooter.isShown())
                mLayoutFooter.setVisibility(View.GONE);
        }
    }

    public interface onRefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    // 布局填充器对象
    private LayoutInflater mInflater;
    // 头视图(即上拉刷新时显示的部分)
    private RelativeLayout mLayoutHeader;
    private TextView tvPullDown;
    private GhostLoadingView glvPullDown;

    // 尾视图(即下拉加载时显示的部分)
    private RelativeLayout mLayoutFooter;
    private TextView tvPullUp;
    private EatBeanLoadingView elvPullUp;

    // 最后一个content-child-view的index
    private int lastChildIndex;

    // ViewGroup的内容高度(不包括header与footer的高度)
    private int mLayoutContentHeight;
    // 用于平滑滑动的Scroller对象
    private Scroller mLayoutScroller;
    // Scroller的滑动速度
    private static final int SCROLL_SPEED = 650;
    // 当滚动到内容最底部时Y轴所需要滑动的举例
    private int mReachBottomScroll;
    // 最小有效滑动距离(滑动超过该距离才视作一次有效的滑动刷新/加载操作)
    private static int mEffectiveScroll;

    // 拉动部分背景(color|drawable)
//    private Drawable mPullBgDrawable = null;
    // 是否允许下拉刷新
    private boolean mEnablePullDown;
    // 是否允许上拉加载
    private boolean mEnablePullUp;

    public SmartRefreshLayout(Context context) {
        super(context);
    }

    public SmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray array = context.obtainStyledAttributes(attrs,
//                R.styleable.SmartRefreshLayout);
        try {
            mEnablePullDown = true;
            mEnablePullUp = true;
//            mPullBgDrawable = array.getDrawable(R.styleable.SmartRefreshLayout_pullBackground);
        } finally {
//            array.recycle();
        }

        // 实例化布局填充器
        mInflater = LayoutInflater.from(context);
        // 实例化Scroller
        mLayoutScroller = new Scroller(context);
        // 计算最小有效滑动距离
        mEffectiveScroll = (int) DpUtil.dp2px(context,60);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        lastChildIndex = getChildCount() - 1;

        // 添加上拉刷新部分
        if (mEnablePullDown)
            addLayoutHeader();

        // 添加下拉加载部分
        if (mEnablePullUp)
            addLayoutFooter();
    }

    /**
     * 添加上拉刷新布局作为header
     */
    private void addLayoutHeader() {
        // 通过LayoutInflater获取从布局文件中获取header的view对象
        mLayoutHeader = (RelativeLayout) mInflater.inflate(R.layout.srl_layout_header, null);
//        if (mPullBgDrawable != null)
//            mLayoutHeader.setBackgroundDrawable(mPullBgDrawable);

        // 获取上拉刷新的文字描述
        tvPullDown = (TextView) mLayoutHeader.findViewById(R.id.srl_tv_pull_down);
        // 获取上拉刷新的loading-view
        glvPullDown = (GhostLoadingView) mLayoutHeader.findViewById(R.id.srl_glv_pull_down);
        // 设置布局参数(宽度为MATCH_PARENT,高度为MATCH_PARENT)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        // 将Header添加进Layout当中
        addView(mLayoutHeader, params);
    }

    /**
     * 添加下拉加载布局作为footer
     */
    private void addLayoutFooter() {
        // 通过LayoutInflater获取从布局文件中获取footer的view对象
        mLayoutFooter = (RelativeLayout) mInflater.inflate(R.layout.srl_layout_footer, null);
//        if (mPullBgDrawable != null)
//            mLayoutFooter.setBackgroundDrawable(mPullBgDrawable);

        // 上拉提示
        tvPullUp = (TextView) mLayoutFooter.findViewById(R.id.srl_tv_pull_up);
        // 上拉loading动画
        elvPullUp = (EatBeanLoadingView) mLayoutFooter.findViewById(R.id.srl_elv_pull_up);
        // 设置布局参数(宽度为MATCH_PARENT,高度为MATCH_PARENT)
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        // 将footer添加进Layout当中
        addView(mLayoutFooter, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 遍历进行子视图的测量工作
        for (int i = 0; i < getChildCount(); i++) {
            // 通知子视图进行测量
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 重置(避免重复累加)
        mLayoutContentHeight = 0;

        // 遍历进行子视图的置位工作
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            if (child == mLayoutHeader) { // 头视图隐藏在ViewGroup的顶端
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else if (child == mLayoutFooter) { // 尾视图隐藏在ViewGroup所有内容视图之后
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
            } else { // 内容视图根据定义(插入)顺序,按由上到下的顺序在垂直方向进行排列
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                if (index <= lastChildIndex) {
                    if (child instanceof ScrollView) {
                        mLayoutContentHeight += getMeasuredHeight();
                        continue;
                    }
                    mLayoutContentHeight += child.getMeasuredHeight();
                }
            }
        }
        // 计算到达内容最底部时ViewGroup的滑动距离
        mReachBottomScroll = mLayoutContentHeight - getMeasuredHeight();
    }

    // Layout状态
    private int status = NORMAL;
    // 普通状态
    private static final int NORMAL = 0;
    // 意图刷新
    private static final int TRY_REFRESH = 1;
    // 刷新状态
    private static final int REFRESH = 2;
    // 意图加载
    private static final int TRY_LOAD_MORE = 3;
    // 加载状态
    private static final int LOAD_MORE = 4;

    // 用于计算滑动距离的Y坐标中介
    private int mLastYMoved;
    // 用于判断是否拦截触摸事件的Y坐标中介
    private int mLastYIntercept;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        // 记录此次触摸事件的y坐标
        int y = (int) event.getY();
        // 判断触摸事件类型
        switch (event.getAction()) {
            // Down事件
            case MotionEvent.ACTION_DOWN: {
                // 记录下本次系列触摸事件的起始点Y坐标
                mLastYMoved = y;
                // 不拦截ACTION_DOWN，因为当ACTION_DOWN被拦截，后续所有触摸事件都会被拦截
                intercept = false;
                break;
            }
            // Move事件
            case MotionEvent.ACTION_MOVE: {
                if (y > mLastYIntercept) { // 下滑操作
                    // 获取最顶部的子视图
                    View child = getChildAt(0);
                    if (child instanceof AdapterView) {
                        intercept = avPullDownIntercept(child);
                    } else if (child instanceof ScrollView) {
                        intercept = svPullDownIntercept(child);
                    } else if (child instanceof RecyclerView) {
                        intercept = rvPullDownIntercept(child);
                    }
                } else if (y < mLastYIntercept) { // 上拉操作
                    // 获取最底部的子视图
                    View child = getChildAt(lastChildIndex);
                    if (child instanceof AdapterView) {
                        intercept = avPullUpIntercept(child);
                    } else if (child instanceof ScrollView) {
                        intercept = svPullUpIntercept(child);
                    } else if (child instanceof RecyclerView) {
                        intercept = rvPullUpIntercept(child);
                    }
                } else {
                    intercept = false;
                }
                break;
            }
            // Up事件
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }

        mLastYIntercept = y;
        return intercept;
    }

    private boolean avPullDownIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterChild = (AdapterView) child;
        // 判断AbsListView是否已经到达内容最顶部
        if (adapterChild.getFirstVisiblePosition() != 0
                || adapterChild.getChildAt(0).getTop() != 0) {
            // 如果没有达到最顶端，则仍然将事件下放
            intercept = false;
        }
        return intercept;
    }

    private boolean avPullUpIntercept(View child) {
        boolean intercept = false;
        AdapterView adapterChild = (AdapterView) child;

        // 判断AbsListView是否已经到达内容最底部
        if (adapterChild.getLastVisiblePosition() == adapterChild.getCount() - 1
                && (adapterChild.getChildAt(adapterChild.getChildCount() - 1).getBottom() == getMeasuredHeight())) {
            // 如果到达底部，则拦截事件
            intercept = true;
        }
        return intercept;
    }

    private boolean svPullDownIntercept(View child) {
        boolean intercept = false;
        if (child.getScrollY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    private boolean svPullUpIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        View scrollChild = scrollView.getChildAt(0);

        if (scrollView.getScrollY() >= (scrollChild.getHeight() - scrollView.getHeight())) {
            intercept = true;
        }
        return intercept;
    }

    private boolean rvPullDownIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerChild = (RecyclerView) child;
        if (recyclerChild.computeVerticalScrollOffset()<=0)
           intercept = true;

        return intercept;
    }

    private boolean rvPullUpIntercept(View child) {
        boolean intercept = false;

        RecyclerView recyclerChild = (RecyclerView) child;
        if (recyclerChild.computeVerticalScrollExtent() + recyclerChild.computeVerticalScrollOffset()
                >= recyclerChild.computeVerticalScrollRange())
            intercept = true;

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE: {
                // 计算本次滑动的Y轴增量(距离)
                int dy = mLastYMoved - y;
                // 如果滑动增量小于0，即下拉操作
                if (dy < 0) {
                    if (mEnablePullDown) {
                        // 如果下拉的距离小于mLayoutHeader1/2的高度,则允许滑动
                        if (getScrollY() > 0 || Math.abs(getScrollY()) <= mLayoutHeader.getMeasuredHeight() / 2) {
                            if (status != TRY_LOAD_MORE && status != LOAD_MORE) {
                                scrollBy(0, dy);
                                if (status != REFRESH) {
                                    if (getScrollY() <= 0) {
                                        if (status != TRY_REFRESH)
                                            updateStatus(TRY_REFRESH);

                                        if (Math.abs(getScrollY()) > mEffectiveScroll)
                                            updateStatus(REFRESH);
                                    }
                                }
                            } else {
                                if (getScrollY() > 0) {
                                    dy = dy > 30 ? 30 : dy;
                                    scrollBy(0, dy);
                                    if (getScrollY() < mReachBottomScroll + mEffectiveScroll) {
                                        updateStatus(TRY_LOAD_MORE);
                                    }
                                }
                            }
                        }
                    }
                } else if (dy > 0) {
                    if (mEnablePullUp) {
                        if (getScrollY() <= mReachBottomScroll + mLayoutFooter.getMeasuredHeight() / 2) {
                            // 进行Y轴上的滑动
                            if (status != TRY_REFRESH && status != REFRESH) {
                                scrollBy(0, dy);
                                if (status != LOAD_MORE) {
                                    if (getScrollY() >= mReachBottomScroll) {
                                        if (status != TRY_LOAD_MORE)
                                            updateStatus(TRY_LOAD_MORE);

                                        if (getScrollY() >= mReachBottomScroll + mEffectiveScroll)
                                            updateStatus(LOAD_MORE);
                                    }
                                }
                            } else {
                                if (getScrollY() <= 0) {
                                    dy = dy > 30 ? 30 : dy;
                                    scrollBy(0, dy);
                                    if (Math.abs(getScrollY()) < mEffectiveScroll)
                                        updateStatus(TRY_REFRESH);
                                }
                            }
                        }
                    }
                }
                // 记录y坐标
                mLastYMoved = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                // 判断本次触摸系列事件结束时,Layout的状态
                switch (status) {
                    case NORMAL: {
                        upWithStatusNormal();
                        break;
                    }

                    case TRY_REFRESH: {
                        upWithStatusTryRefresh();
                        break;
                    }

                    case REFRESH: {
                        upWithStatusRefresh();
                        break;
                    }

                    case TRY_LOAD_MORE: {
                        upWithStatusTryLoadMore();
                        break;
                    }

                    case LOAD_MORE: {
                        upWithStatusLoadMore();
                        break;
                    }
                }
            }
        }
        mLastYIntercept = 0;
        postInvalidate();
        return true;
    }

    private void updateStatus(int status) {
        switch (status) {
            case NORMAL:
                break;
            case TRY_REFRESH: {
                this.status = TRY_REFRESH;
                break;
            }
            case REFRESH: {
                this.status = REFRESH;
                tvPullDown.setText("释放刷新");
                break;
            }
            case TRY_LOAD_MORE: {
                this.status = TRY_LOAD_MORE;
                break;
            }
            case LOAD_MORE:
                this.status = LOAD_MORE;
                tvPullUp.setText("释放刷新");
                break;
        }
    }

    private void upWithStatusNormal() {

    }

    private void upWithStatusTryRefresh() {
        // 取消本次的滑动
        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY(), SCROLL_SPEED);
        tvPullDown.setText("释放刷新");
        status = NORMAL;
    }

    private void upWithStatusRefresh() {
        mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - (-mEffectiveScroll)), SCROLL_SPEED);
        tvPullDown.setVisibility(View.GONE);
        glvPullDown.setVisibility(View.VISIBLE);
        glvPullDown.startAnim();
        // 通过Listener接口执行刷新时的监听事件
        if (mListener != null)
            mListener.onRefresh();
    }

    private void upWithStatusTryLoadMore() {
        mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - mReachBottomScroll), SCROLL_SPEED);
        tvPullUp.setText("释放刷新");
        status = NORMAL;
    }

    private void upWithStatusLoadMore() {
        mLayoutScroller.startScroll(0, getScrollY(), 0, -((getScrollY() - mEffectiveScroll) - mReachBottomScroll), SCROLL_SPEED);
        tvPullUp.setVisibility(View.GONE);
        elvPullUp.setVisibility(View.VISIBLE);
        elvPullUp.startAnim();
        // 通过Listener接口执行加载时的监听事件
        if (mListener != null)
            mListener.onLoadMore();
    }

    public void resetLayoutLocation() {
        status = NORMAL;
        scrollTo(0,0);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mLayoutScroller.computeScrollOffset()) {
            scrollTo(0, mLayoutScroller.getCurrY());
        }
        postInvalidate();
    }

    private static final int STOP_REFRESH = 1;
    private static final int STOP_LOAD_MORE = 2;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_REFRESH: {
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY(), SCROLL_SPEED);
                    tvPullDown.setText("释放刷新");
                    tvPullDown.setVisibility(View.VISIBLE);
                    glvPullDown.stopAnim();
                    glvPullDown.setVisibility(View.GONE);
                    status = NORMAL;
                    break;
                }

                case STOP_LOAD_MORE: {
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - mReachBottomScroll), SCROLL_SPEED);
                    tvPullUp.setText("释放刷新");
                    tvPullUp.setVisibility(View.VISIBLE);
                    elvPullUp.stopAnim();
                    elvPullUp.setVisibility(View.GONE);
                    status = NORMAL;
                    break;
                }
            }
        }
    };

    public void stopRefresh() {
        Message msg = mUIHandler.obtainMessage(STOP_REFRESH);
        mUIHandler.sendMessage(msg);
    }

    public void stopLoadMore() {
        Message msg = mUIHandler.obtainMessage(STOP_LOAD_MORE);
        mUIHandler.sendMessage(msg);
    }
}
