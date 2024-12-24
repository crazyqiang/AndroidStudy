package org.ninetripods.mq.study.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ShadowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.WHITE // 控件填充色
        style = Paint.Style.FILL
        setShadowLayer(10f, 5f, 5f, Color.GRAY) // 设置阴影
    }

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, paint) // 关闭硬件加速
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(50f, 50f, 200f, 200f, paint) // 绘制矩形
    }
}