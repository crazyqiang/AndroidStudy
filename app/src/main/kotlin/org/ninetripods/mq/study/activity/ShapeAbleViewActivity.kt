package org.ninetripods.mq.study.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.OffsetEdgeTreatment
import com.google.android.material.shape.RelativeCornerSize
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.gone
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.visible

/**
 * ShapeableImageView示例
 *
 */
class ShapeAbleViewActivity : BaseActivity() {

    companion object {
        const val TYPE_0 = 0
        const val TYPE_1 = 1
        const val TYPE_2 = 2
        const val TYPE_3 = 3
        const val TYPE_4 = 4
        const val TYPE_5 = 5
        const val TYPE_6 = 6
        const val TYPE_7 = 7
        const val TYPE_8 = 8
        const val TYPE_9 = 9
        const val TYPE_10 = 10
        const val TYPE_11 = 11
        const val TYPE_12 = 12
        const val TYPE_13 = 13
        const val TYPE_14 = 14
        const val TYPE_15 = 15
        const val TYPE_16 = 16
        const val TYPE_17 = 17
    }

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mRvShapeAble: RecyclerView by id(R.id.rv_shape_able_view)
    private val mTvShadow: TextView by id(R.id.tv_shadow)
    private val mIvShape: ShapeableImageView by id(R.id.iv_shape_view)


    override fun setContentView() {
        setContentView(R.layout.activity_shape_able)
    }

