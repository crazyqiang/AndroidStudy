package org.ninetripods.mq.study.kotlin.ktx

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.MyApplication
import org.ninetripods.mq.study.kotlin.util.ScreenUtil

/**
 * Toast扩展函数
 */
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApplication.getApplication(), message, duration).show()
}

fun log(message: String) {
    Log.e("Tag", message)
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

fun View?.visible() {
    if (this?.visibility != View.VISIBLE) {
        this?.visibility = View.VISIBLE
    }
}

fun View?.invisible() {
    if (this?.visibility != View.INVISIBLE) {
        this?.visibility = View.INVISIBLE
    }
}

fun View?.gone() {
    if (this?.visibility != View.GONE) {
        this?.visibility = View.GONE
    }
}

fun Activity.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Activity.showToast(@StringRes msg: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun Fragment.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), msg, duration).show()
}

fun Fragment.showToast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}



