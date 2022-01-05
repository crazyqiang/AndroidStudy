package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import org.ninetripods.lib_viewpager2.R

/**
 * TextView视图
 */
class TextLoader : BaseLoader() {

    @ColorRes
    private var mBgColor: Int = R.color.white

    @ColorRes
    private var mTextColor: Int = R.color.red_deep

    override fun createView(context: Context): View {
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frameLayout.setBackgroundColor(context.resources.getColor(mBgColor))
        val textView = TextView(context)
        textView.gravity = Gravity.CENTER
        textView.setTextColor(context.resources.getColor(mTextColor))
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frameLayout.addView(textView, layoutParams)
        return frameLayout
    }

    override fun display(context: Context, content: Any, targetView: View) {
        val frameLayout = targetView as FrameLayout
        val childView = frameLayout.getChildAt(0)
        if (childView is TextView) {
            childView.text = content.toString()
        }
    }

    fun setBgColor(@ColorRes bgColor: Int): TextLoader {
        this.mBgColor = bgColor
        return this
    }

    fun setTextColor(@ColorRes textColor: Int): TextLoader {
        this.mTextColor = textColor
        return this
    }
}