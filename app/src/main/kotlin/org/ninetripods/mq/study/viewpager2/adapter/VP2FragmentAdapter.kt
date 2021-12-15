package org.ninetripods.mq.study.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.viewpager2.PageDiffUtil
import org.ninetripods.mq.study.viewpager2.model.VP2Model

const val PAGES_NUM = 10

class ViewPager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val mItems: ArrayList<VP2Model> = arrayListOf()

    override fun getItemCount(): Int = PAGES_NUM

    override fun createFragment(position: Int): Fragment {
        log("pos:$position: createFragment()")
        return VP2Fragment(position)
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        log("pos:$position: onBindViewHolder()")
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }

    fun setDatas(newItems: List<VP2Model>) {
        //不借助DiffUtil更新数据
        //mItems.clear()
        //mItems.addAll(newItems)
        //notifyDataSetChanged()

        //借助DiffUtil更新数据
        val callback = PageDiffUtil(mItems, newItems)
        val difResult = DiffUtil.calculateDiff(callback)
        mItems.clear()
        mItems.addAll(newItems)
        difResult.dispatchUpdatesTo(this)
    }
}

//class DoppelgangerAdapter(private val activity: FragmentActivity) : FragmentStateAdapter(activity) {
//
//    private val items: ArrayList<DoppelgangerItem> = arrayListOf()
//
//
//    override fun createFragment(position: Int): Fragment {
//        return DoppelgangerFragment.getInstance(doppelgangerList[position])
//    }
//
//    override fun getItemCount() = items.size
//
//    override fun getItemId(position: Int): Long {
//        return items[position].id.toLong()
//    }
//
//    override fun containsItem(itemId: Long): Boolean {
//        return items.any { it.id.toLong() == itemId }
//    }
//
//    fun setItems(newItems: List<PagerItem>) {
//        //不借助DiffUtil更新数据
//        //items.clear()
//        //items.addAll(newItems)
//        //notifyDataSetChanged()
//
//        //使用DiffUtil更新数据
//        val callback = PagerDiffUtil(items, newItems)
//        val diff = DiffUtil.calculateDiff(callback)
//        items.clear()
//        items.addAll(newItems)
//        diff.dispatchUpdatesTo(this)
//    }
//}