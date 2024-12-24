package org.ninetripods.mq.study.activity

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id

class ShadowActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mTvShadow: TextView by id(R.id.tv_shadow)
    private val mIvShadow: ImageView by id(R.id.iv_shadow)
    private val mTvShadowOutline: TextView by id(R.id.tv_shadow_outline)
    private val shadowSize = 15.dp2px().toFloat()

    // 利用MaterialShapeDrawable来设置阴影
    private val mTvShape: TextView by id(R.id.tv_shape)
    private val mFlShape: FrameLayout by id(R.id.fl_shape)

    override fun setContentView() {
        setContentView(R.layout.activity_shadow_view)
    }

    override fun initViews() {
        initToolBar(mToolBar, "阴影设置", true, false)

        mTvShadow.run {
            elevation = shadowSize
        }

        mIvShadow.elevation = shadowSize
        setOutLineProvider()

        // 利用MaterialShapeDrawable来设置阴影
        mTvShape.setShadow(this, hasStroke = true, strokeWidth = 1.dp2px().toFloat())
        mFlShape.setShadow(this@ShadowActivity, elevationColor = Color.RED)
    }


    private fun setOutLineProvider() {
        val customOutlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                // 设置为圆形轮廓
                outline.setOval(0, 0, view.width, view.height)
            }
        }
        // 应用到控件
        mTvShadowOutline.run {
            outlineProvider = customOutlineProvider
            clipToOutline = true
            elevation = shadowSize
        }
    }
}

// 设置填充颜色为绿色（正常状态），按下时为红色
//val fillColors = ColorStateList(
//    arrayOf(
//        intArrayOf(-android.R.attr.state_pressed),  // 默认状态
//        intArrayOf(android.R.attr.state_pressed)    // 按下状态
//    ),
//    intArrayOf(
//        Color.WHITE,  // 默认状态填充颜色
//        Color.GRAY     // 按下状态填充颜色
//    )
//)

fun View.setShadow(
    context: Context,
    elevationWidth: Float = 10.dp2px().toFloat(),
    elevationColor: Int = Color.GRAY,
    hasStroke: Boolean = false,
    strokeColor: Int = Color.GRAY,
    strokeWidth: Float = 2.dp2px().toFloat()
) {
    val shapeAppearanceModel = ShapeAppearanceModel.builder()
        .setAllCorners(CornerFamily.ROUNDED, 10.dp2px().toFloat())
        .build()
    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
        fillColor = ColorStateList.valueOf(Color.WHITE) //可以设置各个状态颜色（默认颜色、点击颜色等）
        if (hasStroke) {
            //有轮廓
            val padding = (strokeWidth / 2).toInt()
            setStroke(strokeWidth, ColorStateList.valueOf(strokeColor)) //设置描边宽度及颜色
            setPadding(padding, padding, padding, padding) //设置padding
        }
        shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS //阴影兼容模式
        initializeElevationOverlay(context)
        elevation = elevationWidth// 设置阴影高度
        setShadowColor(elevationColor) // 阴影颜色（半透明黑）
    }
    (this.parent as? ViewGroup)?.clipChildren = false
    this.background = materialShapeDrawable
}