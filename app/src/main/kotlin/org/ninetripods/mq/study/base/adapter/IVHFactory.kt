package org.ninetripods.mq.study.base.adapter

import android.content.Context
import android.view.ViewGroup

/**
 * Created by mq on 2023/7/20
 */
interface IVHFactory {
    fun getVH(context: Context, parent: ViewGroup, viewType: Int): BaseVHolder<*>
}

interface IMultiType {
    fun getItemViewType(): Int
}