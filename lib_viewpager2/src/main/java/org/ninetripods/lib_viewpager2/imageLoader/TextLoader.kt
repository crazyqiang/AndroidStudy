package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView

/**
 * TextView视图
 */
class TextLoader : BaseLoader() {

    private lateinit var mTextView: TextView

    override fun createView(context: Context): View {
        val frameLayout = FrameLayout(context)
        frameLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frameLayout.setBackgroundColor(Color.parseColor("#DDDDDD"))
        mTextView = TextView(context)
        mTextView.gravity = Gravity.CENTER
        mTextView.setTextColor(Color.parseColor("#FFFFFF"))
        val layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        frameLayout.addView(mTextView, layoutParams)
        return frameLayout
    }

    override fun display(context: Context, content: Any, targetView: View) {
        mTextView.text = content.toString()
    }
}