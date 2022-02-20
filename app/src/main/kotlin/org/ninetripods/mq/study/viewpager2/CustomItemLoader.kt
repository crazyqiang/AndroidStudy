package org.ninetripods.mq.study.viewpager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.imageLoader.BaseLoader
import org.ninetripods.mq.study.R

private const val TYPE_IMAGE_ONLY = 0 //样式1
private const val TYPE_VIEW_MULTI = 1 //样式2

data class MultiModel(val url: String, val text: String, val subText: String)

class CustomItemLoader(private val models: MutableList<Any>? = null) : BaseLoader() {

    override fun createView(parent: ViewGroup, viewType: Int): View {
        return when (viewType) {
            TYPE_IMAGE_ONLY -> super.createView(parent, viewType)
            TYPE_VIEW_MULTI -> LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_view_multi, parent, false)
            else -> super.createView(parent, viewType)
        }
    }

    override fun display(targetView: View, content: Any, position: Int) {
        when (content) {
            is String -> {
                val targetIv = targetView as ImageView
                //加载url
                Glide.with(targetView.context).load(content).into(targetIv)
            }
            is MultiModel -> {
                val targetIv = targetView.findViewById<ImageView>(R.id.iv_img)
                val targetTv = targetView.findViewById<TextView>(R.id.tv_title)
                val targetSubTv = targetView.findViewById<TextView>(R.id.tv_sub_title)
                //加载url
                Glide.with(targetIv.context).load(content.url).into(targetIv)
                targetTv.text = content.text
                targetSubTv.text = content.subText
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (models == null || models.isEmpty() || models.size <= position) {
            return super.getItemViewType(position)
        }
        return when (models[position]) {
            is String -> TYPE_IMAGE_ONLY
            is MultiModel -> TYPE_VIEW_MULTI
            else -> TYPE_IMAGE_ONLY
        }
    }
}