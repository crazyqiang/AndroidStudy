package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * 自定义Behavior必须要继承有参数的，否则会报以下错误：
 * Caused by: java.lang.RuntimeException: Could not inflate Behavior subclass org.ninetripods.mq.study.nestedScroll.behavior.CustomBehavior
 * ，因为CoordinatorLayout里是利用反射(use[CoordinatorLayout.parseBehavior]) 获取该Behavior的
 * @param context
 * @param attrs
 */
class TopBehavior(
    context: Context,
    attrs: AttributeSet? = null,
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    // 1、onMeasureChild()、onLayoutChild()是对子View的测量、布局
    // 2、layoutDependsOn()、onDependentViewChanged()、onDependentViewRemoved()是子View之间设置Behavior的条件等
    // 3、onInterceptTouchEvent()、onTouchEvent()对事件的拦截与处理
    // 4、onStartNestedScroll()、onNestedScrollAccepted()、onNestedScroll()、 onNestedPreScroll()、
    //    onStopNestedScroll()、onNestedFling()、onNestedPreFling()执行嵌套滑动

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
        log("onStartNestedScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, directTargetChild:$directTargetChild, target:$target, axes:$axes, type:$type)")
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ) {
        log("onNestedScrollAccepted(coordinatorLayout:$coordinatorLayout," +
                " child:$child, directTargetChild:$directTargetChild, target:$target, axes:$axes, type:$type)")
        super.onNestedScrollAccepted(coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type)
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int,
    ) {
        //向上滑动时 dy>0 ; 向下滑动时 dy<0
        log("onNestedPreScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, target:$target, dx:$dx, dy:$dy, consumed:$consumed, type:$type)")
        //super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        // 注意，手指向上滑动的时候，dy大于0。向下的时候dy小于0。
        val translationY = child.translationY
        if (-translationY >= child.measuredHeight || dy < 0) {
            // child已经滚动到屏幕外了，或者向下滚动，就不去消耗滚动了
            return
        }
        // 还差desireHeight距离将会移出屏幕外
        val desireHeight = translationY + child.measuredHeight
        if (dy <= desireHeight) {
            // 将dy全部消耗掉
            child.translationY = translationY - dy
            consumed[1] = dy
        } else {
            // 消耗一部分的的dy
            child.translationY = translationY - desireHeight
            consumed[1] = desireHeight.toInt()
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray,
    ) {
        log("onNestedScroll(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, dxConsumed:$dxConsumed," +
                " dyConsumed:$dyConsumed, dxUnconsumed:$dxUnconsumed, dyUnconsumed$dyUnconsumed, type:$type, consumed:$consumed)")
        super.onNestedScroll(coordinatorLayout,
            child,
            target,
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            type,
            consumed)
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        type: Int,
    ) {
        log("onStopNestedScroll(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, type:$type)")
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean,
    ): Boolean {
        log("onNestedFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY, consumed:$consumed)")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        velocityX: Float,
        velocityY: Float,
    ): Boolean {
        log("onNestedPreFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY)")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    /**
     * @param parent 父View
     * @param child 设置当前Behavior的子View
     * @param dependency 依赖的View
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View,
    ): Boolean {
        log("layoutDependsOn()")
        return dependency is TargetTextView
    }

    /**
     * dependency View发生变化时执行
     * @param parent 父View
     * @param child 设置当前Behavior的子View
     * @param dependency 依赖的View
     * @return
     */
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View,
    ): Boolean {
        log("onDependentViewChanged()")
        child.y = dependency.bottom.toFloat()
        child.x = dependency.left.toFloat()
        return true
    }

    /**
     * dependency View从父View中移除的时候，即child失去dependency View依赖时执行
     * @param parent
     * @param child
     * @param dependency
     */
    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        log("onDependentViewRemoved()")
        super.onDependentViewRemoved(parent, child, dependency)
    }

    /**
     * 是否对事件进行拦截
     * @param parent 父View
     * @param child 子View
     * @param ev MotionEvent事件
     */
    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: View,
        ev: MotionEvent,
    ): Boolean {
        return super.onInterceptTouchEvent(parent, child, ev)
    }

    /**
     * 事件处理
     * @param parent 父View
     * @param child 子View
     * @param ev MotionEvent事件
     */
    override fun onTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {
        return super.onTouchEvent(parent, child, ev)
    }

}