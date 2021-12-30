package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.consts.log

class DefaultLoader : BaseLoader() {

    override fun display(context: Context, path: Any, targetView: ImageView) {
        log("display:$path")
        Glide.with(context).load(path).into(targetView)
    }
}