package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * 自定义Behavior必须要继承有参数的，否则会报以下错误：
 * Caused by: java.lang.RuntimeException: Could not inflate Behavior subclass org.ninetripods.mq.study.nestedScroll.behavior.CustomBehavior
 * ，因为CoordinatorLayout里是利用反射(use[CoordinatorLayout.parseBehavior]) 获取该Behavior的
 * @param context
 * @param attrs
 */
class CustomBehavior(
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

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View,
    ): Boolean {
        log("layoutDependsOn()")
        return dependency is TargetTextView
    }

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

    override fun onDependentViewRemoved(parent: CoordinatorLayout, child: View, dependency: View) {
        log("onDependentViewRemoved()")
        super.onDependentViewRemoved(parent, child, dependency)
    }
}