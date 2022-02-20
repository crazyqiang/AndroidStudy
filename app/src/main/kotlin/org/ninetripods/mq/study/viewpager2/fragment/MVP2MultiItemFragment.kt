package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.viewpager2.CustomItemLoader
import org.ninetripods.mq.study.viewpager2.MultiModel

class MVP2MultiItemFragment : BaseFragment() {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)

    private val mModels: MutableList<Any> = mutableListOf()

    override fun getLayoutId(): Int = R.layout.fragment_mvpager2_muti_item

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mModels.add(MultiModel(MConstant.IMG_3, "北京2022冬奥会", "冬奥会盛大开幕"))
        mModels.add(MultiModel(MConstant.IMG_1, "精美商品", "9块9包邮"))
        mModels.add(MultiModel(MConstant.IMG_4, "美轮美奂节目", "奥运五环缓缓升起"))
        mMVPager2.setModels(mModels)
            .setLoader(CustomItemLoader(mModels))
            .setIndicatorShow(true)
            .setPagePadding(40, 0, 60, 0)
            .setOffscreenPageLimit(1) //离屏缓存数量
            .setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(15))
            })
            .setAutoPlay(true) //设置自动轮播
            .setPageInterval(3000L) //轮播间隔
            .setAnimDuration(300) //切换动画执行时间
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    //Item点击
                    showToast("position is $position")
                }
            })
            .start()
    }
}