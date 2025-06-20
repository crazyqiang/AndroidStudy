package org.ninetripods.mq.study.widget.matrix

import android.view.MotionEvent
import kotlin.math.atan2

/**
 * 实现旋转手势
 */
class RotationGestureDetector(private val listener: OnRotationGestureListener) {
    private var prevAngle = 0f

    interface OnRotationGestureListener {
        fun onRotation(rotationDetector: RotationGestureDetector, angle: Float)
    }

    fun onTouchEvent(event: MotionEvent?) {
        if (event == null) return
        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    prevAngle = calculateAngle(event)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 2) {
                    val angle = calculateAngle(event)
                    val delta = angle - prevAngle
                    listener.onRotation(this, delta)
                    prevAngle = angle
                }
            }

            MotionEvent.ACTION_UP -> {}
        }
    }

    /**
     * 根据两个触摸点的位置，计算它们连线相对于水平方向的角度（单位为度）
     */
    private fun calculateAngle(event: MotionEvent): Float {
        val dx = event.getX(1) - event.getX(0)
        val dy = event.getY(1) - event.getY(0)
        /**
         * 1、atan2(dy, dx) 计算连线与水平方向的夹角，返回的是弧度；
         * 2、Math.toDegrees(...) 把角度从弧度转换成角度制（degree），范围为 -180° ~ +180°
         */
        return Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())).toFloat()
    }
}