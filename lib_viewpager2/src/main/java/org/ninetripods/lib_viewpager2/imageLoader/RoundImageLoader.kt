package org.ninetripods.lib_viewpager2.imageLoader

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/**
 * 设置圆角半径
 */
class RoundImageLoader(private val roundRadius: Int = 30) : BaseLoader() {

    override fun display(targetView: View, content: Any, position: Int) {
        Glide.with(targetView.context)
            .load(content)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transform(CenterCrop(), RoundedCorners(roundRadius)) //设置圆角半径
            .into(targetView as ImageView)
    }

}