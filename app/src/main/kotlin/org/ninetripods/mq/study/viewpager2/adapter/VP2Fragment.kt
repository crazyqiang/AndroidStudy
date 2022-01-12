package org.ninetripods.mq.study.viewpager2.adapter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.imageLoader.OnBannerClickListener
import org.ninetripods.lib_viewpager2.transformer.ScaleInTransformer
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

class VP2Fragment(val position: Int = 0) : BaseFragment() {

    private val mTvContent: TextView by id(R.id.tv_content)
    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        log("pos$position: onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        log("pos$position: onCreate()")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_slide
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMvp2()
        mTvContent.text = position.toString()
//        log("pos$position: onViewCreated()")
    }

    private fun initMvp2() {
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        multiTransformer.addTransformer(MarginPageTransformer(20))

        mMVPager2.setModels(MConstant.urls)
            .setItemClickListener(object : OnBannerClickListener {
                override fun OnItemClick(position: Int) {
                    log("$position is click")
                }
            })
            .setIndicatorShow(true)
            .setOffscreenPageLimit(1)
            .setPageTransformer(multiTransformer)
            .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
            //.setUserInputEnabled(false)
            //.setPagePadding(100, 0, 100, 0)
            .setAutoPlay(true)
            .setPageInterval(5 * 1000L)
            .start()
    }

    override fun onStart() {
        super.onStart()
//        log("pos$position: onStart()")
    }

    override fun onResume() {
        super.onResume()
//        log("pos$position: onResume()")
    }

    override fun onPause() {
        super.onPause()
//        log("pos$position: onPause()")
    }

    override fun onStop() {
        super.onStop()
//        log("pos$position: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        log("pos$position: onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
//        log("pos$position: onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
//        log("pos$position: onDetach()")
    }

}