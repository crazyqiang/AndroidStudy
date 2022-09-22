package org.ninetripods.mq.study.viewpager2.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.lib_viewpager2.log
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.viewpager2.loader.TxNewsLoader

class VpLoadMoreView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    companion object {
        const val STATE_CLOSED = 0 //关闭状态
        const val STATE_OPEN = 1 //打开状态
        const val STATE_MOVING_LEFT = 2 //左滑将要打开状态
        const val STATE_MOVING_RIGHT = 3 //右滑将要关闭状态
    }

    private var mCurPos: Int = 0
    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private var mNeedIntercept: Boolean = false //是否需要拦截VP2事件
    private var mLastX = 0f
    private var mLastY = 0f
    private var mDownX = 0f
    private var mDownY = 0f
    private val mLoadMoreContainer: LinearLayout by id(R.id.load_more_container)
    private var mMenuWidth = 0 //加载更多宽度
    private var mCurState = 0 //当前滑动状态

    init {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.fragment_tx_news, this)
    }

    fun setData(mModels: MutableList<Any>) {
        mMVPager2.setModels(mModels)
            .setLoop(false) //非循环模式
            .setIndicatorShow(false)
            .setLoader(TxNewsLoader(mModels))
//            .setPagePadding(40, 0, 60, 0)
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
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    log("onPageScrolled -> position:$position, " +
                            "positionOffset:$positionOffset, positionOffsetPixels:$positionOffsetPixels")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    log("onPageScrollStateChanged -> state:$state")
                    if (state == ViewPager2.SCROLL_STATE_DRAGGING && mCurPos == mModels.lastIndex) {
                        mNeedIntercept = true
                        mMVPager2.setUserInputEnabled(false)
//                        mMVPager2.get().isUserInputEnabled = false
                        log("lastScroll")
                    }
                }

                override fun onPageSelected(position: Int) {
                    mCurPos = position
                    if (mCurPos == mModels.lastIndex) mNeedIntercept = true
                    log("onPageSelected -> position:$position")
//                    val innerRecyclerView = mMVPager2.get().getChildAt(0) as RecyclerView
//                    if (position == mModels.lastIndex) {
//                        innerRecyclerView.setPadding(300, 0, 300, 0)
//                    } else {
//                        innerRecyclerView.setPadding(40, 0, 60, 0)
//                    }
                }
            })
            .start()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mMenuWidth = mLoadMoreContainer.measuredWidth
        super.onLayout(changed, l, t, r, b)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                isIntercept = false
                mDownX = ev.x
                mDownY = ev.y
                mLastX = ev.x
                log("mLastX:$mLastX,mDownY:$mDownY")
            }
            MotionEvent.ACTION_MOVE -> {
                isIntercept = mNeedIntercept
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isIntercept = false
            }
        }
        log("ev?.action: ${ev?.action},isIntercept: $isIntercept")
        return isIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                val dx = mDownX - ev.x
                val dy = mDownY - ev.y
                val mDeltaX = mLastX - ev.x
                log("parent: onTouchEvent -> ${ev.action}," +
                        "mLastX:$mLastX,mDeltaX:$mDeltaX，getScrollX:$scaleX，mMenuWidth：$mMenuWidth")
                if (mDeltaX > 0) {
                    //向左滑动
                    mCurState = STATE_MOVING_LEFT
                    if (mDeltaX >= mMenuWidth || scaleX + mDeltaX >= mMenuWidth) {
                        //右边缘检测
                        mCurState = STATE_OPEN
                        scrollTo(mMenuWidth, 0)
                        return super.onTouchEvent(ev)
                    }
                } else if (mDeltaX < 0) {
                    //向右滑动
                    mCurState = STATE_MOVING_RIGHT
                    if (scaleX + mDeltaX <= 0) {
                        //左边缘检测
                        mCurState = STATE_CLOSED
                        scrollTo(0, 0)
                        return super.onTouchEvent(ev)
                    }
                }
                scrollBy(mDeltaX.toInt(), 0)
                mLastX = ev.x
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
            }
        }
        return super.onTouchEvent(ev)
    }
}