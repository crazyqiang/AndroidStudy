package org.ninetripods.lib_viewpager2.proxy

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

var isCustomScrollTime: Boolean = true
const val SCROLLER_INTERVAL = 1000

/**
 * 自定义LinearLayoutManager，自定义轮播速率
 */
class LayoutManagerProxy(
    context: Context,
    layoutManager: LinearLayoutManager
) : LinearLayoutManager(
    context, layoutManager.orientation, false
) {

    override fun performAccessibilityAction(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        action: Int,
        args: Bundle?
    ): Boolean {
        return super.performAccessibilityAction(recycler, state, action, args)
    }

    override fun onInitializeAccessibilityNodeInfo(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        info: AccessibilityNodeInfoCompat
    ) {
        super.onInitializeAccessibilityNodeInfo(recycler, state, info)
    }

    override fun requestChildRectangleOnScreen(
        parent: RecyclerView,
        child: View,
        rect: Rect,
        immediate: Boolean,
        focusedChildVisible: Boolean
    ): Boolean {
        return super.requestChildRectangleOnScreen(
            parent,
            child,
            rect,
            immediate,
            focusedChildVisible
        )
    }

    override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
        super.calculateExtraLayoutSpace(state, extraLayoutSpace)
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller = LinearSmoothScrollerProxy(recyclerView!!.context)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    internal class LinearSmoothScrollerProxy(context: Context) : LinearSmoothScroller(context) {

        /**
         * 控制轮播切换速度
         */
        override fun calculateTimeForScrolling(dx: Int): Int {
            return if (isCustomScrollTime) SCROLLER_INTERVAL else super.calculateTimeForScrolling(dx)
        }

    }
}