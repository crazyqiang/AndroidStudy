package org.ninetripods.mq.study.span

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.style.LeadingMarginSpan.LeadingMarginSpan2
import org.ninetripods.mq.study.kotlin.ktx.dp2px


/**
 * Created by mq on 2023/8/1
 * @param lineCount 行数
 * @param mFirst 段落前N行margin 单位dp
 * @param mRest 段落剩余行margin 单位dp
 */
class TextAroundSpan(
    private var imgInfo: ImgInfo,
    private val lineCount: Int,
    private val mFirst: Int,
    private val mRest: Int = 0,
) :
    LeadingMarginSpan2 {


    /**
     * 段落缩进的行数
     */
    override fun getLeadingMarginLineCount(): Int = lineCount

    /**
     * @param first true作用于段落中前N行（N为getLeadingMarginLineCount()中返回的值），否则作用于段落剩余行
     */
    override fun getLeadingMargin(first: Boolean): Int =
        if (first) mFirst.dp2px() else mRest.dp2px()


    /**
     * 绘制页边距（leading margin）。在{@link #getLeadingMargin(boolean)}返回值调整页边距之前调用。
     *
     * @param canvas the canvas
     * @param paint the paint. The this should be left unchanged on exit.
     * @param x the current position of the margin
     * @param dir the base direction of the paragraph; if negative, the margin
     * is to the right of the text, otherwise it is to the left.
     * @param top the top of the line
     * @param baseline the baseline of the line
     * @param bottom the bottom of the line
     * @param text the text
     * @param start the start of the line
     * @param end the end of the line
     * @param first true if this is the first line of its paragraph
     * @param layout the layout containing this line
     */
    override fun drawLeadingMargin(
        canvas: Canvas?,
        paint: Paint?,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence?,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout?,
    ) {
        if (canvas == null || paint == null) return
        val drawable: Drawable = imgInfo.drawable
        canvas.save()
        drawable.setBounds(0, 0, imgInfo.width, imgInfo.height)
        canvas.translate(imgInfo.dx, imgInfo.dy)
        drawable.draw(canvas)
        canvas.restore()
    }

    data class ImgInfo(
        val drawable: Drawable,
        val width: Int,
        val height: Int,
        val dx: Float = 1.dp2px().toFloat(),
        val dy: Float = 2.dp2px().toFloat(),
    )

}