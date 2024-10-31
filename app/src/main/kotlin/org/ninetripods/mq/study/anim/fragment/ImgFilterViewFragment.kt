package org.ninetripods.mq.study.anim.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id

class ImgFilterViewFragment : BaseFragment() {
    private val mClFilterView: ImageFilterView by id(R.id.iv_filter_view)
    private val mRvImgFilter: RecyclerView by id(R.id.rv_img_filter)

    override fun getLayoutId(): Int {
        return R.layout.fragment_cl_img_filter
    }

    companion object {
        const val TYPE_SATURATION = 1 // 色彩饱和度
        const val TYPE_BRIGHT_NESS = 6 // 对比度
        const val TYPE_CONTRACT = 7 // 亮度
        const val TYPE_WARMTH = 2 // 色温
        const val TYPE_ROUND = 3 //  设置圆角
        const val TYPE_ROUND_PERCENT = 4 //圆角大小百分比
        const val TYPE_ALT_SCR = 5 //覆盖在src上面的交叉图片
        const val TYPE_IMG_ROTATE = 9 //图片旋转
        const val TYPE_IMG_ZOOM = 10 //图片缩放
        const val TYPE_OTHER = 11 //其他
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRvImgFilter.layoutManager = GridLayoutManager(context, 3)

        // 示例数据
        val imageList = mutableListOf<ImageItem>().apply {
            add(ImageItem(TYPE_SATURATION, "色彩饱和度saturation=0.1f"))
            add(ImageItem(TYPE_BRIGHT_NESS, "亮度brightness=2f"))
            add(ImageItem(TYPE_CONTRACT, "对比度contrast=0.5f"))
            add(ImageItem(TYPE_WARMTH, "色温warmth=0.5f"))
            add(ImageItem(TYPE_ROUND, "圆角round=10dp"))
            add(ImageItem(TYPE_ROUND_PERCENT, "圆角百分比\nroundPercent=1.0f"))
            add(ImageItem(TYPE_ALT_SCR, "覆盖在src上面的交叉图片altSrc"))
            add(ImageItem(TYPE_IMG_ROTATE, "图片旋转\nimageRotate=90f"))
            add(ImageItem(TYPE_IMG_ZOOM, "图片缩放imageZoom=0.5f"))
        }
        mRvImgFilter.adapter = ImageAdapter(imageList)
    }

    data class ImageItem(val type: Int, val description: String)

    class ImageAdapter(private val imageList: List<ImageItem>) :
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

        inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imgFilterView: ImageFilterView = itemView.findViewById(R.id.imageView)
            val textView: TextView = itemView.findViewById(R.id.textView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_image_filter_view, parent, false)
            return ImageViewHolder(view)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            val item = imageList[position]
            val ivFilter = holder.imgFilterView
            when (item.type) {
                TYPE_SATURATION -> ivFilter.saturation = 0.1f
                TYPE_BRIGHT_NESS -> ivFilter.brightness = 2f
                TYPE_CONTRACT -> ivFilter.contrast = 0.5f
                TYPE_WARMTH -> ivFilter.warmth = 0.5f
                TYPE_ROUND -> ivFilter.round = 10.dp2px().toFloat()
                TYPE_ROUND_PERCENT -> ivFilter.roundPercent = 1.0f
                TYPE_ALT_SCR -> {
                    ivFilter.setAltImageResource(R.drawable.icon_cat_h)
                    //上方图片的透明度
                    ivFilter.crossfade = 0.5f
                }
                TYPE_IMG_ROTATE -> { ivFilter.imageRotate = 90f }
                TYPE_IMG_ZOOM -> { ivFilter.imageZoom = 0.5f }
            }
            holder.textView.text = item.description
        }

        override fun getItemCount(): Int = imageList.size
    }
}