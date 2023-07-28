package org.ninetripods.mq.study.base.iInterface

import android.view.View

/**
 * Created by mq on 2023/7/27
 */
interface ItemClick<T> {
    fun itemClick(itemView: View, model: T?, position: Int)
}

class ItemClickListener<T> : View.OnClickListener {
    var itemClick: ItemClick<T>? = null
    var position: Int = -1
    var model: T? = null

    override fun onClick(v: View?) {
        v?.let { itemClick?.itemClick(v, model, position) }
    }

}