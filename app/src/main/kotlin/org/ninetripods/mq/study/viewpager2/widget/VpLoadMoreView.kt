package org.ninetripods.mq.study.viewpager2.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.OverScroller
import android.widget.TextView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.viewpager2.loader.TxNewsLoader

class VpLoadMoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private var mNeedIntercept: Boolean = false //是否需要拦截VP2事件
    private val mLoadMoreContainer: LinearLayout by id(R.id.load_more_container)
    private val mIvArrow: ImageView by id(R.id.iv_pull)
    private val mTvTips: TextView by id(R.id.tv_tips)

    private var mCurPos: Int = 0 //Banner当前滑动的位置
    private var mLastX = 0f
    private var mLastDownX = 0f //用于判断滑动方向
    private var mMenuWidth = 0 //加载更多View的宽度
    private var mShowMoreMenuWidth = 0 //加载更多发生变化时的宽度
    private var mLastStatus = false // 默认箭头样式
    private var mAction: (() -> Unit)? = null
    private var mScroller: OverScroller
    private var isTouchLeft = false //是否是向左滑动
    private var animRightStart = RotateAnimation(0f, -180f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 300
        fillAfter = true
    }

    private var animRightEnd = RotateAnimation(-180f, 0f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = 300
        fillAfter = true
    }

    init {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.fragment_tx_news, this)
        mScroller = OverScroller(context)
    }

    /**
     * @param mModels 要加载的数据
     * @param action 回调Action
     */
    fun setData(mModels: MutableList<Any>, action: () -> Unit) {
        this.mAction = action
        mMVPager2.setModels(mModels)
            .setLoop(false) //非循环模式
            .setIndicatorShow(false)
            .setLoader(TxNewsLoader(mModels))
            .setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(15))
            })
            .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
            .setAutoPlay(false)
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    showToast(mModels[position].toString())
                }
            })
            .registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    if (mCurPos == mModels.lastIndex && isTouchLeft && state == ViewPager2.SCROLL_STATE_DRAGGING) {
                        //Banner在最后一页 & 手势往左滑动 & 当前是滑动状态
                        mNeedIntercept = true //父View可以拦截
                        mMVPager2.setUserInputEnabled(false) //VP2设置为不可滑动
                    }
                }

                override fun onPageSelected(position: Int) {
                    mCurPos = position
                }
            })
            .start()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mMenuWidth = mLoadMoreContainer.measuredWidth
        mShowMoreMenuWidth = mMenuWidth / 3 * 2
        super.onLayout(changed, l, t, r, b)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = ev.x
                mLastDownX = ev.x
            }
            MotionEvent.ACTION_MOVE -> {
                isTouchLeft = mLastDownX - ev.x > 0 //判断滑动方向
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> isIntercept = mNeedIntercept //是否拦截Move事件
        }
        //log("ev?.action: ${ev?.action},isIntercept: $isIntercept")
        return isIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                val mDeltaX = mLastX - ev.x
                if (mDeltaX > 0) {
                    //向左滑动
                    if (mDeltaX >= mMenuWidth || scrollX + mDeltaX >= mMenuWidth) {
                        //右边缘检测
                        scrollTo(mMenuWidth, 0)
                        return super.onTouchEvent(ev)
                    }
                } else if (mDeltaX < 0) {
                    //向右滑动
                    if (scrollX + mDeltaX <= 0) {
                        //左边缘检测
                        scrollTo(0, 0)
                        return super.onTouchEvent(ev)
                    }
                }
                showLoadMoreAnim(scrollX + mDeltaX)
                scrollBy(mDeltaX.toInt(), 0)
                mLastX = ev.x
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                smoothCloseMenu()
                mNeedIntercept = false
                mMVPager2.setUserInputEnabled(true)
                //执行回调
                val mDeltaX = mLastX - ev.x
                if (scrollX + mDeltaX >= mShowMoreMenuWidth) {
                    mAction?.invoke()
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun smoothCloseMenu() {
        mScroller.forceFinished(true)
        /**
         * 左上为正，右下为负
         * startX：X轴开始位置
         * startY: Y轴结束位置
         * dx：X轴滑动距离
         * dy：Y轴滑动距离
         * duration：滑动时间
         */
        mScroller.startScroll(scrollX, 0, -scrollX, 0, 300)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            showLoadMoreAnim(0f) //动画还原
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }

    private fun showLoadMoreAnim(dx: Float) {
        val showLoadMore = dx >= mShowMoreMenuWidth
        if (mLastStatus == showLoadMore) return
        if (showLoadMore) {
            mIvArrow.startAnimation(animRightStart)
            mTvTips.text = "释放查看图文详情"
            mLastStatus = true
        } else {
            mIvArrow.startAnimation(animRightEnd)
            mTvTips.text = "滑动查看图文详情"
            mLastStatus = false
        }
    }
}