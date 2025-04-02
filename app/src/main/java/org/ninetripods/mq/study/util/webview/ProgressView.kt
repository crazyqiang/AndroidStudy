package org.ninetripods.mq.study.util.webview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by MQ on 2017/4/1.
 */
class ProgressView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //初始化画笔
    private var mPaint: Paint = Paint().apply {
        isDither = true
        isAntiAlias = true
        strokeWidth = 10f
        color = Color.RED
    }
    private var mWidth = 0
    private var mHeight = 0
    private var progress = 0 //加载进度

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        mWidth = w
        mHeight = h
        super.onSizeChanged(w, h, ow, oh)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, (mWidth * progress / 100).toFloat(), mHeight.toFloat(), mPaint)
        super.onDraw(canvas)
    }

    /**
     * 设置新进度 重新绘制
     *
     * @param newProgress 新进度
     */
    fun setProgress(newProgress: Int) {
        this.progress = newProgress
        invalidate()
    }

    /**
     * 设置进度条颜色
     *
     * @param color 色值
     */
    fun setColor(color: Int) {
        mPaint.color = color
    }
}
