package org.ninetripods.lib_viewpager2.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.lib_viewpager2.imageLoader.IClickListener
import org.ninetripods.lib_viewpager2.imageLoader.ILoader
import org.ninetripods.lib_viewpager2.log

const val EXTRA_NUM = 4 //额外增加4条数据
const val SIDE_NUM = 2 //左右两侧各增加2条
const val DELAY_INTERVAL_TIME = 5 * 1000L//自动轮播时间间隔

class MVP2Adapter<T : Any> : RecyclerView.Adapter<MVP2Adapter.PageViewHolder>() {
    private var mDatas: List<T> = ArrayList()
    private var mLoader: ILoader<ImageView>? = null
    private var mItemClickListener: IClickListener? = null

    fun setData(datas: List<T>) {
        this.mDatas = datas
    }

    fun setImageLoader(loader: ILoader<ImageView>) {
        this.mLoader = loader
    }

    fun setOnItemClickListener(listener: IClickListener?) {
        this.mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        log("onCreateViewHolder()")
        var imageView = mLoader?.createView(parent.context)
        if (imageView == null) {
            imageView = ImageView(parent.context)
        }
        /**
         * 必须都是MATCH_PARENT，否则报java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent)
         */
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return PageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        log("onBindViewHolder(): pos is $position")
        val imgUrl = mDatas[position]
        mLoader?.display(holder.mIvImage.context, imgUrl, holder.mIvImage)
        holder.mIvImage.setOnClickListener {
            mItemClickListener?.onItemClick(getRealPosition(position))
        }
    }

    override fun getItemCount(): Int = mDatas.size

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mIvImage: ImageView = itemView as ImageView
    }

    /**
     * 获取数据对应的真实位置
     * @param exPosition 扩展数据中的位置
     */
    private fun getRealPosition(exPosition: Int): Int {
        val realCount = itemCount - EXTRA_NUM
        var realPos = (exPosition - SIDE_NUM) % realCount
        if (realPos < 0) realPos += realCount
        return realPos
    }

}