package org.ninetripods.mq.study.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.sp2px

/**
 * 自定义Tag Span
 * Created by mq on 2023/7/31
 *
 * @property tagColor tag外框颜色
 * @property tagRadius tag圆角半径
 * @property tagStrokeWidth tag外框宽度
 * @property tagMarginLeft tag外框左侧的margin
 * @property tagMarginRight tag外框右侧的margin
 * @property tagPadding tag内侧文字padding
 * @property txtSize 文字大小
 * @property txtColor 文字颜色
 */
class TagSpan(
    private val tagColor: Int = Color.RED,
    private val tagRadius: Float = 2.dp2px().toFloat(),
    private val tagStrokeWidth: Float = 1.dp2px().toFloat(),
    private val tagMarginLeft: Float = 0.dp2px().toFloat(),
    private val tagMarginRight: Float = 5.dp2px().toFloat(),
    private val tagPadding: Float = 2.dp2px().toFloat(),
    private val txtSize: Float = 14.sp2px().toFloat(),
    private val txtColor: Int = Color.RED,
) : ReplacementSpan() {

    private var mSpanWidth = 0 //包含了Span文字左右间距在内的宽度

    /**
     * 返回Span的宽度。子类可以通过更新Paint.FontMetricsInt的属性来设置Span的高度。
     * 如果Span覆盖了整个文本，并且高度没有设置，那么draw(Canvas, CharSequence, int, int, float, int, int, int, Paint)方法将不会调用。
     *
     * @param paint Paint画笔
     * @param text 当前文本
     * @param start Span开始索引
     * @param end Span结束索引
     * @param fm Paint.FontMetricsInt，可能是空
     * @return 返回Span的宽度
     */
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?,
    ): Int {
        if (text.isNullOrEmpty()) return 0
        paint.textSize = txtSize
        //测量包含了Span文字左右间距在内的宽度
        mSpanWidth = (paint.measureText(text, start, end) + getTxtLeftW() + getTxtRightW()).toInt()
        log("getSize-> text:$text,start:$start,end：$end,fm:$fm,mSpanWidth:$mSpanWidth")
        return mSpanWidth
    }

    /**
     * 将Span绘制到Canvas中
     *
     * @param canvas Canvas画布
     * @param text 当前文本
     * @param start Span开始索引
     * @param end Span结束索引
     * @param x Edge of the replacement closest to the leading margin.
     * @param top 行文字显示区域的Top
     * @param y Baseline基线
     * @param bottom 行文字显示区域的Bottom  当在XML中设置lineSpacingExtra时，这里也会受影响
     * @param paint Paint画笔
     */
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
        log("draw -> text:$text,start:$start,end:$end,x:$x,top:$top,y:$y,bottom:$bottom")
        if (text.isNullOrEmpty()) return
        paint.run {
            color = tagColor
            isAntiAlias = true
            isDither = true
            style = Paint.Style.STROKE
            strokeWidth = tagStrokeWidth
        }
        //文字高度
        val txtHeight = paint.fontMetricsInt.descent - paint.fontMetricsInt.ascent
        //1、绘制标签
        val tagRect = RectF(
            x + getTagLeft(), top.toFloat(),
            x + mSpanWidth - tagMarginRight, (top + txtHeight).toFloat()
        )
        canvas.drawRoundRect(tagRect, tagRadius, tagRadius, paint)

        //2、绘制文字
        paint.run {
            color = txtColor
            style = Paint.Style.FILL
        }
        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        val baseY = tagRect.height() / 2 - (paint.descent() + paint.ascent()) / 2
        //绘制标签内文字
        canvas.drawText(text, start, end, x + getTxtLeftW(), baseY, paint)
    }

    private fun getTagLeft(): Float {
        return tagMarginLeft + tagStrokeWidth
    }

    /**
     * Span文字左侧所有的间距
     */
    private fun getTxtLeftW(): Float {
        return tagPadding + tagMarginLeft + tagStrokeWidth
    }

    /**
     * Span文字右侧所有的间距
     */
    private fun getTxtRightW(): Float {
        return tagPadding + tagMarginRight + tagStrokeWidth
    }
}