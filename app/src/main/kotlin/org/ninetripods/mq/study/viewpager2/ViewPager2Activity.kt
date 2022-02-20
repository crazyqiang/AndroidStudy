package org.ninetripods.mq.study.viewpager2

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.viewpager2.fragment.MVP2BaseUseFragment
import org.ninetripods.mq.study.viewpager2.fragment.MVP2MultiItemFragment
import org.ninetripods.mq.study.viewpager2.fragment.NestedScrollFragment
import org.ninetripods.mq.study.viewpager2.fragment.SearchVScrollFragment

const val FRAGMENT_BASE = 0
const val FRAGMENT_NESTED_SCROLL = 1
const val FRAGMENT_SEARCH_V_SCROLL = 2
const val FRAGMENT_MULTI_ITEM_TYPE = 3
const val KEY_FRAGMENT_TYPE = "key_fragment_type"

class ViewPager2Activity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_NESTED_SCROLL

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_FRAGMENT_TYPE, FRAGMENT_NESTED_SCROLL)
        val titleName = when (mCurPos) {
            FRAGMENT_BASE -> "MVPager2基本使用"
            FRAGMENT_NESTED_SCROLL -> "MVPager2嵌套滑动"
            FRAGMENT_SEARCH_V_SCROLL -> "仿淘宝搜索文字上下轮播"
            FRAGMENT_MULTI_ITEM_TYPE -> "MVPager2自定义Item样式"
            else -> "MVPager2"
        }
        initToolBar(mToolBar, titleName, true, false, TYPE_BLOG)
    }

    override fun initEvents() {
        val targetFragment = createTargetFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, targetFragment)
            .commitAllowingStateLoss()
    }

    private fun createTargetFragment(): Fragment {
        return when (mCurPos) {
            FRAGMENT_BASE -> MVP2BaseUseFragment()
            FRAGMENT_NESTED_SCROLL -> NestedScrollFragment()
            FRAGMENT_SEARCH_V_SCROLL -> SearchVScrollFragment()
            FRAGMENT_MULTI_ITEM_TYPE -> MVP2MultiItemFragment()
            else -> MVP2BaseUseFragment()
        }
    }

}