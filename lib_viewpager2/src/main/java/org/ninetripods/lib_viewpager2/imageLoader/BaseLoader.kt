package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

abstract class BaseLoader : ILoader<View> {

    override fun createView(context: Context): View {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return imageView
    }
}