package org.ninetripods.mq.study.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.base.iInterface.ItemClick
import org.ninetripods.mq.study.base.iInterface.ItemClickListener

/**
 * Created by mq on 2023/7/19
 */
abstract class BaseVHolder<T>(context: Context, parent: ViewGroup, resource: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(resource, parent, false)) {

    protected val onItemClickListener by lazy { ItemClickListener<T>() }

    fun bindItemClick(itemClick: ItemClick<T>? = null) {
        itemClick?.let {
            onItemClickListener.itemClick = it
            onBindVHClick(onItemClickListener)
        }
    }

    /**
     * 在子ViewHolder中实现
     */
    open fun onBindVHClick(clickListener: ItemClickListener<T>) {}

    fun onBindViewHolder(item: T, position: Int) {
        //补充Model及位置position
        onItemClickListener.itemClick?.let {
            onItemClickListener.model = item
            onItemClickListener.position = position
        }
        onBindView(item, position)
    }

    abstract fun onBindView(item: T, position: Int)

    protected fun <V : View> bind(id: Int): V {
        return itemView.findViewById(id)
    }
}