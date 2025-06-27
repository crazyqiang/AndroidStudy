package org.ninetripods.mq.study.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.matrix.ColorMatrixImageView


/**
 * ColorMatrix Fragment
 */
class ColorMatrixFragment : BaseFragment() {

    private val mIvOrigin: ImageView by id(R.id.iv_origin)
    private val mIvColorMatrix: ColorMatrixImageView by id(R.id.iv_color_matrix_xml_view)
    private val mTvProcess: TextView by id(R.id.tv_process)
    private val mSeekBar: SeekBar by id(R.id.seek_bar)

    override fun getLayoutId(): Int {
        return R.layout.layout_color_matrix_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val barValue = progress / 20f // 例如从 0.0f 到 5.0f
                //设置亮度
//                mIvColorMatrix.setBrightness(barValue)
//                mTvProcess.text = String.format("brightness=%.2f", barValue)

                //设置饱和度
//                mIvColorMatrix.setSaturation(barValue)
//                mTvProcess.text = String.format("饱和度=%.2f", barValue)

                //亮度 + 饱和度
                mIvColorMatrix.setAdjustments(barValue, barValue)
                mTvProcess.text = String.format("亮度&饱和度 = %.2f", barValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        //其他colorMatrix示例
        colorMatrixDemo()
    }



    /**
     *  对原图Bitmap进行饱和度处理后再显示出来
     */
    private fun colorMatrixDemo() {
        //方式一：
        //获取原始Bitmap
//        val originBitmap = BitmapFactory.decodeResource(resources, R.drawable.icon_cat_h)
//        //创建一个和原始Bitmap一样大小的Bitmap
//        val newBitmap = Bitmap.createBitmap(originBitmap.width, originBitmap.height, originBitmap.config)
//        //创建画笔和画布，并绑定newBitmap
//        val canvas = Canvas(newBitmap)
//        //创建ColorMatrix
//        val cm = ColorMatrix().apply {
//            setSaturation(0f) //设置饱和度
//        }
//        //设置画笔滤镜
//        val paint = Paint().apply {
//            setColorFilter(ColorMatrixColorFilter(cm))
//        }
//        //originBitmap通过带滤镜的paint绘制到canvas上，因为canvas绑定的是newBitmap，所以最终变灰后的结果是画到了newBitmap上！
//        canvas.drawBitmap(originBitmap, 0f, 0f, paint)
//        mIvOrigin.setImageBitmap(newBitmap)

        /**
         * 方式二：
         */
        //创建ColorMatrix
        //val matrix = ColorMatrix()
        //matrix.setRotate(1, 45f) //绕绿色通道旋转45度，第一个参数：0-Red  1-Green 2-Blue
        //matrix.setScale(1f, 1f, 1f, 0.5f) // 让alpha半透明
        //matrix.setScale(2f,2f, 2f, 1f) //变亮
        //mIvOrigin.colorFilter = ColorMatrixColorFilter(matrix)

        /**
         * 颜色反转
         */
//        val invert = ColorMatrix(
//            floatArrayOf(
//                -1f, 0f, 0f, 0f, 255f,
//                0f, -1f, 0f, 0f, 255f,
//                0f, 0f, -1f, 0f, 255f,
//                0f, 0f, 0f, 1f, 0f
//            )
//        )
//        mIvOrigin.colorFilter = ColorMatrixColorFilter(invert)
    }

}

