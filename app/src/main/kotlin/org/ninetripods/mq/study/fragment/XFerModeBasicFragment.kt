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
        setLayerType(LAYER_TYPE_SOFTWARE, null) //关闭硬件加速器
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

        for (row in 0..4) {
            for (column in 0..3) {
                if (row == 4 && column in 2..3) return
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

                //1、画圆(DST)
                mPaint.color = mCircleColor
                val left = mCircleRadius + 3
                val top = mCircleRadius + 3
                canvas.drawCircle(left, top, mCircleRadius, mPaint)
                //2、设置xfermode
                mPaint.xfermode = sModes[index]
                //3、画矩形(SRC)
                mPaint.color = mRectColor
                val rectRight = mCircleRadius + mRectSize
                val rectBottom = mCircleRadius + mRectSize
                canvas.drawRect(left, top, rectRight, rectBottom, mPaint)
                //4、清空xfermode
                mPaint.xfermode = null
                canvas.restoreToCount(layer)
            }
        }
    }

    companion object {
        /**
         * NOTE：先绘制DST(下层)  后绘制SRC(上层)
         */
        private val sModes = arrayOf<Xfermode>(
            //Alpha合成效果：
            /**
             * IN：取指定层的交集部分，如：SRC_IN 表示取交集部分的SRC图像
             * OUT：取指定层的非交集部分，如：DST_OUT 表示取非交集的DST图像部分
             * OVER：取指定层在上层显示，如：SRC_OVER表示SRC图像覆盖DST图像
             * XOR：取上下两层的非交集部分显示
             * ATOP：取指定层的交集部分和非指定层的非交集部分，如： DST_ATOP表示取交集部分的DST + 非交集部分的SRC
             */
            PorterDuffXfermode(PorterDuff.Mode.CLEAR),  // DST区域被清空，不绘制任何东西，要闭硬件加速，否则无效
            PorterDuffXfermode(PorterDuff.Mode.SRC),  // 显示SRC图像，完全覆盖DST图像
            PorterDuffXfermode(PorterDuff.Mode.DST),  // 显示DST图像，不显示SRC图像。
            PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),  // SRC图像覆盖DST图像
            PorterDuffXfermode(PorterDuff.Mode.DST_OVER),  // DST图像覆盖SRC图像
            PorterDuffXfermode(PorterDuff.Mode.SRC_IN),  // 取交集部分的SRC图像
            PorterDuffXfermode(PorterDuff.Mode.DST_IN),  // 取交集部分的DST图像
            PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),  // SRC图像的非交集区域显示，DST图像被移除
            PorterDuffXfermode(PorterDuff.Mode.DST_OUT),  // DST图像的非交集区域显示，SRC图像被移除
            PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),  //取交集部分的SRC + 非交集部分的DST
            PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),  //取交集部分的DST + 非交集部分的SRC
            PorterDuffXfermode(PorterDuff.Mode.XOR),  // 显示SRC图像与DST图像不重叠的部分，交集区域被移除。
            //混合特效：注意要闭硬件加速，否则无效
            PorterDuffXfermode(PorterDuff.Mode.DARKEN),  // 相交区域变暗。
            PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),  // 相交区域变亮。
            PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),  // 显示重合的图像，SRC图像和DST图像的颜色值相乘，颜色合并
            PorterDuffXfermode(PorterDuff.Mode.SCREEN), // SRC图像和DST图像的颜色值叠加，得到更亮的颜色
            PorterDuffXfermode(PorterDuff.Mode.ADD), //饱和度叠加，SRC图像和DST图像的颜色值相加，颜色变得更亮
            PorterDuffXfermode(PorterDuff.Mode.OVERLAY), //结合 MULTIPLY 和 SCREEN 模式，根据背景的亮度进行混合
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
            "Screen",
            "Add",
            "Overlay"
        )
    }
}