package org.ninetripods.mq.study.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id


class XFerModeActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)

    data class ViewItem(val titleName: String, val position: Int)
    private val recyclerView: RecyclerView by id(R.id.rv_view)
    
    companion object {
        const val FRAGMENT_X_BASIC = 0 //基础效果
        const val FRAGMENT_X_GGK = 1 //刮刮卡
        const val FRAGMENT_X_IMG = 2 //图片圆角
        const val FRAGMENT_X_BW = 3
    }

    override fun setContentView() {
        setContentView(R.layout.activity_xfermode)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Xfermode", true, false)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 准备数据
        val dataList = mutableListOf<ViewItem>().apply {
            add(ViewItem("XFerMode基础效果", FRAGMENT_X_BASIC))
            add(ViewItem("图片处理", FRAGMENT_X_IMG))
            add(ViewItem("刮刮卡效果", FRAGMENT_X_GGK))
        }

        // 设置适配器
        val adapter = MyAdapter(dataList) { _, item ->
            XFerModeDemosActivity.start(this, item.position, item.titleName)
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

