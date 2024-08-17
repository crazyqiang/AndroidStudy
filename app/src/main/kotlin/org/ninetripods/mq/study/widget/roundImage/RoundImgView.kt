package org.ninetripods.mq.study.widget.roundImage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import org.ninetripods.mq.study.kotlin.ktx.dp2px

/**
 * 通过clipPath的方式实现圆角矩形和圆形图片
 *
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class RoundImgView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        const val SHAPE_ROUND_RECT = 0
        const val SHAPE_CIRCLE = 1
    }

    private val path = Path()
    private val strokePath = Path()
    private var cornerRadius = 10.dp2px().toFloat()
    private var mStrokeWidth = 10.dp2px().toFloat()
    private var mShapeType = SHAPE_ROUND_RECT
    private var isHasStroke = false //是否设置描边

    // 创建一个画笔
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWidth
    }

    override fun onDraw(canvas: Canvas) {
        //canvas.drawColor(Color.RED)
        //设置描边
        if (isHasStroke) {
            processPath(strokePath) //设置path
            canvas.save()
            canvas.drawPath(strokePath, paint)
            canvas.restore()
        }
        processPath(path)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    private fun processPath(path: Path) {
        if (mShapeType == SHAPE_CIRCLE) {
            //圆形
            val radius = (maxOf(width, height) - mStrokeWidth) / 2f
            path.addCircle(width / 2f, height / 2f, radius, Path.Direction.CW)
        } else {
            //圆角矩形
            path.addRoundRect(
                mStrokeWidth, mStrokeWidth,
                width.toFloat() - mStrokeWidth,
                height.toFloat() - mStrokeWidth,
                floatArrayOf(
                    cornerRadius, cornerRadius, cornerRadius, cornerRadius,
                    cornerRadius, cornerRadius, cornerRadius, cornerRadius
                ),
                Path.Direction.CW
            )
        }
    }

    /**
     * 设置描边宽度
     * @param width 宽度
     */
    fun setStrokeWidth(width: Float): RoundImgView {
        this.mStrokeWidth = width
        paint.strokeWidth = mStrokeWidth
        isHasStroke = true
        return this
    }

    /**
     * 设置圆角半径
     * @param radius 半径
     */
    fun setCornerRadius(radius: Float): RoundImgView {
        cornerRadius = radius
        return this
    }

    /**
     * 设置图片类型
     * @param type
     */
    fun setShapeType(type: Int): RoundImgView {
        this.mShapeType = type
        return this
    }

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
    }
}