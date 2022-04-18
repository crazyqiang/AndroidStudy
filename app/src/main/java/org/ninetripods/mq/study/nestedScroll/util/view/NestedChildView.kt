package org.ninetripods.mq.study.nestedScroll.util.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper

/**
 * Created by mq on 2018/4/6 下午5:22
 * mqcoder90@gmail.com
 */
class NestedChildView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), NestedScrollingChild {
    private val mChildHelper: NestedScrollingChildHelper
    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mChildHelper.isNestedScrollingEnabled = enabled
    }

    /**
     * 是否可以嵌套滑动
     *
     * @return true表示可以嵌套滑动
     */
    override fun isNestedScrollingEnabled(): Boolean {
        return mChildHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        //告诉Parent自己要滑动
        return mChildHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        mChildHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return mChildHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        //Parent是否需要继续滑动你剩下的距离
        return mChildHelper.dispatchNestedScroll(dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow)
    }

    /**
     * @param dx
     * @param dy
     * @param consumed       父View消费的长度
     * @param offsetInWindow 子View窗体偏移量
     * @return
     */
    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        //询问Parent是否需要滑动
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY)
    }

    init {
        mChildHelper = NestedScrollingChildHelper(this)
    }
}