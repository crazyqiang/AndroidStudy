package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.lib_viewpager2.imageLoader.TextLoader
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast

class SearchVScrollFragment : BaseFragment() {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mTvSearch: TextView by id(R.id.tv_search)
    private val mIvScan: ImageView by id(R.id.iv_scan)

    override fun getLayoutId(): Int {
        return R.layout.fragment_search_v_scroll
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVerticalTxScroll()
        mTvSearch.setOnClickListener { showToast("点击搜索") }
        mIvScan.setOnClickListener { showToast("点击扫描") }
    }

    private fun initVerticalTxScroll() {
        val models = arrayListOf("猫咪宠物衣服", "年货节，聚划算", "飞猪旅行", "天猫超市")
        mMVPager2.setModels(models)
            .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            .setUserInputEnabled(false)
            .setPageInterval(3000)
            .setAnimDuration(300)
            .setAutoPlay(true)
            .setLoader(
                TextLoader().setGravity(Gravity.START or Gravity.CENTER_VERTICAL)
                    .setTextColor(R.color.gray_holo_dark).setTextSize(14f)
            )
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    showToast(models[position])
                }
            })
            .start()
    }
}