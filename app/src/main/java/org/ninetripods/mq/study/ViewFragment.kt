package org.ninetripods.mq.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.activity.RoundImageActivity
import org.ninetripods.mq.study.activity.ShadowActivity
import org.ninetripods.mq.study.activity.ShapeAbleViewActivity
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

/**
 * View相关
 */
class ViewFragment : BaseFragment() {
    data class ViewItem(val titleName: String, val clz: Class<*>)

    private val recyclerView: RecyclerView by id(R.id.rv_view)

    override fun getLayoutId(): Int = R.layout.fragment_view_series

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.layoutManager = LinearLayoutManager(context)

        // 准备数据
        val dataList = mutableListOf<ViewItem>().apply {
            add(ViewItem("图片设置圆角矩形、圆形等", RoundImageActivity::class.java))
            add(ViewItem("ShapeableImageView", ShapeAbleViewActivity::class.java))
            add(ViewItem("设置阴影", ShadowActivity::class.java))
        }

        // 设置适配器
        val adapter = MyAdapter(dataList) { _, item ->
            NavitateUtil.startActivity(activity, item.clz)
        }
        recyclerView.adapter = adapter
    }

    class MyAdapter(
        private val dataList: List<ViewItem>,
        private val onItemClick: (Int, ViewItem) -> Unit
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_tv, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = dataList[position]
            holder.textView.text = data.titleName

            // 设置点击事件
            holder.itemView.setOnClickListener {
                onItemClick(position, data)
            }
        }

        override fun getItemCount(): Int = dataList.size

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.tv_title)
        }
    }

}