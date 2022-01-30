package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
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

    override fun createView(context: Context): View {
        return super.createView(context)
    }

    override fun display(context: Context, content: Any, targetView: View) {
        Glide.with(context)
            .load(content)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transform(CenterCrop(), RoundedCorners(roundRadius)) //设置圆角半径
            .into(targetView as ImageView)
    }

}