    override fun initViews() {
        initToolBar(mToolBar, "ShapeableImageView + ShapeAppearanceModel", true, false)
        mRvShapeAble.layoutManager = GridLayoutManager(this, 3)

        // 示例数据
        val imageList = mutableListOf<ShapeItem>().apply {
            add(ShapeItem(TYPE_0))
            add(ShapeItem(TYPE_1))
            add(ShapeItem(TYPE_2))
            add(ShapeItem(TYPE_3))
            add(ShapeItem(TYPE_4))
            add(ShapeItem(TYPE_5))

            add(ShapeItem(TYPE_6))
            add(ShapeItem(TYPE_7))
            add(ShapeItem(TYPE_8))
            add(ShapeItem(TYPE_9))
            add(ShapeItem(TYPE_10))
            add(ShapeItem(TYPE_11))
            add(ShapeItem(TYPE_12))
            add(ShapeItem(TYPE_13))
            add(ShapeItem(TYPE_14))
            add(ShapeItem(TYPE_15))
            add(ShapeItem(TYPE_16))
            add(ShapeItem(TYPE_17))
        }
        mRvShapeAble.adapter = ShapeAdapter(imageList)


        mTvShadow.isClickable = true
        // 设置填充颜色为绿色（正常状态），按下时为红色
        val fillColors = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_pressed),  // 默认状态
                intArrayOf(android.R.attr.state_pressed)    // 按下状态
            ),
            intArrayOf(
                Color.WHITE,  // 默认状态填充颜色
                Color.GRAY     // 按下状态填充颜色
            )
        )

        /**
         * 设置阴影效果
         */
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setAllCorners(CornerFamily.ROUNDED, 20f) // 设置圆角
            .build()

        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            //fillColor = ColorStateList.valueOf(Color.WHITE) //设置单个填充色
            fillColor = fillColors //可以设置各个状态颜色（默认颜色、点击颜色等）
            setStroke(2.dp2px().toFloat(), ColorStateList.valueOf(Color.RED)) //设置描边宽度及颜色
            setPadding(1.dp2px(), 1.dp2px(), 1.dp2px(), 1.dp2px()) //设置padding
            shadowCompatibilityMode = MaterialShapeDrawable.SHADOW_COMPAT_MODE_ALWAYS //阴影兼容模式
            initializeElevationOverlay(this@ShapeAbleViewActivity)
            elevation = 8.dp2px().toFloat() // 设置阴影高度
            setShadowColor(Color.GRAY) // 阴影颜色（半透明黑）
        }
        (mTvShadow.parent as? ViewGroup)?.clipChildren = false
        mTvShadow.background = materialShapeDrawable
    }

    data class ShapeItem(val type: Int, var desc: String = "")

    class ShapeAdapter(private val list: List<ShapeItem>) :
        RecyclerView.Adapter<ShapeAdapter.ShapeViewHolder>() {

        inner class ShapeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val ivShape: ShapeableImageView = itemView.findViewById(R.id.iv_shape_able)
            val tvShape: TextView = itemView.findViewById(R.id.textView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShapeViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shape_able_view, parent, false)
            return ShapeViewHolder(view)
        }

        override fun onBindViewHolder(holder: ShapeViewHolder, position: Int) {
            val item = list[position]
            val ivShape = holder.ivShape
            val tvShape = holder.tvShape
            if (position > TYPE_5) {
                //ImageView
                ivShape.visible()
                tvShape.gone()
            } else {
                //TextView
                ivShape.gone()
                tvShape.visible()
            }

            when (position) {
                TYPE_0 -> {
                    //圆角矩形
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f)
                        .build()
                    val drawable = MaterialShapeDrawable(shapeAppearanceModel)
                    tvShape.background = drawable
                }

                TYPE_1 -> {
                    //圆角矩形
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(RoundedCornerTreatment())
                        .setAllCornerSizes(RelativeCornerSize(0.5f))
                        .build()
                    val drawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
                        setStroke(2.dp2px().toFloat(), ColorStateList.valueOf(Color.RED)) //设置描边宽度及颜色
                        setPadding(1.dp2px(), 1.dp2px(), 1.dp2px(), 1.dp2px()) //设置padding
                    }
                    tvShape.layoutParams.run {
                        width = LayoutParams.MATCH_PARENT
                        height = 50.dp2px()
                    }
                    tvShape.background = drawable
                }

                TYPE_2 -> {
                    //切边
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.CUT, 20f) // 设置圆角
                        .build()

                    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
                    tvShape.background = materialShapeDrawable
                }

                TYPE_3 -> {
                    //聊天气泡
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f) // 设置圆角
                        .setBottomEdge(TriangleEdgeTreatment(16f, false)) //凸边
                        //.setRightEdge(TriangleEdgeTreatment(16f, false))
                        //.setLeftEdge(TriangleEdgeTreatment(16f, false))
                        .build()

                    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
                    tvShape.run {
                        (parent as? ViewGroup)?.clipChildren = false
                        tvShape.layoutParams.height = 38.dp2px()
                        setPadding(20.dp2px(), 5.dp2px(), 20.dp2px(), 5.dp2px())
                        background = materialShapeDrawable
                    }
                }

                TYPE_4 -> {
                    //聊天气泡
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f) // 设置圆角
                        .setBottomEdge(OffsetEdgeTreatment(TriangleEdgeTreatment(16f, false),-15.dp2px().toFloat())) //凸边
                        .build()

                    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
                    tvShape.run {
                        (parent as? ViewGroup)?.clipChildren = false
                        tvShape.layoutParams.height = 38.dp2px()
                        setPadding(20.dp2px(), 5.dp2px(), 20.dp2px(), 5.dp2px())
                        background = materialShapeDrawable
                    }
                }

                TYPE_5 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 14f) // 设置圆角
                        .setAllEdges(TriangleEdgeTreatment(12f, true)) //凹边
                        .build()

                    val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
                    tvShape.run {
                        tvShape.layoutParams.height = 50.dp2px()
                        setPadding(20.dp2px(), 5.dp2px(), 20.dp2px(), 5.dp2px())
                        background = materialShapeDrawable
                    }
                }
                TYPE_6 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f)
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_7 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.CUT, 16f)
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_8 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        //RoundedCornerTreatment圆角  CutCornerTreatment切边
                        .setAllCorners(CutCornerTreatment())
                        //AbsoluteCornerSize具体数值 RelativeCornerSize比例（0.0-1.0）
                        .setAllCornerSizes(16f) //.setAllCornerSizes(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_9 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(RoundedCornerTreatment())
                        .setAllCornerSizes(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_10 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setTopLeftCornerSize(RelativeCornerSize(0.5f))
                        .setTopLeftCorner(RoundedCornerTreatment())
                        .setBottomRightCorner(RoundedCornerTreatment())
                        .setBottomRightCornerSize(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_11 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setTopRightCornerSize(RelativeCornerSize(0.5f))
                        .setTopRightCorner(RoundedCornerTreatment())
                        .setBottomLeftCorner(RoundedCornerTreatment())
                        .setBottomLeftCornerSize(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_12 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CornerFamily.ROUNDED, 16f)
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                    ivShape.run {
                        //设置边框
                        strokeColor = ColorStateList.valueOf(Color.RED)
                        strokeWidth = 4.dp2px().toFloat()
                        setPadding(2.dp2px(), 2.dp2px(), 2.dp2px(), 2.dp2px())
                    }
                }

                TYPE_13 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(RoundedCornerTreatment())
                        .setAllCornerSizes(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                    ivShape.run {
                        //设置边框
                        strokeColor = ColorStateList.valueOf(Color.RED)
                        strokeWidth = 4.dp2px().toFloat()
                        setPadding(2.dp2px(), 2.dp2px(), 2.dp2px(), 2.dp2px())
                    }
                }

                TYPE_14 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllEdges(TriangleEdgeTreatment(16f, true))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                    ivShape.run {
                        //设置边框
                        strokeColor = ColorStateList.valueOf(Color.RED)
                        strokeWidth = 4.dp2px().toFloat()
                        setPadding(2.dp2px(), 2.dp2px(), 2.dp2px(), 2.dp2px())
                    }
                }

                TYPE_15 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllEdges(OffsetEdgeTreatment(TriangleEdgeTreatment(16f, true), 50f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

                TYPE_16 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setLeftEdge(TriangleEdgeTreatment(16f, true))
                        //.setAllEdges(OffsetEdgeTreatment(TriangleEdgeTreatment(16f, true),50f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }
                TYPE_17 -> {
                    val shapeAppearanceModel = ShapeAppearanceModel.builder()
                        .setAllCorners(CutCornerTreatment())
                        .setAllCornerSizes(RelativeCornerSize(0.5f))
                        .build()
                    ivShape.shapeAppearanceModel = shapeAppearanceModel
                }

            }
        }

        override fun getItemCount(): Int = list.size
    }
}