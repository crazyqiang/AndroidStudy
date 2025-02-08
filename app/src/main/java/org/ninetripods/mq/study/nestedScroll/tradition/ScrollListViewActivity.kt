package org.ninetripods.mq.study.nestedScroll.tradition

import android.view.View
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.nestedScroll.util.view.CustomListView
import org.ninetripods.mq.study.R
import android.widget.ArrayAdapter

class ScrollListViewActivity : BaseActivity() {
    private var lv_listView: CustomListView? = null
    private val data = arrayOf("Apple",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango",
        "Banana",
        "Orange",
        "Watermelon",
        "Pear",
        "Grape",
        "Pineapple",
        "Strawberry",
        "Cherry",
        "Mango")

    override fun setContentView() {
        setContentView(R.layout.activity_scroll)
    }

    override fun initViews() {
        lv_listView = findViewById<View>(R.id.lv_listView) as CustomListView
        val adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, data)
        lv_listView?.adapter = adapter
    }
}