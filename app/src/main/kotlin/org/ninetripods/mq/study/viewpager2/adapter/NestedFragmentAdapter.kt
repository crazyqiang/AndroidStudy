package org.ninetripods.mq.study.viewpager2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.ninetripods.mq.study.viewpager2.PageDiffUtil
import org.ninetripods.mq.study.viewpager2.model.VP2Model

open class NestedFragmentAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val mItems: ArrayList<VP2Model> = arrayListOf()

    override fun getItemCount(): Int = mItems.size

    override fun createFragment(position: Int): Fragment {
        return NestedScrollItemFragment(mItems[position].id)
    }

    fun setDatas(newItems: List<VP2Model>, useDiffUtil: Boolean = true) {
        if (useDiffUtil) {
            //使用DiffUtil更新数据
            val callback = PageDiffUtil(mItems, newItems)
            val difResult = DiffUtil.calculateDiff(callback)
            mItems.clear()
            mItems.addAll(newItems)
            difResult.dispatchUpdatesTo(this)
        } else {
            //不使用DiffUtil更新数据
            mItems.clear()
            mItems.addAll(newItems)
            notifyDataSetChanged()
        }
    }
}
