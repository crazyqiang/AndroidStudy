package org.ninetripods.lib_viewpager2.proxy

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Method

/**
 * 自定义LinearLayoutManager，自定义轮播速率
 */
class LayoutManagerProxy(
    val context: Context,
    private val layoutManager: LinearLayoutManager,
    private val customSwitchAnimDuration: Int = 0,
) : LinearLayoutManager(
    context, layoutManager.orientation, false
) {

    override fun performAccessibilityAction(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        action: Int,
        args: Bundle?
    ): Boolean {
        return layoutManager.performAccessibilityAction(recycler, state, action, args)
    }

    override fun onInitializeAccessibilityNodeInfo(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        info: AccessibilityNodeInfoCompat
    ) {
        layoutManager.onInitializeAccessibilityNodeInfo(recycler, state, info)
    }

    override fun onInitializeAccessibilityNodeInfoForItem(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        host: View,
        info: AccessibilityNodeInfoCompat
    ) {
        layoutManager.onInitializeAccessibilityNodeInfoForItem(recycler, state, host, info)
    }

    override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
        try {
            val method: Method = layoutManager.javaClass.getDeclaredMethod(
                "calculateExtraLayoutSpace",
                state.javaClass,
                extraLayoutSpace.javaClass
            )
            method.isAccessible = true
            method.invoke(layoutManager, state, extraLayoutSpace)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun requestChildRectangleOnScreen(
        parent: RecyclerView,
        child: View,
        rect: Rect,
        immediate: Boolean,
        focusedChildVisible: Boolean
    ): Boolean {
        return layoutManager.requestChildRectangleOnScreen(
            parent,
            child,
            rect,
            immediate,
            focusedChildVisible
        )
    }

    /**
     * 主要改变的是下面这个方法
     */
    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller =
            LinearSmoothScrollerProxy(context, customSwitchAnimDuration)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    /**
     * 优化RecyclerView: Inconsistency detected. Invalid item position
     * https://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
     */
//    override fun supportsPredictiveItemAnimations(): Boolean {
//        return false
//    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    internal class LinearSmoothScrollerProxy(
        context: Context,
        private val customSwitchAnimDuration: Int = 0
    ) :
        LinearSmoothScroller(context) {

        /**
         * 控制轮播切换速度
         */
        override fun calculateTimeForScrolling(dx: Int): Int {
            return if (customSwitchAnimDuration != 0)
                customSwitchAnimDuration
            else
                super.calculateTimeForScrolling(dx)
        }

    }
}