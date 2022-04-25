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
class CustomBehavior(
    context: Context,
    attrs: AttributeSet? = null,
) : CoordinatorLayout.Behavior<View>(context, attrs) {

    // 一、
    // 1、onMeasureChild()、onLayoutChild()是对子View的测量、布局
    // 2、layoutDependsOn()、onDependentViewChanged()、onDependentViewRemoved()是子View之间设置Behavior的条件等
    // 3、onInterceptTouchEvent()、onTouchEvent()对事件的拦截与处理
    // 4、onStartNestedScroll()、onNestedScrollAccepted()、onNestedScroll()、 onNestedPreScroll()、
    //    onStopNestedScroll()、onNestedFling()、onNestedPreFling()执行嵌套滑动
    // 二、
    // (use[CoordinatorLayout.parseBehavior])如何反射获取Behavior

    /**
     * 对启动嵌套滚动操作的子View(ViewCompat#startNestedScroll(View, int))作出反应。
     * @param coordinatorLayout 父View
     * @param child CoordinatorLayout中的子View
     * @param directTargetChild 发生滑动的View在CoordinatorLayout中的直接子View
     * @param target 发生滑动的View，target <= directTargetChild，当target是CoordinatorLayout直接子View时，target = directTargetChild
     * @param axes 横、竖方向 (ViewCompat.SCROLL_AXIS_HORIZONTAL, ViewCompat.SCROLL_AXIS_VERTICAL)
     * @param type ViewCompat.TYPE_TOUCH(触摸滑动，值为0)、ViewCompat.TYPE_NON_TOUCH(非触摸滑动，即惯性滑动，值为1)
     * @return 返回true，表示参与嵌套滑动；返回false，不参与嵌套滑动
     */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View,
        directTargetChild: View, target: View, axes: Int, type: Int,
    ): Boolean {
        ViewCompat.TYPE_TOUCH
        log("onStartNestedScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, directTargetChild:$directTargetChild, target:$target, axes:$axes, type:$type)")
        return super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    /**
     * onStartNestedScroll()返回true时会执行到该方法，可以做一些初始化操作。参数跟onStartNestedScroll()中的含义一致
     */
    override fun onNestedScrollAccepted(
        coordinatorLayout: CoordinatorLayout, child: View,
        directTargetChild: View, target: View, axes: Int, type: Int,
    ) {
        log("onNestedScrollAccepted(coordinatorLayout:$coordinatorLayout," +
                " child:$child, directTargetChild:$directTargetChild, target:$target, axes:$axes, type:$type)")
        super.onNestedScrollAccepted(
            coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    /**
     * 真正开始滑动时回调的方法
     * @param coordinatorLayout 父View
     * @param child
     * @param target 目标View
     * @param dx x轴滑动距离 dx>0 向左滑动；dx<0 向右滑动
     * @param dy y轴滑动距离 dy>0 向上滑动；dy<0 想下滑动
     * @param consumed 消耗的滑动距离，类型为IntArray，大小为2，consumed[0]是对dx的消耗，consumed[1]是对dy的消耗。需要手动将消耗的dx、dy赋值给consumed数组
     * @param type ViewCompat.TYPE_TOUCH(触摸滑动，值为0)、ViewCompat.TYPE_NON_TOUCH(非触摸滑动，即惯性滑动，值为1)
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout, child: View,
        target: View, dx: Int, dy: Int, consumed: IntArray, type: Int,
    ) {
        log("onNestedPreScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, target:$target, dx:$dx, dy:$dy, consumed:$consumed, type:$type)")
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    /**
     * 前面onNestedPreScroll()执行完后，剩余事件交给target去处理，如果有剩余，会将剩余事件又交给Behavior去滑动
     * @param coordinatorLayout 父View
     * @param child
     * @param target
     * @param dxConsumed dx已消耗的x方向的值
     * @param dyConsumed dy已消耗的y方向的值
     * @param dxUnconsumed dx未消耗的x方向的值
     * @param dyUnconsumed dy未消耗的y方向的值
     * @param type ViewCompat.TYPE_TOUCH(触摸滑动，值为0)、ViewCompat.TYPE_NON_TOUCH(非触摸滑动，即惯性滑动，值为1)
     * @param consumed 存放消耗事件  注意：消耗之后应该进行叠加而不是赋值
     */
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, target: View, dxConsumed: Int,
        dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int, consumed: IntArray,
    ) {
        log("onNestedScroll(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, dxConsumed:$dxConsumed," +
                " dyConsumed:$dyConsumed, dxUnconsumed:$dxUnconsumed, dyUnconsumed$dyUnconsumed, type:$type, consumed:$consumed)")
        super.onNestedScroll(coordinatorLayout,
            child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @param consumed
     * @return
     */
    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, velocityX: Float, velocityY: Float, consumed: Boolean,
    ): Boolean {
        log("onNestedFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY, consumed:$consumed)")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }


    /**
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param velocityX
     * @param velocityY
     * @return
     */
    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, velocityX: Float, velocityY: Float,
    ): Boolean {
        log("onNestedPreFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY)")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param type
     */
    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int,
    ) {
        log("onStopNestedScroll(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, type:$type)")
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
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
        parent: CoordinatorLayout, child: View,
        parentWidthMeasureSpec: Int, widthUsed: Int, parentHeightMeasureSpec: Int, heightUsed: Int,
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
        parent: CoordinatorLayout, child: View, layoutDirection: Int,
    ): Boolean {
        log("onLayoutChild(parent, child, layoutDirection)")
        if (parent.childCount < 2) return false
        val firstView = parent.getChildAt(0)
        child.layout(0, firstView.measuredHeight, child.measuredWidth, child.measuredHeight)
        return true
    }

    /**
     * 是否对事件进行拦截
     * @param parent 父View
     * @param child 子View
     * @param ev MotionEvent事件
     */
    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout, child: View, ev: MotionEvent,
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