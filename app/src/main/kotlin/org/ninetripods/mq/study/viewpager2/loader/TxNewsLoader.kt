package org.ninetripods.mq.study.viewpager2.loader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import org.ninetripods.lib_viewpager2.imageLoader.BaseLoader
import org.ninetripods.mq.study.R

private const val TYPE_VIEW_NEWS = 1 //样式1
private const val TYPE_IMAGE_MORE = 2 //样式2

data class TxNewsModel(val url: String, val text: String, val subText: String)

class TxNewsLoader(private val models: MutableList<Any>? = null) : BaseLoader() {

    override fun createView(parent: ViewGroup, viewType: Int): View {
        return when (viewType) {
            TYPE_VIEW_NEWS -> LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_view_news, parent, false)
            TYPE_IMAGE_MORE -> LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_view_more, parent, false)
            else -> super.createView(parent, viewType)
        }
    }

    override fun display(targetView: View, content: Any, position: Int) {
        when (content) {
            is String -> {
                val targetTv: TextView = targetView.findViewById(R.id.tv_title_more)
                targetTv.text = content
            }
            is TxNewsModel -> {
                val newsHolder: NewsHolder
                if (targetView.tag == null) {
                    newsHolder = NewsHolder()
                    newsHolder.targetIv = targetView.findViewById(R.id.iv_img)
                    newsHolder.targetTv = targetView.findViewById(R.id.tv_title)
                    newsHolder.targetSubTv = targetView.findViewById(R.id.tv_sub_title)
                    targetView.tag = newsHolder
                } else {
                    newsHolder = targetView.tag as NewsHolder
                }
                //加载url
                with(newsHolder) {
                    Glide.with(targetIv.context).load(content.url).into(targetIv)
                    targetTv.text = content.text
                    targetSubTv.text = content.subText
                }
            }
        }
    }

    internal class NewsHolder {
        lateinit var targetIv: ImageView
        lateinit var targetTv: TextView
        lateinit var targetSubTv: TextView
    }

    override fun getItemViewType(position: Int): Int {
        if (models == null || models.isEmpty() || models.size <= position) {
            return super.getItemViewType(position)
        }
        return when (models[position]) {
            is String -> TYPE_IMAGE_MORE
            is TxNewsModel -> TYPE_VIEW_NEWS
            else -> TYPE_VIEW_NEWS
        }
    }
}