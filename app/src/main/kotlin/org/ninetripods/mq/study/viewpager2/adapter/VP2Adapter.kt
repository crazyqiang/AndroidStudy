package org.ninetripods.mq.study.viewpager2.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class VP2Adapter : RecyclerView.Adapter<VP2Adapter.PageViewHolder>() {
    private var mDatas: List<VP2Model> = ArrayList()

    fun setData(datas: List<VP2Model>) {
        this.mDatas = datas
    }

    class PageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTvContent: TextView = itemView.findViewById(R.id.tv_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        log("onCreateViewHolder()")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_slide, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        log("onBindViewHolder(): pos is $position")
        val data = mDatas[position]
        holder.mTvContent.text = data.content
        holder.mTvContent.setBackgroundColor(Color.parseColor(data.color))
    }

    override fun getItemCount(): Int = mDatas.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}