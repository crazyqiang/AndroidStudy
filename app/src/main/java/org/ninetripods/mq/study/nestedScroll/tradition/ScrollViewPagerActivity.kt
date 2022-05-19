package org.ninetripods.mq.study.nestedScroll.tradition

import android.view.View
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.nestedScroll.util.adapter.MyPagerAdapter
import org.ninetripods.mq.study.nestedScroll.util.view.CustomViewPager

class ScrollViewPagerActivity : BaseActivity() {
    private var view_pager: CustomViewPager? = null
    private var myPagerAdapter: MyPagerAdapter? = null
    override fun setContentView() {
        setContentView(R.layout.activity_scroll_view_pager)
    }

    override fun initViews() {
        view_pager = findViewById<View>(R.id.view_pager) as CustomViewPager
        myPagerAdapter = MyPagerAdapter(this)
        view_pager!!.adapter = myPagerAdapter
    }

    override fun initEvents() {
        super.initEvents()
    }
}