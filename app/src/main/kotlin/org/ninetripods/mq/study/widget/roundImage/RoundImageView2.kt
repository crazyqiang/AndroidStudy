package org.ninetripods.mq.study.widget.roundImage

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class RoundImageView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
    private lateinit var imageBitmap: Bitmap

    // 圆角半径
    private var cornerRadius = 50f // 默认圆角半径

    // 设置图片资源
    fun setImageBitmap(bitmap: Bitmap) {
        imageBitmap = bitmap
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!::imageBitmap.isInitialized) return

        // 创建一个新的图层，用于混合绘制
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)

        // 绘制一个圆角矩形作为目标图像
        val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())
        paint.color = Color.RED // 矩形颜色无所谓，只需要形状
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint)

        // 设置混合模式
        paint.xfermode = xfermode

        // 绘制源图像
        val scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, width/2, height/2, false)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, paint)
        // 绘制源图像
        val scaledBitmap1 = Bitmap.createScaledBitmap(imageBitmap, width/2, height/2, false)
        canvas.drawBitmap(scaledBitmap1, width/2f, 0f, paint)

        // 清除混合模式
        paint.xfermode = null

        // 恢复图层
        canvas.restoreToCount(layerId)
    }
}