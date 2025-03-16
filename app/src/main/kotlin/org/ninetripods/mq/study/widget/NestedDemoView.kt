package org.ninetripods.mq.study.widget

import android.view.View
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParent3

class NestedDemoView : NestedScrollingParent3 {
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onStopNestedScroll(target: View) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        TODO("Not yet implemented")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        TODO("Not yet implemented")
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNestedScrollAxes(): Int {
        TODO("Not yet implemented")
    }
}