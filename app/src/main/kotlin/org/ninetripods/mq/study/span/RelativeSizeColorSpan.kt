package org.ninetripods.mq.study.span

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.RelativeSizeSpan
import androidx.annotation.ColorInt

/**
 * 自定义Span
 * @param size : 文本被缩放的比例
 */
class RelativeSizeColorSpan(size: Float, @ColorInt val color: Int = Color.GRAY) :
    RelativeSizeSpan(size) {

    /**
     * 在绘制文本时更新样式的绘制状态，例如文本颜色、下划线等。
     */
    override fun updateDrawState(paint: TextPaint) {
        super.updateDrawState(paint)
        paint.color = color
        paint.isUnderlineText = true
    }

    /**
     *在测量文本时更新样式的测量状态，例如文本大小、字体等。
     */
    override fun updateMeasureState(paint: TextPaint) {
        super.updateMeasureState(paint)
        paint.typeface = Typeface.DEFAULT_BOLD

    }
}