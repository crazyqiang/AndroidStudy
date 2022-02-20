package org.ninetripods.lib_viewpager2.imageLoader

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
    private var mTextColor: Int = R.color.black
    private var mTextGravity: Int = Gravity.CENTER
    private var mTextSize: Float = 14f

    override fun createView(parent: ViewGroup, viewType: Int): View {
        val frameLayout = FrameLayout(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(context.resources.getColor(mBgColor))
        }
        val textView = TextView(parent.context).apply {
            gravity = mTextGravity
            setTextColor(context.resources.getColor(mTextColor))
            textSize = mTextSize
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        frameLayout.addView(textView)
        return frameLayout
    }

    override fun display(targetView: View, content: Any, position: Int) {
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

    fun setGravity(gravity: Int): TextLoader {
        this.mTextGravity = gravity
        return this
    }

    fun setTextSize(textSize: Float): TextLoader {
        this.mTextSize = textSize
        return this
    }
}