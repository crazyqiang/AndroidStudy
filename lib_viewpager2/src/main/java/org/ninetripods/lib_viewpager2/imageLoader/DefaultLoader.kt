package org.ninetripods.lib_viewpager2.imageLoader

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.R

/**
 * 默认为ImageView加载
 */
class DefaultLoader : BaseLoader() {

    override fun createView(context: Context): View {
        return super.createView(context)
    }

    override fun display(context: Context, content: Any, targetView: View) {
        val targetImageView = targetView as ImageView
        when (content) {
            is Int -> {
                //加载本地资源
                targetImageView.setImageResource(content)
            }
            is String -> {
                //加载url
                Glide.with(context)
                    .load(content)
                    .error(R.drawable.layout_img_default)
                    .into(targetImageView)
            }
        }
    }
}