package org.ninetripods.lib_viewpager2.imageLoader

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.R

/**
 * 默认为ImageView加载
 */
class DefaultLoader : BaseLoader() {

    override fun display(targetView: View, content: Any, position: Int) {
        val targetIv= targetView as ImageView
        when (content) {
            is Int -> {
                //加载本地资源
                targetIv.setImageResource(content)
            }
            is String -> {
                //加载url
                Glide.with(targetView.context)
                    .load(content)
                    .error(R.drawable.layout_img_default)
                    .into(targetIv)
            }
        }
    }
}