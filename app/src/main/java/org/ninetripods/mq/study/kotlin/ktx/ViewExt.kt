package org.ninetripods.mq.study.kotlin.ktx

import android.app.Activity
import android.app.Dialog
import android.graphics.Outline
import android.os.Build
import android.view.View
import android.view.ViewOutlineProvider
import org.ninetripods.mq.study.jetpack.base.widget.IRootView
import org.ninetripods.mq.study.widget.roundImage.RoundImgView

fun <T : View> Activity.bind(id: Int): T {
    return this.findViewById(id) as T
}

fun <T : View> View.bind(id: Int): T {
    return this.findViewById(id) as T
}

fun <T : View> Dialog.bind(id: Int): T {
    return this.findViewById(id) as T
}

fun <T : View> IRootView.bind(id: Int): T {
    return this.rootView().bind(id)
}

fun <T : View> Activity.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> View.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun <T : View> IRootView.id(id: Int) = lazy {
    this.rootView().findViewById<T>(id)
}

fun <T : View> Dialog.id(id: Int) = lazy {
    findViewById<T>(id)
}

fun View.clipToRoundView(type: Int = RoundImgView.SHAPE_ROUND_RECT) {
    if (Build.VERSION.SDK_INT >= 21) {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                if (view == null) return
                if (type == RoundImgView.SHAPE_ROUND_RECT) {
                    //设置一个矩形的轮廓，并指定其圆角半径
                    outline?.setRoundRect(0, 0, view.width, view.height, 15.dp2px().toFloat())
                } else {
                    //设置成椭圆或者圆形
                    outline?.setOval(0, 0, view.width, view.height)
                }
            }
        }
        //视图会根据outlineProvider提供的轮廓进行裁剪。任何超出轮廓的部分都会被裁剪掉
        clipToOutline = true
    }
}