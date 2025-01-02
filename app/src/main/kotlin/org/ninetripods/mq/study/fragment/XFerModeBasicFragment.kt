package org.ninetripods.mq.study.fragment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.util.AttributeSet
import android.view.View
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment

/**
 * Xfermode基础效果
 */
class XFerModeBasicFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_xfermode_base
    }
}

class XfermodeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint
    internal var mItemSize = 0f
    internal var mItemHorizontalOffset = 0f
    internal var mItemVerticalOffset = 0f
    internal var mCircleRadius = 0f
    internal var mRectSize = 0f
    internal var mCircleColor = -0x33bc//黄色
    internal var mRectColor = -0x995501//蓝色
    internal var mTextSize = 25f

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = mTextSize
            textAlign = Paint.Align.CENTER
            strokeWidth = 2f
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mItemSize = w / 4.5f
        mItemHorizontalOffset = mItemSize / 6
        mItemVerticalOffset = mItemSize * 0.426f
        mCircleRadius = mItemSize / 3
        mRectSize = mItemSize * 0.6f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置背景色
        //canvas.drawARGB(255, 139, 197, 186);

        for (row in 0..3) {
            for (column in 0..3) {
                canvas.save()
                //保存当前图层
                val layer = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
                mPaint.xfermode = null
                val index = row * 4 + column
                val translateX = (mItemSize + mItemHorizontalOffset) * column
                val translateY = (mItemSize + mItemVerticalOffset) * row
                canvas.translate(translateX, translateY)

                //画文字
                val text = sLabels[index]
                mPaint.color = Color.BLACK
                val textXOffset = mItemSize / 2
                val textYOffset = mTextSize + (mItemVerticalOffset - mTextSize) / 2
                canvas.drawText(text, textXOffset, textYOffset, mPaint)
                canvas.translate(0f, mItemVerticalOffset)

                //画边框
                mPaint.style = Paint.Style.STROKE
                mPaint.color = -0x1000000
                canvas.drawRect(2f, 2f, mItemSize - 2, mItemSize - 2, mPaint)
                mPaint.style = Paint.Style.FILL

                //画圆
                mPaint.color = mCircleColor
                val left = mCircleRadius + 3
                val top = mCircleRadius + 3
                canvas.drawCircle(left, top, mCircleRadius, mPaint)
                mPaint.xfermode = sModes[index]

                //画矩形
                mPaint.color = mRectColor
                val rectRight = mCircleRadius + mRectSize
                val rectBottom = mCircleRadius + mRectSize
                canvas.drawRect(left, top, rectRight, rectBottom, mPaint)
                mPaint.xfermode = null
                canvas.restoreToCount(layer)
            }
        }
    }

    companion object {
        private val sModes = arrayOf<Xfermode>(
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),  // 清空所有，要闭硬件加速，否则无效
            PorterDuffXfermode(PorterDuff.Mode.SRC),  // 显示前者图像，不显示后者
            PorterDuffXfermode(PorterDuff.Mode.DST),  // 显示后者图像，不显示前者
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),  // 后者叠于前者
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),  // 前者叠于后者
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),  // 显示相交的区域，但图像为后者
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),  // 显示相交的区域，但图像为前者
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),  // 显示后者不重叠的图像
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),  // 显示前者不重叠的图像
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),  // 显示前者图像，与后者重合的图像
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),  // 显示后者图像，与前者重合的图像
            PorterDuffXfermode(PorterDuff.Mode.XOR),  // 显示持有不重合的图像
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),  // 后者叠于前者上，后者与前者重叠的部份透明。要闭硬件加速，否则无效
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),  // 前者叠于前者，前者与后者重叠部份透明。要闭硬件加速，否则无效
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),  // 显示重合的图像，且颜色会合并
            PorterDuffXfermode(PorterDuff.Mode.SCREEN) // 显示持有图像，重合的会变白
        )

        private val sLabels = arrayOf(
            "Clear",
            "Src",
            "Dst",
            "SrcOver",
            "DstOver",
            "SrcIn",
            "DstIn",
            "SrcOut",
            "DstOut",
            "SrcATop",
            "DstATop",
            "Xor",
            "Darken",
            "Lighten",
            "Multiply",
            "Screen"
        )
    }
}