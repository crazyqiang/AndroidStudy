package org.ninetripods.mq.study.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import kotlin.math.cos
import kotlin.math.sin

class XfermodeImgView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        style = Paint.Style.FILL
    }
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) // 显示相交的区域，但图像为DST
    private lateinit var imageBitmap: Bitmap
    private val mRectF = RectF()
    private var mCurShape = SHAPE_CIRCLE

    //边框
    private val mStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 1.dp2px().toFloat()
        color = Color.RED // 矩形颜色无所谓，只需要形状
    }
    private val mPath = Path()

    // 圆角半径
    private var cornerRadius = 50f // 默认圆角半径

    // 设置图片资源
    fun setImageBitmap(bitmap: Bitmap, shape: Int) {
        this.mCurShape = shape
        imageBitmap = bitmap
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mRectF.set(0f, 0f, w.toFloat(), h.toFloat())
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!::imageBitmap.isInitialized) return

        canvas.drawRect(mRectF, mStrokePaint) //绘制边框

        if (mCurShape == SHAPE_ORIGIN) {
            //展示原图
            val scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
            canvas.drawBitmap(scaledBitmap, 0f, 0f, mPaint)
            return
        }

        // 创建一个新的图层，用于混合绘制
        val layerId = canvas.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null)
        drawDSTByCanvas(canvas) //绘制DST
        // 设置混合模式
        mPaint.xfermode = xfermode
        // 绘制源图像
        val scaledBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
        canvas.drawBitmap(scaledBitmap, 0f, 0f, mPaint)
        // 清除混合模式
        mPaint.xfermode = null
        // 恢复图层
        canvas.restoreToCount(layerId)
    }

    private fun drawDSTByCanvas(canvas: Canvas) {
        val halfLength = mRectF.width() / 2
        when (mCurShape) {
            SHAPE_ROUND -> {
                canvas.drawRoundRect(mRectF, cornerRadius, cornerRadius, mPaint)
            }

            SHAPE_ROUND_SUB -> {
                // 定义每个角的圆角半径（左上、右上、右下、左下，每个角2个值：横向半径、纵向半径）
                val radii = floatArrayOf(
                    halfLength, halfLength, // 左上角圆角半径
                    halfLength, halfLength, // 右上角圆角半径
                    0f, 0f, // 右下角圆角半径
                    0f, 0f // 左下角圆角半径
                )
                mPath.reset()
                // 创建 Path，并添加圆角矩形
                mPath.addRoundRect(mRectF, radii, Path.Direction.CW)
                // 使用 Canvas 的 drawDoubleRoundRect 方法绘制
                canvas.drawPath(mPath, mPaint)
            }

            SHAPE_CIRCLE -> {
                canvas.drawCircle(halfLength, halfLength, halfLength, mPaint)
            }

            SHAPE_STAR -> {
                val centerX = mRectF.width() / 2 // 矩形中心 X 坐标
                val centerY = mRectF.height() / 2 // 矩形中心 Y 坐标
                val radiusOuter = mRectF.width() / 2f // 外圆半径（五角星的顶点半径）
                val radiusInner = radiusOuter * 0.5f // 内圆半径（五角星的交点半径）
                // 清空 Path
                mPath.reset()
                // 五角星的顶点数量
                val points = 5
                // 计算每个角度（360°分为 10 个角）
                val angleStep = Math.PI * 2 / (points * 2)
                // 绘制五角星的路径
                for (i in 0 until points * 2) {
                    // 交替使用外圆和内圆的半径
                    val radius = if (i % 2 == 0) radiusOuter else radiusInner
                    // 当前点的角度
                    val angle = angleStep * i - Math.PI / 2 // 起始角度为 -90°（顶点朝上）
                    // 计算点的坐标
                    val x = centerX + (radius * cos(angle)).toFloat()
                    val y = centerY + (radius * sin(angle)).toFloat()

                    if (i == 0) {
                        mPath.moveTo(x, y) // 第一个点移动到起点
                    } else {
                        mPath.lineTo(x, y) // 连接到下一个点
                    }
                }
                mPath.close() // 闭合路径，形成五角星
                canvas.drawPath(mPath, mPaint)
            }

            SHAPE_HEART -> {
                val width = mRectF.width()
                val height = mRectF.height()
                val centerX = mRectF.left + width / 2
                val centerY = mRectF.top + height / 3

                // 心形的上圆弧半径
                val radius = width / 4

                // 左侧圆弧的中心点
                val leftCircleCenterX = centerX - radius
                val leftCircleCenterY = centerY

                // 右侧圆弧的中心点
                val rightCircleCenterX = centerX + radius
                val rightCircleCenterY = centerY

                // 底部点 (心形的尖端)
                val bottomPointX = centerX
                val bottomPointY = mRectF.bottom

                // 起始点 (从左侧圆弧顶部开始)
                val startX = centerX - 2 * radius
                val startY = centerY

                // 开始绘制路径
                mPath.reset()
                mPath.moveTo(startX, startY)
                // 左侧圆弧
                mPath.arcTo(
                    leftCircleCenterX - radius, leftCircleCenterY - radius,
                    leftCircleCenterX + radius, leftCircleCenterY + radius,
                    180f, 180f, false
                )
                // 右侧圆弧
                mPath.arcTo(
                    rightCircleCenterX - radius, rightCircleCenterY - radius,
                    rightCircleCenterX + radius, rightCircleCenterY + radius,
                    180f, 180f, false
                )
                // 连接到底部点
                mPath.lineTo(bottomPointX, bottomPointY)
                // 闭合路径，返回起点
                mPath.close()
                canvas.drawPath(mPath, mPaint)
            }
            SHAPE_LEAF -> {
                // 定义每个角的圆角半径（左上、右上、右下、左下，每个角2个值：横向半径、纵向半径）
                val radii = floatArrayOf(
                    halfLength, halfLength, // 左上角圆角半径
                    0f, 0f, // 右上角圆角半径
                    halfLength, halfLength, // 右下角圆角半径
                    0f, 0f // 左下角圆角半径
                )
                mPath.reset()
                // 创建 Path，并添加圆角矩形
                mPath.addRoundRect(mRectF, radii, Path.Direction.CW)
                // 使用 Canvas 的 drawDoubleRoundRect 方法绘制
                canvas.drawPath(mPath, mPaint)
            }
            SHAPE_TRI -> {

                // 矩形中心点
                val centerX = halfLength
                val centerY = halfLength

                // 顶点坐标
                val topX = centerX
                val topY = mRectF.top

                // 左下角坐标
                val leftX = mRectF.left
                val leftY = mRectF.bottom

                // 右下角坐标
                val rightX = mRectF.right
                val rightY = mRectF.bottom

                // 构建三角形路径
                mPath.reset()
                mPath.moveTo(topX, topY)      // 移动到顶部
                mPath.lineTo(leftX, leftY)    // 绘制左边的线
                mPath.lineTo(rightX, rightY)  // 绘制右边的线
                mPath.close()                 // 闭合路径
                canvas.drawPath(mPath, mPaint)
            }
            SHAPE_DIAMOND -> {
                // 获取矩形的中心点
                val centerX = halfLength
                val centerY = halfLength

                // 计算菱形的四个顶点
                val topX = centerX
                val topY = mRectF.top

                val rightX = mRectF.right
                val rightY = centerY

                val bottomX = centerX
                val bottomY = mRectF.bottom

                val leftX = mRectF.left
                val leftY = centerY

                // 构建菱形路径
                mPath.reset()
                mPath.moveTo(topX, topY)       // 移动到顶部点
                mPath.lineTo(rightX, rightY)   // 绘制右边线
                mPath.lineTo(bottomX, bottomY) // 绘制底部线
                mPath.lineTo(leftX, leftY)     // 绘制左边线
                mPath.close()                  // 闭合路径
                canvas.drawPath(mPath, mPaint)
            }
        }
    }

    companion object {
        const val SHAPE_ORIGIN = 100 // 原图
        const val SHAPE_ROUND = 0 // 圆角矩形
        const val SHAPE_ROUND_SUB = 1 // 不完整的圆角矩形
        const val SHAPE_CIRCLE = 2 // 圆形
        const val SHAPE_STAR = 3 //五角星
        const val SHAPE_HEART = 4 //心形
        const val SHAPE_LEAF = 5 //叶子
        const val SHAPE_TRI = 6 //三角形
        const val SHAPE_DIAMOND = 7 //菱形
    }
}