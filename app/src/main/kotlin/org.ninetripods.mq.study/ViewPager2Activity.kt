package org.ninetripods.mq.study

import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.mq.study.kotlin.ktx.id

class ViewPager2Activity : BaseActivity() {

    private val mViewPager2: ViewPager2 by id(R.id.view_pager2)

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2)
    }

    override fun initEvents() {
        val pagerAdapter = ViewPager2Adapter(this)
        mViewPager2.adapter = pagerAdapter
    }

}