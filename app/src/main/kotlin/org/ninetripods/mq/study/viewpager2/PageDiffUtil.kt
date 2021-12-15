package org.ninetripods.mq.study.viewpager2

import androidx.recyclerview.widget.DiffUtil
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class PageDiffUtil(val oldList: List<VP2Model>, val newList: List<VP2Model>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        TODO("Not yet implemented")
    }
}