package org.ninetripods.mq.study.viewpager2.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class MyAdapter : ListAdapter<VP2Model, MyAdapter.MyViewHolder>(TASKS_COMPARATOR) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvContent: TextView = itemView.findViewById(R.id.tv_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val TASKS_COMPARATOR = object : DiffUtil.ItemCallback<VP2Model>() {
            override fun areItemsTheSame(oldItem: VP2Model, newItem: VP2Model): Boolean =
                oldItem.content == newItem.content

            override fun areContentsTheSame(oldItem: VP2Model, newItem: VP2Model): Boolean =
                oldItem == newItem
        }
    }
}