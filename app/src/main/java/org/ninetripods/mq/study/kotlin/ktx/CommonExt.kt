package org.ninetripods.mq.study.kotlin.ktx

import android.app.Activity
import android.app.Dialog
import android.view.View
import com.example.jetpackstudy.util.ScreenUtil
import org.ninetripods.mq.study.MyApplication

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

fun Number.dp2px(): Int {
    return ScreenUtil.dp2px(MyApplication.getApplication(), toFloat())
}

fun Number.px2dp(): Int {
    return ScreenUtil.px2dp(MyApplication.getApplication(), toFloat())
}

fun Number.sp2px(): Int {
    return ScreenUtil.sp2px(MyApplication.getApplication(), toFloat())
}

fun Number.px2sp(): Int {
    return ScreenUtil.px2sp(MyApplication.getApplication(), toFloat())
}



