package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
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

    // 一、
    // 1、onMeasureChild()、onLayoutChild()是对子View的测量、布局
    // 2、layoutDependsOn()、onDependentViewChanged()、onDependentViewRemoved()是子View之间设置Behavior的条件等
    // 3、onInterceptTouchEvent()、onTouchEvent()对事件的拦截与处理
    // 4、onStartNestedScroll()、onNestedScrollAccepted()、onNestedPreScroll()、onNestedScroll()、
    //    onNestedPreFling()、onNestedFling()、onStopNestedScroll()执行嵌套滑动
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
        log("onStartNestedScroll(coordinatorLayout:$coordinatorLayout, " +
                "child:$child, directTargetChild:$directTargetChild, target:$target, axes:$axes, type:$type)")
        return super.onStartNestedScroll(
            coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    /**
     * onStartNestedScroll()返回true时会执行到该方法，可以做一些初始化操作。参数跟onStartNestedScroll()中的含义一致
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param axes
     * @param type
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
     * 前面onNestedPreScroll()执行完后，剩余事件交给子View去处理，子View执行完后，如果还有剩余，会将剩余事件又交给Behavior去滑动
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
     * 1、子View发生惯性滑动时，同样会先传给Behavior的onNestedFling()方法。该方法判断是否需要消耗本次惯性滑动。
     * 2、在NestedScrollingParent2/NestedScrollingParent3中默认会在Child中去消耗惯性滑动，Behavior中保持默认实现即可
     * @param coordinatorLayout 父View
     * @param child
     * @param target
     * @param velocityX 惯性滑动的水平速度
     * @param velocityY 惯性滑动的垂直速度
     * @return  返回true，表示会消耗惯性滑动；反之false，表示不消耗惯性滑动
     */
    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, velocityX: Float, velocityY: Float,
    ): Boolean {
        log("onNestedPreFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY)")
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    /**
     * 执行惯性滑动处理
     * @param coordinatorLayout 父View
     * @param child
     * @param target
     * @param velocityX 惯性滑动的水平速度
     * @param velocityY 惯性滑动的垂直速度
     * @param consumed 发起滚动的子View是否消耗此次惯性滑动
     * @return 返回true，表示Behavior消耗此次惯性滑动
     */
    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: View, target: View, velocityX: Float, velocityY: Float, consumed: Boolean,
    ): Boolean {
        log("onNestedFling(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, velocityX:$velocityX, velocityY:$velocityY, consumed:$consumed)")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    /**
     * 滚动结束后会执行该方法
     * @param coordinatorLayout 父View
     * @param child
     * @param target
     * @param type ViewCompat.TYPE_TOUCH(触摸滑动，值为0)、ViewCompat.TYPE_NON_TOUCH(非触摸滑动，即惯性滑动，值为1)
     */
    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout, child: View, target: View, type: Int,
    ) {
        log("onStopNestedScroll(coordinatorLayout:$coordinatorLayout, child:$child, target:$target, type:$type)")
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }

    /**
     * child、dependency都是parent（CoordinatorLayout）的直接或间接子View，设置Behavior的子View和其他子View是否存在依赖关系。
     * Behavior会遍历除child之外的其他子View，带入该方法判断是否存在依赖关系，即dependency是一直会变的且layoutDependsOn()会被执行多次。
     * @param parent 父View
     * @param child 设置当前Behavior的子View
     * @param dependency 依赖的View
     * @return 返回true，存在依赖关系；返回false，不存在依赖关系
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View,
    ): Boolean {
        log("layoutDependsOn(parent: $parent, child: $child, dependency)")
        return super.layoutDependsOn(parent, child, dependency)
    }

    /**
     * dependency View发生变化时执行
     * @param parent 父View
     * @param child 设置当前Behavior的子View
     * @param dependency 依赖的View
     * @return child发生变化时返回true
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
     * 对子View的布局 CoordinatorLayout是一个FrameLayout，所以需要自行实现对子View的布局和测量
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
     * 是否对事件进行拦截  相当于CoordinatorLayout中的事件拦截抽离到Behavior中了
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