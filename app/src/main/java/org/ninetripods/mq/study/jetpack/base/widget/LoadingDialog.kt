package org.ninetripods.mq.study.jetpack.base.widget

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.animation.Animation
import android.view.animation.Animation.INFINITE
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import org.ninetripods.mq.study.R

class LoadingDialog(val mContext: Context, private val isCancelable: Boolean) : Dialog(
    mContext,
    R.style.LoadingDialog
) {
    private var imageView: ImageView

    init {
        setContentView(R.layout.layout_loading_view)
        imageView = findViewById(R.id.iv_image)
    }

    /**
     * 展示Loading
     */
    fun showDialog() {
        if (mContext is Activity && mContext.isFinishing) return
        val animation: Animation = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        animation.duration = 2000
        animation.repeatCount = INFINITE
        animation.interpolator = LinearInterpolator()
        animation.fillAfter = true
        imageView.startAnimation(animation)
        setCancelable(isCancelable)
        show()
    }

    /**
     * 关闭Loading
     */
    fun dismissDialog() {
        dismiss()
    }

}