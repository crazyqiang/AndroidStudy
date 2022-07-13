package org.ninetripods.mq.study.kotlin.flow.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.sp2px
import kotlin.math.min

/**
 * 跳绳倒计时CircleView
 */
class CountDownCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private val mPaint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 8.dp2px().toFloat()
        color = Color.parseColor("#F7F9FA")
    }

    private val mCirclePaint: Paint = Paint().apply {
        isAntiAlias = true
        isDither = true
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val mTextPaint: TextPaint = TextPaint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.parseColor("#A1EA42")
        textAlign = Paint.Align.CENTER //绘制方向 居中绘制
        textSize = 50.sp2px().toFloat()
        typeface = Typeface.DEFAULT_BOLD
    }
    private val mRect = RectF()
    private var mCenterX: Float = 0f
    private var mCenterY: Float = 0f
    private var mRadius: Float = 0f
    private var mMinWH: Float = 0f
    private val colorArr = intArrayOf(Color.parseColor("#BAF900"), Color.parseColor("#84F000"))

    //设置渐变色
    private val mLinearShader =
        LinearGradient(0f, 0f, mMinWH, mMinWH, colorArr, null, Shader.TileMode.MIRROR)
    private var mMaxCount: Int = 0 //最大数
    private var mSweepAngle: Float = 360f //扫描过的度数
    private var mCountDown: Job? = null
    private var mText = "-"

    /**
     * 开始倒计数
     */
    fun startCountDown(count: Int, finishFuc: () -> Unit) {
        if (context !is FragmentActivity) return
        this.mMaxCount = count
        mCountDown = countDownByFlow(mMaxCount, (context as FragmentActivity).lifecycleScope,
            onTick = {
                if (it == 0) mCountDown?.cancel()
                mText = it.toString()
                mSweepAngle = (it / mMaxCount.toFloat()) * 360
                invalidate()
            }, onFinish = {
                finishFuc.invoke()
            })
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mCenterX = (w / 2).toFloat()
        mCenterY = (h / 2).toFloat()
        mMinWH = min(w, h).toFloat()
        mRadius = (mMinWH - mPaint.strokeWidth) / 2
        //设置矩形范围
        val strokeHalf = mPaint.strokeWidth / 2
        mRect.set(strokeHalf, strokeHalf, mMinWH - strokeHalf, mMinWH - strokeHalf)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            mPaint.shader = null
            //绘制白色背景圆
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mCirclePaint)
            //绘制灰色背景圆
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint)
            //绘制渐变色弧形 从12点方向开始绘制
            mPaint.shader = mLinearShader
            canvas.drawArc(mRect, -90f, mSweepAngle, false, mPaint)

            //绘制中间倒计时数字
            //如果设置的 Align = LEFT，那么baseX = (mMinWH - mTextPaint.measureText(mText)) / 2
            val baseX = mCenterX
            // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
            val baseY =
                (mCenterY - (mTextPaint.descent() + mTextPaint.ascent()) / 2).toInt()
            // 居中画一个文字
            canvas.drawText(mText, baseX, baseY.toFloat(), mTextPaint)
        }
    }

    /**
     * 使用Flow实现一个倒计时功能
     */
    private fun countDownByFlow(
        max: Int,
        scope: CoroutineScope,
        onTick: (Int) -> Unit,
        onFinish: (() -> Unit)? = null,
    ): Job {
        return flow {
            for (num in max downTo 0) {
                emit(num)
                if (num != 0) delay(1000)
            }
        }.flowOn(Dispatchers.Main)
            .onEach { onTick.invoke(it) }
            .onCompletion { cause -> if (cause == null) onFinish?.invoke() }
            .launchIn(scope) //保证在一个协程中执行
    }

}