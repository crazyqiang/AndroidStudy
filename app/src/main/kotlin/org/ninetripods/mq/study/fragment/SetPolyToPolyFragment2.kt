package org.ninetripods.mq.study.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.matrix.WarpImageView


/**
 * Poly Fragment2
 */
class SetPolyToPolyFragment2 : BaseFragment() {

    private val warpView: WarpImageView by id(R.id.warpView)
    private val btnStart: Button by id(R.id.btn_start)
    private val btnReset: Button by id(R.id.btn_reset)

    override fun getLayoutId(): Int {
        return R.layout.layout_matrix_poly_fragment2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bm = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_h)
        warpView.setImageBitmap(bm)

        btnStart.setOnClickListener {
            warpView.post {
                val vw = warpView.width.toFloat()
                val vh = warpView.height.toFloat()
                val dst = floatArrayOf(
                    10f, 40f,                  // 左上
                    vw - 300f, 40f,              // 右上
                    vw - 40f, vh - 10f,          // 右下
                    250f, vh - 20f              // 左下
                )
                warpView.setTargetCorners(dst)
                warpView.startCorrection(600L)
            }
        }

        btnReset.setOnClickListener {
            warpView.resetToStart(600L)
        }

    }

}

