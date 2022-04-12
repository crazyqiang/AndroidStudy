package org.ninetripods.mq.study.nestedScroll.widget

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import org.ninetripods.mq.study.R
import java.util.*

class NestedParentView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), NestedScrollingParent {

    companion object {
        private const val TAG = "TTT"
    }

    private val TOP_CHILD_FLING_THRESHOLD = 3
    private var mTop: View? = null
    private var mNav: View? = null
    private var mViewPager: ViewPager? = null
    private var mTopViewHeight = 0
    private val mScroller: OverScroller
    private var mOffsetAnimator: ValueAnimator? = null
    private val parentHelper: NestedScrollingParentHelper

    init {
        orientation = VERTICAL
        parentHelper = NestedScrollingParentHelper(this)
        mScroller = OverScroller(context)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mTop = findViewById(R.id.id_nested_layout_top)
        mNav = findViewById(R.id.id_nested_layout_indicator)
        val view = findViewById<View>(R.id.id_nested_layout_viewpager) as? ViewPager
            ?: throw RuntimeException(
                "id_stickynavlayout_viewpager show used by ViewPager !")
        mViewPager = view
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mTopViewHeight = mTop!!.measuredHeight
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        getChildAt(0).measure(widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED))
        val params = mViewPager!!.layoutParams
        params.height = measuredHeight - mNav!!.measuredHeight
        setMeasuredDimension(measuredWidth,
            mTop!!.measuredHeight + mNav!!.measuredHeight + mViewPager!!.measuredHeight)
    }

    /**
     * @param child
     * @param target
     * @param nestedScrollAxes ViewCompat.SCROLL_AXIS_VERTICAL
     * ViewCompat.SCROLL_AXIS_HORIZONTAL
     * @return true 表示父View接受嵌套滑动操作，消费部分距离
     * false 父View不会参与嵌套滑动了
     */
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        Log.e(TAG, "onStartNestedScroll")
        return true
    }

    /**
     * @param child
     * @param target
     * @param nestedScrollAxes ViewCompat.SCROLL_AXIS_VERTICAL
     * ViewCompat.SCROLL_AXIS_HORIZONTAL
     */
    override fun onNestedScrollAccepted(child: View, target: View, nestedScrollAxes: Int) {
        Log.e(TAG, "onNestedScrollAccepted")
        parentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes)
    }

    /**
     * 滑动之前被调用
     *
     * @param target   targetView
     * @param dx       x轴滑动
     * @param dy       y轴滑动
     * @param consumed 父View消费的x轴 y轴距离
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.e(TAG, "onNestedPreScroll---" + dx + "," + dy + "," + Arrays.toString(consumed))
        //往上滑并且滑动距离不超过topView的高度
        val hiddenTop = dy > 0 && scrollY < mTopViewHeight
        val showTop = dy < 0 && scrollY >= 0 && !target.canScrollVertically(-1)
        if (hiddenTop || showTop) {
            scrollBy(0, dy)
            //完全消费y轴的滑动
            consumed[1] = dy
        }
    }

    /**
     * 滑动之后被调用
     *
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
    ) {
        Log.e(TAG, "onNestedScroll")
    }

    override fun onStopNestedScroll(target: View) {
        Log.e(TAG, "onStopNestedScroll")
        parentHelper.onStopNestedScroll(target)
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        //不做拦截 可以传递给子View
        return false
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean,
    ): Boolean {
        //如果是recyclerView 根据判断第一个元素是哪个位置可以判断是否消耗
        //这里判断如果第一个元素的位置是大于TOP_CHILD_FLING_THRESHOLD的
        //认为已经被消耗，在animateScroll里不会对velocityY<0时做处理
        var consumed = consumed
        if (target is RecyclerView && velocityY < 0) {
            val recyclerView = target
            val firstChild = recyclerView.getChildAt(0)
            val childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild)
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        if (!consumed) {
            animateScroll(velocityY, computeDuration(0f), consumed)
        } else {
            animateScroll(velocityY, computeDuration(velocityY), consumed)
        }
        return true
    }

    override fun getNestedScrollAxes(): Int {
        Log.e(TAG, "getNestedScrollAxes")
        return 0
    }

    /**
     * 根据速度计算滚动动画持续时间
     *
     * @param velocityY
     * @return
     */
    private fun computeDuration(velocityY: Float): Int {
        var velocityY = velocityY
        val distance: Int
        distance = if (velocityY > 0) {
            Math.abs(mTop!!.height - scrollY)
        } else {
            Math.abs(mTop!!.height - (mTop!!.height - scrollY))
        }
        val duration: Int
        velocityY = Math.abs(velocityY)
        duration = if (velocityY > 0) {
            3 * Math.round(1000 * (distance / velocityY))
        } else {
            val distanceRatio = distance.toFloat() / height
            ((distanceRatio + 1) * 150).toInt()
        }
        return duration
    }

    private fun animateScroll(velocityY: Float, duration: Int, consumed: Boolean) {
        val currentOffset = scrollY
        val topHeight = mTop!!.height
        if (mOffsetAnimator == null) {
            mOffsetAnimator = ValueAnimator()
            mOffsetAnimator!!.interpolator = LinearInterpolator()
            mOffsetAnimator!!.addUpdateListener { animation ->
                if (animation.animatedValue is Int) {
                    scrollTo(0, animation.animatedValue as Int)
                }
            }
        } else {
            mOffsetAnimator!!.cancel()
        }
        mOffsetAnimator!!.duration = Math.min(duration, 600).toLong()
        if (velocityY >= 0) {
            mOffsetAnimator!!.setIntValues(currentOffset, topHeight)
            mOffsetAnimator!!.start()
        } else {
            //如果子View没有消耗down事件 那么就让自身滑倒0位置
            if (!consumed) {
                mOffsetAnimator!!.setIntValues(currentOffset, 0)
                mOffsetAnimator!!.start()
            }
        }
    }

    override fun scrollTo(x: Int, y: Int) {
        var y = y
        if (y < 0) {
            y = 0
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight
        }
        if (y != scrollY) {
            super.scrollTo(x, y)
        }
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.currY)
            invalidate()
        }
    }
}