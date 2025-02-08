package org.ninetripods.mq.study.nestedScroll

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.nestedScroll.util.adapter.ZJBaseRecyclerAdapter
import org.ninetripods.mq.study.nestedScroll.util.adapter.ZJViewHolder
import java.util.*

/**
 * CoordinatorLayout + Toolbar
 */
class CoordinatorLayoutToolbarActivity : BaseActivity() {

    private val data = arrayOf(
        "Apple", "Banana", "Orange", "Watermelon",
        "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana", "Orange",
        "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango", "Banana",
        "Orange", "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
        "Banana", "Orange", "Watermelon",
    )

    override fun setContentView() {
        setContentView(R.layout.activity_coordinator_layout_toolbar)
    }

    override fun initViews() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        initToolBar(toolbar, "CoordinatorLayout+Toolbar", false)
        val stringList = Arrays.asList(*data)
        val recycle_view: RecyclerView = findViewById(R.id.recycle_view)
        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.adapter = object : ZJBaseRecyclerAdapter<Any?>(stringList as Collection<Any?>?,
            android.R.layout.simple_list_item_1, null) {
            override fun onBindViewHolder(holder: ZJViewHolder?, model: Any?, position: Int) {
                holder?.setText(android.R.id.text1, stringList[position])
            }
        }
    }
}