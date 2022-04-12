package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

class TargetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val mScaleTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mLastX: Float = 0F
    private var mLastY: Float = 0F

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.x
                mLastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.x - mLastX
                val dy = event.y - mLastY
                if (abs(dx) > mScaleTouchSlop || abs(dy) > mScaleTouchSlop){
                    mLastX = event.x
                    mLastY = event.y
                }
            }
        }
        return super.onTouchEvent(event)
    }

}