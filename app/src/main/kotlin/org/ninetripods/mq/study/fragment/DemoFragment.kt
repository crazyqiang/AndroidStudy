package org.ninetripods.mq.study.fragment

import android.graphics.Matrix
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.unit.dp
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * 示例Fragment
 */
class DemoFragment : BaseFragment() {
    private val mImg1: ImageView by id(R.id.iv_1)
    private val mImg2: ImageView by id(R.id.iv_2)
    private val mImg3: ImageView by id(R.id.iv_3)

    override fun getLayoutId(): Int {
        return R.layout.layout_demo_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        log("view: $view")
        log("1dp = ${1.dp2px()}px")
        processImg(mImg1)
        processImg(mImg2)
        processImg(mImg3)
    }

    private fun processImg(imageView: ImageView) {
        imageView.post {
            val drawable = imageView.drawable
            drawable?.let {
                val matrix = Matrix()
                //计算缩放比例，确保宽度填充满 ImageView
                val scale = imageView.width.toFloat() / it.intrinsicWidth
                log("imageView.width.toFloat():${imageView.width.toFloat()},it.intrinsicWidth:${it.intrinsicWidth},scale:$scale")
                matrix.setScale(scale, scale)
                //不平移，确保图片从顶部开始展示
                matrix.postTranslate(0f, 0f)
                //应用 Matrix 到 ImageView
                imageView.imageMatrix = matrix
            }
        }
    }
}

