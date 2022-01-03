package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * 默认为ImageView加载
 */
class DefaultLoader : BaseLoader() {

    override fun display(context: Context, content: Any, targetView: View) {
        Glide.with(context).load(content).into(targetView as ImageView)
    }
}