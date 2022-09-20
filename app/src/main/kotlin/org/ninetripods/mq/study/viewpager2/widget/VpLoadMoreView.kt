package org.ninetripods.mq.study.viewpager2.widget

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
    private var mCurPos: Int = 0
    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private var mNeedIntercept: Boolean = false //是否需要拦截VP2事件

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
//                    if (state == ViewPager2.SCROLL_STATE_DRAGGING && mCurPos == mModels.lastIndex) {
//                        mNeedIntercept = true
////                        mMVPager2.get().isUserInputEnabled = false
//                        log("lastScroll")
//                        scrollTo(200, 0)
//                    }
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
//        var isIntercept = false
//        when (ev?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                isIntercept = false
//            }
//            MotionEvent.ACTION_MOVE -> {
//                isIntercept = mNeedIntercept
//            }
//            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                isIntercept = false
//            }
//        }
        return mNeedIntercept
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        log("parent: onTouchEvent")
        scrollTo(200, 0)
        return super.onTouchEvent(event)
    }
}