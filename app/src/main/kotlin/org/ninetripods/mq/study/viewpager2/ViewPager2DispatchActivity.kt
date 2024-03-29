package org.ninetripods.mq.study.viewpager2

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * ViewPager2
 */
class ViewPager2DispatchActivity : BaseActivity() {

    private val mTvBaseVp2: TextView by id(R.id.tv_vp2_base)
    private val mTvMultiItemVp2: TextView by id(R.id.tv_vp2_multi_item)
    private val mTvNestedScrollVp2: TextView by id(R.id.tv_vp2_nested_scroll)
    private val mTvVerticalRoll: TextView by id(R.id.tv_vp2_vertical)
    private val mTvTxNews: TextView by id(R.id.tv_tx_news)
    private val mToolBar: Toolbar by id(R.id.toolbar)

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2_dispatch)
    }

    override fun initViews() {
        initToolBar(mToolBar, "ViewPager2系列", true, false)
    }

    override fun initEvents() {
        mTvBaseVp2.setOnClickListener(this)
        mTvNestedScrollVp2.setOnClickListener(this)
        mTvVerticalRoll.setOnClickListener(this)
        mTvMultiItemVp2.setOnClickListener(this)
        mTvTxNews.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_vp2_nested_scroll -> jumpTargetPage(FRAGMENT_NESTED_SCROLL)
            R.id.tv_vp2_vertical -> jumpTargetPage(FRAGMENT_SEARCH_V_SCROLL)
            R.id.tv_vp2_base -> jumpTargetPage(FRAGMENT_BASE)
            R.id.tv_vp2_multi_item -> jumpTargetPage(FRAGMENT_MULTI_ITEM_TYPE)
            R.id.tv_tx_news -> jumpTargetPage(FRAGMENT_TX_NEWS_BANNER)
        }
    }

    private fun jumpTargetPage(targetPage: Int) {
        val intent = Intent(this, ViewPager2Activity::class.java)
        intent.putExtra(KEY_FRAGMENT_TYPE, targetPage)
        startActivity(intent)
    }
}