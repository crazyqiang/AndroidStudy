package org.ninetripods.mq.study.nestedScroll

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * 自定义Behavior页面
 */
class CustomBehaviorActivity : BaseActivity() {

    private val mRvChild: RecyclerView by id(R.id.rv_nested_view)

    override fun setContentView() {
        setContentView(R.layout.activity_custom_behavior_scrolling)
    }

    override fun initViews() {
        mRvChild.layoutManager = LinearLayoutManager(this)
        mRvChild.adapter = NestedAdapter()
    }

    override fun initEvents() {
        super.initEvents()
    }

    class NestedAdapter : RecyclerView.Adapter<NestedAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: TextView) : RecyclerView.ViewHolder(itemView) {
            val textView = itemView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val textView = TextView(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 200)
                gravity = Gravity.CENTER
            }
            return MyViewHolder(textView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = "position: $position"
        }

        override fun getItemCount(): Int = 20
    }
}