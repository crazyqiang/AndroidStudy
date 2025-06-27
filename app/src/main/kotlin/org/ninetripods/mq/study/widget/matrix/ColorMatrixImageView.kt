package org.ninetripods.mq.study.widget.matrix

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.kotlin.ktx.log

class ColorMatrixImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var brightness = 1.0f // 默认亮度
    private var saturation = 1.0f // 默认饱和度

    //防抖延迟更新
    private var mJob: Job? = null
    private val mScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        scaleType = ScaleType.CENTER_CROP
    }

    /**
     * 设置亮度值（例如：0.5f ~ 2.0f）
     */
    fun setBrightness(brightness: Float) {
        this.brightness = brightness
        scheduleUpdate()
    }

    /**
     * 设置饱和度 [0f ~ 2f]
     */
    fun setSaturation(value: Float) {
        this.saturation = value
        scheduleUpdate()
    }

    /**
     * @param brightness
     * @param saturation
     */
    fun setAdjustments(brightness: Float, saturation: Float) {
        this.saturation = saturation
        this.brightness = brightness
        scheduleUpdate()
    }

    //避免频繁更新
    private fun scheduleUpdate(delayMs: Long = 16L) {
        mJob?.cancel()
        mJob = mScope.launch {
            delay(delayMs)
            applyAdjustments()
        }
    }

    private fun applyAdjustments() {
        val combineMatrix = ColorMatrix()
        val saturationMatrix = ColorMatrix()
        //饱和度矩阵
        saturationMatrix.setSaturation(saturation)
        /**
         * 亮度矩阵，也可以通过matrix.setScale(brightness, brightness, brightness, 1f)来实现
         */
        val contrastMatrix = ColorMatrix(
            floatArrayOf(
                brightness, 0f, 0f, 0f, 0f,
                0f, brightness, 0f, 0f, 0f,
                0f, 0f, brightness, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )
        )
        //postConcat 合并矩阵
        combineMatrix.postConcat(saturationMatrix)
        combineMatrix.postConcat(contrastMatrix)
        log("brightness:$brightness,saturation:$saturation")
        colorFilter = ColorMatrixColorFilter(combineMatrix)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mScope.cancel()
    }
}


//    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
//        (drawable as? BitmapDrawable)?.bitmap?.let {
//            // 绘制带亮度调整的图片
//            canvas.drawBitmap(it, imageMatrix, paint)
//        }
//        val matrix = Matrix()
//        //matrix.setRotate(-45, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
//        matrix.setScale(0.5f, 0.5f)
//        canvas.save()
//        canvas.concat(matrix)
//        canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
//        canvas.restore()
//    }
