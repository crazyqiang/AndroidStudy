package org.ninetripods.lib_viewpager2.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.lib_viewpager2.imageLoader.IClickListener
import org.ninetripods.lib_viewpager2.imageLoader.ILoader

const val EXTRA_NUM = 4 //额外增加4条数据
const val SIDE_NUM = 2 //左右两侧各增加2条

class MVP2Adapter<T : Any> : RecyclerView.Adapter<MVP2Adapter.PageViewHolder>() {
    private var mModels: List<T> = ArrayList()
    private var mLoader: ILoader<View>? = null
    private var mItemClickListener: IClickListener? = null

    fun setModels(models: List<T>) {
        this.mModels = models
    }

    fun setImageLoader(loader: ILoader<View>?) {
        this.mLoader = loader
    }

    fun setOnItemClickListener(listener: IClickListener?) {
        this.mItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        //log("onCreateViewHolder()")
        var itemShowView = mLoader?.createView(parent.context)
        if (itemShowView == null) {
            itemShowView = ImageView(parent.context)
        }
        /**
         * 必须都是MATCH_PARENT，否则报java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent)
         */
        itemShowView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return PageViewHolder(itemShowView)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        //log("onBindViewHolder(): pos is $position")
        val imgUrl = mModels[position]
        mLoader?.display(holder.itemShowView.context, imgUrl, holder.itemShowView)
        holder.itemShowView.setOnClickListener {
            mItemClickListener?.onItemClick(getRealPosition(position))
        }
    }

    override fun getItemCount(): Int = mModels.size

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemShowView: View = itemView
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