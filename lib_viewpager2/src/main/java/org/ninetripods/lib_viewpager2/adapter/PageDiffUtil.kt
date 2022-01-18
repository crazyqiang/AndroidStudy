package org.ninetripods.lib_viewpager2.adapter

import androidx.recyclerview.widget.DiffUtil

class PageDiffUtil(private val oldModels: List<Any>, private val newModels: List<Any>) :
    DiffUtil.Callback() {

    /**
     * 旧数据
     */
    override fun getOldListSize(): Int = oldModels.size

    /**
     * 新数据
     */
    override fun getNewListSize(): Int = newModels.size

    /**
     * DiffUtil调用来决定两个对象是否代表相同的Item。true表示两个Item相同(表示View可以复用)，false表示不相同(View不可以复用)
     * 例如，如果你的项目有唯一的id，这个方法应该检查它们的id是否相等。
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModels[oldItemPosition]::class.java == newModels[newItemPosition]::class.java
    }

    /**
     * 比较两个Item是否有相同的内容(用于判断Item的内容是否发生了改变)，
     * 该方法只有当areItemsTheSame (int, int)返回true时才会被调用。
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModels[oldItemPosition] == newModels[newItemPosition]
    }

    /**
     * 该方法执行时机：areItemsTheSame(int, int)返回true 并且 areContentsTheSame(int, int)返回false
     * 该方法返回Item中的变化数据，用于只更新Item中变化数据对应的UI
     */
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}