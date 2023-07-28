package org.ninetripods.mq.study.base.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.base.iInterface.ItemClick
import org.ninetripods.mq.study.chatgpt.ChatDiffUtil

/**
 * Created by mq on 2023/7/20
 * RecyclerView.Adapter基类
 */
open class BaseAdapter<T : Any>(
    private val vhFactory: IVHFactory,
    private val itemClick: ItemClick<T>? = null,
) :
    RecyclerView.Adapter<BaseVHolder<T>>() {
    private val models = mutableListOf<T>()

    override fun getItemViewType(position: Int): Int {
        val model = models[position]
        if (model is IMultiType) return model.getItemViewType()
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVHolder<T> {
        val holder = vhFactory.getVH(parent.context, parent, viewType) as BaseVHolder<T>
        //绑定点击事件
        holder.bindItemClick(itemClick)
        return holder
    }

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: BaseVHolder<T>, position: Int) {
        holder.onBindViewHolder(models[position], position)
    }

    fun submitList(newList: List<T>) {
        //传入新旧数据进行比对
        val diffUtil = ChatDiffUtil(models, newList)
        //经过比对得到差异结果
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        //NOTE:注意这里要重新设置Adapter中的数据
        models.clear()
        models.addAll(newList)
        //将数据传给adapter，最终通过adapter.notifyItemXXX更新数据
        diffResult.dispatchUpdatesTo(this)
    }

    fun addModel(model: T) {
        models.add(model)
        notifyItemInserted(itemCount)
    }
}

/**
 * 使用DiffUtil的ListAdapter，其父类还是RecyclerView.Adapter
 * @param diff DiffUtil.Callback主要用于比较新旧列表差异，并处理插入、删除和移动等操作；
 * 而 DiffUtil.ItemCallBack只关注当前位置处的对象是否发生改变。我们应该根据具体情况选择合适的接口来实现数据更新检查并且以最高效率达成目标 。
 */
class BaseListAdapter<T>(diff: DiffUtil.ItemCallback<T>, private val vhFactory: IVHFactory) :
    ListAdapter<T, BaseVHolder<T>>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVHolder<T> {
        return vhFactory.getVH(parent.context, parent, viewType) as BaseVHolder<T>
    }

    override fun onBindViewHolder(holder: BaseVHolder<T>, position: Int) {
        holder.onBindViewHolder(currentList[position], position)
    }

    fun submit(models: List<T>) {
        submitList(models)
    }
}