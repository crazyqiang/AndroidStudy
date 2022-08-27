package org.ninetripods.mq.study.viewpager2

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.viewpager2.fragment.*

const val FRAGMENT_BASE = 0
const val FRAGMENT_NESTED_SCROLL = 1
const val FRAGMENT_SEARCH_V_SCROLL = 2
const val FRAGMENT_MULTI_ITEM_TYPE = 3
const val FRAGMENT_TX_NEWS_BANNER = 4
const val KEY_FRAGMENT_TYPE = "key_fragment_type"

class ViewPager2Activity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_NESTED_SCROLL

    override fun setContentView() {
        setContentView(R.layout.activity_fragment_container)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_FRAGMENT_TYPE, FRAGMENT_NESTED_SCROLL)
        val titleName = when (mCurPos) {
            FRAGMENT_BASE -> "MVPager2基本使用"
            FRAGMENT_NESTED_SCROLL -> "MVPager2嵌套滑动"
            FRAGMENT_MULTI_ITEM_TYPE -> "MVPager2自定义Item样式"
            FRAGMENT_SEARCH_V_SCROLL -> "仿淘宝搜索文字上下轮播"
            FRAGMENT_TX_NEWS_BANNER -> "仿腾讯新闻轮播Banner"
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
            FRAGMENT_MULTI_ITEM_TYPE -> MVP2MultiItemFragment()
            FRAGMENT_SEARCH_V_SCROLL -> SearchVScrollFragment()
            FRAGMENT_TX_NEWS_BANNER -> TxNewsFragment()
            else -> MVP2BaseUseFragment()
        }
    }

}