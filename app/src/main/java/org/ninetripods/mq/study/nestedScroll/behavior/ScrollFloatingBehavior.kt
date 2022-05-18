package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ScrollFloatingBehavior(
    context: Context,
    attrs: AttributeSet? = null,
) : FloatingActionButton.Behavior(context, attrs) {

    private val Tag = "ScrollFloatingBehavior"
    private var mChildView: FloatingActionButton? = null
    private var mHandler = Handler(Looper.getMainLooper())
    private var hasFloatingBtnShow = true //按钮是否展示
    private var runnable: Runnable = Runnable {
        mChildView?.let {
            ViewCompat.animate(it)
                .translationX(0f)
                .setInterpolator(DecelerateInterpolator(3f))
                .start()
        }
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int,
    ): Boolean {
        Log.e(Tag, "onStartNestedScroll:hasFloatingBtnShow $hasFloatingBtnShow")
        if (hasFloatingBtnShow) {
            hasFloatingBtnShow = false
            mHandler.removeCallbacks(runnable)
            ViewCompat.animate(child)
                .translationX((child.width * 2).toFloat())
                .setInterpolator(AccelerateInterpolator(3f))
                .start()
        }
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: FloatingActionButton,
        target: View,
        type: Int,
    ) {
        Log.e(Tag, "onStopNestedScroll:hasFloatingBtnShow $hasFloatingBtnShow")
        mChildView = child
        if (!hasFloatingBtnShow) {
            hasFloatingBtnShow = true
            mHandler.postDelayed(runnable, 1000)
        }
        super.onStopNestedScroll(coordinatorLayout, child, target, type)
    }
}