package org.ninetripods.mq.study.widget.xfermode

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.sp2px

class EraseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG) // 防锯齿画笔
    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG) // 防锯齿画笔
    private var mPath = Path() // 擦除路径

    // 源图像
    private lateinit var srcBitmap: Bitmap
    // 目标图层
    private lateinit var dstBitmap: Bitmap

    private var showText = "" //文案
    private var isShowResultDirect = false //是否直接展示结果

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) //关闭硬件加速器
        mTextPaint.apply {
            color = Color.RED // 设置擦除画笔为红色
            style = Paint.Style.FILL // 设置画笔样式为描边
            strokeWidth = 50f // 设置画笔粗细
            strokeCap = Paint.Cap.ROUND // 设置画笔端点为圆形
            strokeJoin = Paint.Join.ROUND // 设置拐角为圆形
            textSize = 24.sp2px().toFloat()
        }
        mPaint.apply {
            color = Color.RED // 设置擦除画笔为红色
            style = Paint.Style.STROKE // 设置画笔样式为描边
            strokeWidth = 20.dp2px().toFloat() // 设置画笔粗细
            strokeCap = Paint.Cap.ROUND // 设置画笔端点为圆形
            strokeJoin = Paint.Join.ROUND // 设置拐角为圆形
        }
        showText = getRandomStr()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 初始化图片资源
        val originBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_guaguaka)
        srcBitmap = Bitmap.createScaledBitmap(originBitmap, w, h, false)
        dstBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!this::srcBitmap.isInitialized || !this::dstBitmap.isInitialized) return

        kotlin.runCatching {
            canvas.drawColor(Color.parseColor("#BBBBBB"))
        }
        val textWidth = mTextPaint.measureText(showText) //文本宽度
        val fontMetrics = mTextPaint.fontMetrics
        val baselineOffset = (fontMetrics.bottom + fontMetrics.top) / 2
        //显示的文案居中绘制
        canvas.drawText(showText, (width - textWidth) / 2f, height / 2f - baselineOffset, mTextPaint)

        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        // 1. 在画布上绘制擦除路径
        canvas.drawPath(mPath, mPaint)
        // 2. 绘制背景图像（目标图像）
        canvas.drawBitmap(dstBitmap, 0f, 0f, mPaint)
        // 3. 手动擦除，设置混合模式为 SRC_OUT，实现擦除效果；直接展示结果，用DST_OUT
        mPaint.xfermode = PorterDuffXfermode(if (isShowResultDirect) PorterDuff.Mode.DST_OUT else PorterDuff.Mode.SRC_OUT)
        canvas.drawBitmap(srcBitmap, 0f, 0f, mPaint)
        mPaint.xfermode = null
        canvas.restoreToCount(layerId)
    }

    /**
     * 重置状态，再刮一次
     */
    fun resetState() {
        mPath.reset()
        isShowResultDirect = false
        showText = getRandomStr()
        invalidate()
    }

    /**
     * 直接展示结果
     */
    fun showResultDirect() {
        isShowResultDirect = true
        invalidate()
    }

    /**
     * 重置文案
     */
    private fun getRandomStr(): String {
        return listOf("谢谢惠顾", "特等奖", "一等奖", "二等奖", "三等奖").random()
    }

    // 处理触摸事件，动态更新擦除路径
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // 请求父视图不要拦截事件
                parent.requestDisallowInterceptTouchEvent(true)
                mPath.moveTo(x, y) // 路径起点
            }

            MotionEvent.ACTION_MOVE -> {
                mPath.lineTo(x, y) // 路径延续
                invalidate() // 重新绘制
            }

            MotionEvent.ACTION_UP -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return true
    }
}