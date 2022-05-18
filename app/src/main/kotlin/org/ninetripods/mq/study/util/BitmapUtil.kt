package org.ninetripods.mq.study.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

object BitmapUtil {

    /**
     * View to Bitmap
     */
    private fun viewToBitmap(view: View, defaultW: Int = 0, defaultH: Int = 0): Bitmap? {
        //调用measure方法测量
        val measuredWidth: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val measuredHeight: Int = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(measuredWidth, measuredHeight)
        //调用layout方法布局
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        var width: Int = view.width
        if (width <= 0) {
            //兜底宽度
            width = defaultW
        }
        var height: Int = view.height
        if (height <= 0) {
            //兜底高度
            height = defaultH
        }
        return try {
            val bitmap: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            //canvas.drawColor(Color.WHITE)
            view.draw(canvas)
            bitmap
        } catch (ex: Throwable) {
            null
        }
    }
}