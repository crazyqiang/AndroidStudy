package org.ninetripods.mq.study.span

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan
import java.lang.ref.WeakReference

/**
 * Created by mq on 2023/7/2
 */
class CenterImageSpan(drawable: Drawable, verticalAlignment: Int = ALIGN_BASELINE) :
    ImageSpan(drawable, verticalAlignment) {

    private var mDrawableRef: WeakReference<Drawable>? = null

    /**
     * Returns the width of the span. Extending classes can set the height of the span by updating
     * attributes of {@link android.graphics.Paint.FontMetricsInt}. If the span covers the whole
     * text, and the height is not set,
     * {@link #draw(Canvas, CharSequence, int, int, float, int, int, int, Paint)} will not be
     * called for the span.
     *
     * @param paint Paint instance.
     * @param text Current text.
     * @param start Start character index for span.
     * @param end End character index for span.
     * @param fm Font metrics, can be null.
     * @return Width of the span.
     */
    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?,
    ): Int {
        val d = getCachedDrawable()
        val rect = d?.bounds
        rect?.let {
            if (fm != null) {
                val fmInt = paint.fontMetricsInt
                val imgHeight = d.bounds.height() //图片高度
                val textHeight = fm.bottom - fm.top //文字行高度
                val halfDiffer = (imgHeight - textHeight) / 2
                if (imgHeight > textHeight) {
                    //图片大于文字 且居中排版
                    fm.ascent = fmInt.ascent - halfDiffer
                    fm.descent = fmInt.descent + halfDiffer
                    fm.top = fmInt.top - halfDiffer
                    fm.bottom = fmInt.bottom + halfDiffer
                } else {
                    fm.ascent = -rect.bottom
                    fm.descent = 0
                    fm.top = fm.ascent
                    fm.bottom = 0
                }
            }
            return rect.right
        }
        return 0
    }

    /**
     * Draws the span into the canvas.
     *
     * @param canvas Canvas into which the span should be rendered.
     * @param text Current text.
     * @param start Start character index for span.
     * @param end End character index for span.
     * @param x Edge of the replacement closest to the leading margin.
     * @param top Top of the line.
     * @param y Baseline.
     * @param bottom Bottom of the line.
     * @param paint Paint instance.
     */
//    override fun draw(
//        canvas: Canvas,
//        text: CharSequence?,
//        start: Int,
//        end: Int,
//        x: Float,
//        top: Int,
//        y: Int,
//        bottom: Int,
//        paint: Paint,
//    ) {
////        super.draw(canvas, text, start, end, x, top, y, bottom, paint)
//        val b: Drawable? = getCachedDrawable()
//        canvas.save()
//        b?.let {
//            var transY = bottom - b.bounds.bottom
//            if (mVerticalAlignment == DynamicDrawableSpan.ALIGN_BASELINE) {
//                transY -= paint.fontMetricsInt.descent
//            } else if (mVerticalAlignment == DynamicDrawableSpan.ALIGN_CENTER) {
//                transY = top + (bottom - top) / 2 - b.bounds.height() / 2
//                val fm = paint.fontMetricsInt
//                //y is baseline
//                //transY = (y + fm.descent + y + fm.ascent) / 2 - b.bounds.height() / 2
//            }
//            canvas.translate(x, transY.toFloat())
//            b.draw(canvas)
//            canvas.restore()
//        }
//    }

    private fun getCachedDrawable(): Drawable? {
        val wr: WeakReference<Drawable>? = mDrawableRef
        var d: Drawable? = null
        if (wr != null) {
            d = wr.get()
        }
        if (d == null) {
            d = drawable
            mDrawableRef = WeakReference<Drawable>(d)
        }
        return d
    }
}