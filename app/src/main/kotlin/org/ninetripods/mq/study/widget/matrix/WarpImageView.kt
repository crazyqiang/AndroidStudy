package org.ninetripods.mq.study.widget.matrix

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.animation.doOnEnd
import kotlin.math.min

/**
 * SetPolyToPoly View
 * 用法：
 *   warpView.setImageBitmap(bitmap)
 *   warpView.setTargetCorners(floatArrayOf(...8个点...))
 *   warpView.startCorrection(600L)
 */
class WarpImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var bitmap: Bitmap? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private val matrix = Matrix()

    private var srcPts = FloatArray(8) // 源点（bitmap坐标系）

    private var startDst = FloatArray(8) // 起始目标点（view坐标系）
    private var endDst: FloatArray? = null // 最终目标点（view坐标系）
    private var currentDst = FloatArray(8) // 动画当前目标点
    private var animator: ValueAnimator? = null

    fun setImageBitmap(bm: Bitmap) {
        bitmap = bm
        setupSrcAndStartDst()
        invalidate()
    }

    fun setTargetCorners(dst: FloatArray) {
        endDst = dst.copyOf(8)
    }

    fun startCorrection(duration: Long = 500L) {
        val start = startDst.copyOf()
        val end = endDst ?: return
        animateCorners(start, end, duration)
    }

    fun resetToStart(duration: Long = 500L) {
        val start = currentDst.copyOf()
        val end = startDst.copyOf()
        animateCorners(start, end, duration)
    }

    /** 通用动画方法 */
    private fun animateCorners(
        start: FloatArray,
        end: FloatArray,
        duration: Long,
        onEnd: (() -> Unit)? = null
    ) {
        animator?.cancel()

        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            this.duration = duration
            addUpdateListener { va ->
                val frac = va.animatedValue as Float
                for (i in 0 until 8) {
                    currentDst[i] = start[i] + (end[i] - start[i]) * frac
                }
                matrix.reset()
                matrix.setPolyToPoly(srcPts, 0, currentDst, 0, 4)
                invalidate()
            }
            doOnEnd { onEnd?.invoke() }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bm = bitmap ?: return
        if (matrix.isIdentity) {
            matrix.setPolyToPoly(srcPts, 0, currentDst, 0, 4)
        }
        canvas.drawBitmap(bm, matrix, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupSrcAndStartDst()
    }

    private fun setupSrcAndStartDst() {
        val bm = bitmap ?: return
        val bw = bm.width.toFloat()
        val bh = bm.height.toFloat()

        srcPts = floatArrayOf(0f, 0f, bw, 0f, bw, bh, 0f, bh)

        val vw = width.toFloat()
        val vh = height.toFloat()
        if (vw == 0f || vh == 0f) {
            post { setupSrcAndStartDst() }
            return
        }

        val scale = min(vw / bw, vh / bh)
        val sw = bw * scale
        val sh = bh * scale
        val dx = (vw - sw) / 2f
        val dy = (vh - sh) / 2f

        startDst = floatArrayOf(
            dx, dy,
            dx + sw, dy,
            dx + sw, dy + sh,
            dx, dy + sh,
        )
        System.arraycopy(startDst, 0, currentDst, 0, 8)
    }
}