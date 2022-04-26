package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import org.ninetripods.mq.study.kotlin.ktx.log

class TopBehavior(
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
        log("top: onStartNestedScroll(axes:$axes, type:$type)")
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
        log("top: onNestedScrollAccepted(axes:$axes, type:$type)")
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
        //Scroller向上滑动时 dy>0 ; 向下滑动时 dy<0
        log("top: onNestedPreScroll(dx:$dx, dy:$dy, consumed:$consumed, type:$type)")
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
        log("top: onNestedScroll(dxConsumed:$dxConsumed," +
                " dyConsumed:$dyConsumed, dxUnconsumed:$dxUnconsumed, dyUnconsumed$dyUnconsumed, type:$type, consumed:$consumed)")
//        super.onNestedScroll(coordinatorLayout,
//            child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed)

        val translationY = child.translationY
        // 手指向上滚动或者child已经滚出了屏幕，不去处理
        if (translationY >= 0 || dyUnconsumed > 0) return
        if (dyUnconsumed > translationY) {
            //全部消耗
            consumed[1] += dyUnconsumed
            child.translationY = translationY - dyUnconsumed
        } else {
            //消耗一部分
            consumed[1] += child.translationY.toInt()
            child.translationY = 0F
        }
    }

}