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
        log("bottom: layoutDependsOn()")
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
        log("bottom: onDependentViewChanged()")
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
        log("bottom: onLayoutChild(layoutDirection:$layoutDirection)")
        if (parent.childCount < 2) return false
        val firstView = parent.getChildAt(0)
        child.layout(0, firstView.measuredHeight, child.measuredWidth, child.measuredHeight)
        return true
    }

}