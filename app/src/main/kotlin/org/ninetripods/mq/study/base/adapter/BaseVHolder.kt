package org.ninetripods.mq.study.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by mq on 2023/7/19
 */
abstract class BaseVHolder<T>(context: Context, parent: ViewGroup, resource: Int) :
    RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(resource, parent, false)) {

    fun onBindViewHolder(item: T, position: Int) {
        onBindView(item, position)
    }

    abstract fun onBindView(item: T, position: Int)

    protected fun <V : View> bind(id: Int): V {
        return itemView.findViewById(id)
    }
}