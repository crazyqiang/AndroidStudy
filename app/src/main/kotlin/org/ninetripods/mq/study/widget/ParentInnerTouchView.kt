package org.ninetripods.mq.study.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.showToast

class ParentInnerTouchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val tvChildView: TextView

    init {
        inflate(context, R.layout.expand_touch_view, this)
        tvChildView = findViewById(R.id.tv_expand_view)
        tvChildView.setOnClickListener { showToast("扩大了点击事件") }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let { ev ->
            if (ev.action == MotionEvent.ACTION_DOWN) {
                val isChildHit = isHitExpandChildView(tvChildView, Pair(ev.rawX, ev.rawY))
                log("isChildHit = $isChildHit")
                if (isChildHit) {
                    //将事件传递给子控件
                    tvChildView.performClick()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    /**
     * 是否命中子View的扩大点击区域
     * @param childView 子View
     * @param touchPair 点击位置(x,y)
     * @param expandSize 扩大点击区域的大小
     * @return
     */
    private fun isHitExpandChildView(
        childView: View,
        touchPair: Pair<Float, Float>,
        expandSize: Int = 50.dp2px()
    ): Boolean {

        //子View在屏幕上的位置
        val location = IntArray(2)
        childView.getLocationOnScreen(location)

        val childX = location[0].toFloat()
        val childY = location[1].toFloat()
        val touchX = touchPair.first
        val touchY = touchPair.second

        log("childX = $childX, childY = $childY, touchX = $touchX, touchY = $touchY")

        //扩大点击区域
        val rect = RectF()
        rect.set(
            childX - expandSize,
            childY - expandSize,
            childX + childView.width + expandSize,
            childY + childView.height + expandSize
        )
        log("rect = $rect")
        //判断点击区域是否在扩大的子View区域内，如果在，去执行子View的点击事件
        return rect.contains(touchX, touchY)
    }

}
