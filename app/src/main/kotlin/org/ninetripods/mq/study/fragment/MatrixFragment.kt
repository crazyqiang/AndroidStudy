package org.ninetripods.mq.study.fragment

import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.matrix.GestureImageView
import kotlin.math.min


/**
 * Matrix Fragment
 */
class MatrixFragment : BaseFragment() {

    private val mIvGesture: GestureImageView by id(R.id.iv_gesture)

    override fun getLayoutId(): Int {
        return R.layout.layout_matrix_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        setMatrixToImg(mIvGesture)
    }

    private fun setMatrixToImg(img: ImageView) {
        img.scaleType = ScaleType.MATRIX
        img.post {
            val drawable = img.drawable //图片
            drawable?.let {
                val matrix = Matrix()
                //计算宽高比例
                val widthScale = img.width.toFloat() / it.intrinsicWidth
                val heightScale = img.height.toFloat() / it.intrinsicHeight
                //选择较小的缩放比
                val scale = min(widthScale, heightScale)
                //缩放后的图片尺寸
                val scaledWidth = it.intrinsicWidth * scale
                val scaleHeight = it.intrinsicHeight * scale
                //计算平移量
                val dx = (img.width - scaledWidth) / 2
                val dy = (img.height - scaleHeight) / 2
                matrix.postScale(scale, scale)
                matrix.postTranslate(dx, dy)
                img.imageMatrix = matrix
            }
        }
    }

}

