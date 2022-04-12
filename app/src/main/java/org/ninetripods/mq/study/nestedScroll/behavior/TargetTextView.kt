package org.ninetripods.mq.study.nestedScroll.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.ViewCompat
import kotlin.math.abs

class TargetTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    private val mScaleTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private var mLastX: Float = 0F
    private var mLastY: Float = 0F

    init {
        isClickable = true
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return super.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = event.rawX
                mLastY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = event.rawX - mLastX
                val dy = event.rawY - mLastY
                if (abs(dx) > mScaleTouchSlop || abs(dy) > mScaleTouchSlop) {
                    //将视图的水平、垂直位置偏移指定的像素量。offset:-偏移视图的像素数
                    ViewCompat.offsetLeftAndRight(this, dx.toInt())
                    ViewCompat.offsetTopAndBottom(this, dy.toInt())
                    mLastX = event.rawX
                    mLastY = event.rawY
                }
            }
        }
        return true
    }

}