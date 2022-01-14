package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.imageLoader.OnBannerClickListener
import org.ninetripods.lib_viewpager2.transformer.ScaleInTransformer
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast

class MVP2BaseUseFragment : BaseFragment() {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)

    override fun getLayoutId(): Int {
        return R.layout.fragment_mvpager2_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMvp2()
    }

    private fun initMvp2() {
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        multiTransformer.addTransformer(MarginPageTransformer(20))

        mMVPager2.setModels(MConstant.urls)
            .setIndicatorShow(true)
            .setOffscreenPageLimit(1)
            //.setPageTransformer(multiTransformer)
            //.setAnimDuration(500)
            .setOrientation(MVPager2.ORIENTATION_HORIZONTAL)
            //.setUserInputEnabled(false)
            .setAutoPlay(true)
            .setPageInterval(5 * 1000L)
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    showToast("position is $position")
                }
            })
            .start()
    }
}