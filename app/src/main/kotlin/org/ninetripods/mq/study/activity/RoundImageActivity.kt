package org.ninetripods.mq.study.activity

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.clipToRoundView
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.roundImage.RoundImgView

/**
 * 给图片设置圆角
 *
 */
class RoundImageActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mIvTarget: AppCompatImageView by id(R.id.iv_round_img)
    private val mIvTarget2: AppCompatImageView by id(R.id.iv_round_img2)
    private val mIvGlide1: AppCompatImageView by id(R.id.iv_glide1)
    private val mIvGlide2: AppCompatImageView by id(R.id.iv_glide2)
    private val mIvClipPath: RoundImgView by id(R.id.iv_custom_img)
    private val mIvClipPath2: RoundImgView by id(R.id.iv_custom_img2)
    private val mIvBitmapShader: AppCompatImageView by id(R.id.iv_bitmap_shader)
    private val mIvBitmapDrawable: AppCompatImageView by id(R.id.iv_bitmap_drawable)

    //ImageFilterView使用
    private val mIvFilterView1: ImageFilterView by id(R.id.iv_filter_view1)
    private val mIvFilterView2: ImageFilterView by id(R.id.iv_filter_view2)

    //ShapeableImageView使用
    private val mIvShapeAble5: ShapeableImageView by id(R.id.iv_shapeAble_view5)
    private val mIvShapeAble6: ShapeableImageView by id(R.id.iv_shapeAble_view6)
    private val mIvShapeAble7: ShapeableImageView by id(R.id.iv_shapeAble_view7)
    private val mIvShapeAble8: ShapeableImageView by id(R.id.iv_shapeAble_view8)


    override fun setContentView() {
        setContentView(R.layout.activity_image_round)
    }

    override fun initEvents() {
        initToolBar(mToolBar, "图片设置圆角", true, false)
        processRoundImage()
    }

    private fun processRoundImage() {
        /**
         * 方式一：ViewOutlineProvider可以设置圆角矩形、椭圆、圆形等
         */
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        mIvTarget.clipToRoundView(RoundImgView.SHAPE_ROUND_RECT)
        mIvTarget.setImageBitmap(bitmap)

        mIvTarget2.clipToRoundView(RoundImgView.SHAPE_CIRCLE)
        mIvTarget2.setImageBitmap(bitmap)

        /**
         * 方式二：Glide
         */
        //1、图片设置的不是CenterCrop
        Glide.with(this)
            .load(R.drawable.icon_cat_w)
            .transform(RoundedCorners(16.dp2px()))
            .into(mIvGlide1)

        //2、如果图片设置的是CenterCrop，则需要用下面的方式处理CenterCrop与圆角矩形冲突问题
        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(16.dp2px()))

        Glide.with(this)
            .load(R.drawable.icon_cat_w)
            .apply(requestOptions)
            .into(mIvGlide2)

        /**
         * 方式三：Canvas.clipPath()
         */
        val bitmap1 = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        mIvClipPath.setCornerRadius(15.dp2px().toFloat())
            .setShapeType(RoundImgView.SHAPE_ROUND_RECT)
            .setStrokeWidth(10f)
            .setImageBitmap(bitmap1)

        mIvClipPath2.setCornerRadius(15.dp2px().toFloat())
            .setShapeType(RoundImgView.SHAPE_CIRCLE)
            .setStrokeWidth(10f)
            .setImageBitmap(bitmap1)

        /**
         * 方式四：CardView
         */

        /**
         * 方式五：BitmapShader
         */
        val bitmap3 = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        mIvBitmapShader.post {
            val targetBitmap =
                getBitmapByShader(bitmap3, 200.dp2px(), 200.dp2px(), 20.dp2px(), 5.dp2px(), RoundImgView.SHAPE_ROUND_RECT)
            mIvBitmapShader.setImageBitmap(targetBitmap)
        }
        val roundDrawable = RoundDrawable(bitmap3, 200.dp2px().toFloat())
        mIvBitmapShader.setImageDrawable(roundDrawable)

        /**
         * 方式六：RoundedBitmapDrawable
         */
        val bitmap4 = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        val roundBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap4).apply {
            paint.isAntiAlias = true
            cornerRadius = 20.dp2px().toFloat()
        }
        mIvBitmapDrawable.setImageDrawable(roundBitmapDrawable)

        /**
         * 方式7：ImageFilterView
         */
        val bitmap5 = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        val bitmap6 = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_w)
        mIvFilterView1.run {
            round = 10.dp2px().toFloat()
            setImageBitmap(bitmap5)
        }

        mIvFilterView2.run {
            roundPercent = 1f
            setImageBitmap(bitmap6)
        }

        /**
         * 方式8：ShapeableImageView
         */
        // 代码设置圆角 圆角矩形等效果
        val shapeAppearanceModel5 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(20f)
            .build()
        mIvShapeAble5.shapeAppearanceModel = shapeAppearanceModel5

        val shapeAppearanceModel6 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()
        mIvShapeAble6.shapeAppearanceModel = shapeAppearanceModel6

        val shapeAppearanceModel7 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(20f)
            .build()
        mIvShapeAble7.run {
            shapeAppearanceModel = shapeAppearanceModel7
            strokeWidth = 4.dp2px().toFloat()
            strokeColor = ColorStateList.valueOf(Color.RED)
            setPadding(2.dp2px(), 2.dp2px(), 2.dp2px(), 2.dp2px())
        }

        val shapeAppearanceModel8 = ShapeAppearanceModel.builder()
            .setAllCorners(RoundedCornerTreatment())
            .setAllCornerSizes(RelativeCornerSize(0.5f))
            .build()
        mIvShapeAble8.run {
            shapeAppearanceModel = shapeAppearanceModel8
            strokeWidth = 4.dp2px().toFloat()
            strokeColor = ColorStateList.valueOf(Color.RED)
            setPadding(2.dp2px(), 2.dp2px(), 2.dp2px(), 2.dp2px())
        }
    }


    /**
     * @param bitmap  bitmap
     * @param outWidth 输出的宽
     * @param outHeight 输出的高
     * @param radius 半径
     * @param border 描边
     * @param shapeType 图形类型：圆角矩形 or 圆形
     */
    private fun getBitmapByShader(
        bitmap: Bitmap?,
        outWidth: Int,
        outHeight: Int,
        radius: Int,
        border: Int,
        shapeType: Int = RoundImgView.SHAPE_ROUND_RECT
    ): Bitmap? {
        if (bitmap == null || bitmap.height <= 0 || bitmap.width <= 0) {
            return null
        }
        //缩放比例
        val scale = minOf(outWidth.toFloat() / bitmap.width, outHeight.toFloat() / bitmap.height)
        //创建矩阵
        val matrix = Matrix().apply {
            setScale(scale, scale)
        }
        //创建shader
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).apply {
            setLocalMatrix(matrix)
        }
        //通过shader着色器来绘制图像
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.shader = shader
        }

        return Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888).also { output ->
            Canvas(output).apply {
                val rect = RectF(
                    border.toFloat(),
                    border.toFloat(),
                    (outWidth - border).toFloat(),
                    (outHeight - border).toFloat()
                )
                if (shapeType == RoundImgView.SHAPE_ROUND_RECT) {
                    //绘制圆角矩形
                    drawRoundRect(rect, radius.toFloat(), radius.toFloat(), paint)
                } else {
                    //绘制圆形
                    drawCircle(outWidth / 2f, outHeight / 2f, outWidth / 2f, paint)
                }

                if (border > 0) {
                    //如果有描边，绘制描边
                    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                        color = Color.RED
                        style = Paint.Style.STROKE
                        strokeWidth = border.toFloat()
                    }
                    if (shapeType == RoundImgView.SHAPE_ROUND_RECT) {
                        //绘制圆角矩形
                        drawRoundRect(rect, radius.toFloat(), radius.toFloat(), strokePaint)
                    } else {
                        //绘制圆形
                        drawCircle(
                            outWidth / 2f,
                            outHeight / 2f,
                            (outWidth - border) / 2f,
                            strokePaint
                        )
                    }
                }
            }
        }
    }

    class RoundDrawable(val bitmap: Bitmap, val targetWH: Float) : Drawable() {

        private val paint = Paint().apply {
            //缩放比例
            val scale = minOf(targetWH / bitmap.width, targetWH / bitmap.height)
            //创建矩阵
            val matrix = Matrix().apply {
                setScale(scale, scale)
            }
            isAntiAlias = true
            shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).apply {
                setLocalMatrix(matrix)
            }
        }

        override fun draw(canvas: Canvas) {
            val rectF = RectF(0f, 0f, targetWH, targetWH)
            canvas.drawRoundRect(rectF, 20f, 20f, paint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
    }
}