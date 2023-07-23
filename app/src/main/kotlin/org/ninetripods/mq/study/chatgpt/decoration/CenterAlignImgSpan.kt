package org.ninetripods.mq.study.chatgpt.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * Created by mq on 2023/6/15
 */
class CenterAlignImgSpan(d: Drawable, align: Int = ALIGN_BASELINE) : ImageSpan(d, align) {

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint,
    ) {
//        super.draw(canvas, text, start, end, x, top, y, bottom, paint)
        val d = drawable
        val fm = paint.fontMetricsInt
        val tranY = (y + fm.descent + y + fm.ascent) / 2 - d.bounds.bottom / 2
        canvas.save()
        canvas.translate(x, tranY.toFloat())
        d.draw(canvas)
        canvas.restore()
    }
}