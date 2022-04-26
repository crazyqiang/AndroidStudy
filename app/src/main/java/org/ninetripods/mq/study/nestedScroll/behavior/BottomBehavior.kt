package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import org.ninetripods.mq.study.kotlin.ktx.log

class BottomBehavior(
    context: Context,
    attrs: AttributeSet? = null,
) : CoordinatorLayout.Behavior<View>(context, attrs) {

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
        return super.onStartNestedScroll(coordinatorLayout,
            child,
            directTargetChild,
            target,
            axes,
            type)
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

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int,
    ) {
        log("onNestedPreScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, target:$target, dx:$dx, dy:$dy, consumed:$consumed, type:$type)")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
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
        return dependency == parent.getChildAt(0)
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
//        child.y = dependency.bottom.toFloat()
//        child.x = dependency.left.toFloat()
//        child.translationY = dependency.translationY
        //不止改变位置 还改变高度
        child.layout(0,
            (dependency.bottom + dependency.translationY).toInt(),
            child.measuredWidth, child.measuredHeight)
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
     * 对子View的测量
     *
     * @param parent
     * @param child
     * @param parentWidthMeasureSpec
     * @param widthUsed
     * @param parentHeightMeasureSpec
     * @param heightUsed
     * @return
     */
    override fun onMeasureChild(
        parent: CoordinatorLayout,
        child: View,
        parentWidthMeasureSpec: Int,
        widthUsed: Int,
        parentHeightMeasureSpec: Int,
        heightUsed: Int,
    ): Boolean {
        log("onMeasureChild(parent:$parent, child:$child, parentWidthMeasureSpec:$parentWidthMeasureSpec," +
                " widthUsed:$widthUsed, parentHeightMeasureSpec:$parentHeightMeasureSpec, heightUsed:$heightUsed)")
        return super.onMeasureChild(parent, child,
            parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed)
    }

    /**
     * 对子View的布局
     *
     * @param parent
     * @param child
     * @param layoutDirection
     * @return
     */
    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int,
    ): Boolean {
        log("onLayoutChild(parent, child, layoutDirection)")
        if (parent.childCount < 2) return false
        val firstView = parent.getChildAt(0)
        child.layout(0, firstView.measuredHeight, child.measuredWidth, child.measuredHeight)
        return true
    }

}