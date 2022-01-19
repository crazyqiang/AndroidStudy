package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
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
    companion object {
        val urls = arrayListOf(
            "https://cdn03.pinkoi.com/pinkoi.magz/tvzvjB9F/14650149183643.jpg",
            "https://n.sinaimg.cn/spider20201221/308/w1143h765/20201221/4bba-kfnaptu6435105.jpg",
            "https://images.zi.org.tw/bigfang/2020/02/19222035/1582122033-6062688041443908d6d864b6722c38ff.jpg"
        )
    }


    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mTvAdd: TextView by id(R.id.tv_add_one)
    private val mTvDel: TextView by id(R.id.tv_del_one)
    private val mModels = mutableListOf(MConstant.IMG_1)

    override fun getLayoutId(): Int {
        return R.layout.fragment_mvpager2_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMvp2()
        mTvAdd.setOnClickListener {
            mModels.add(MConstant.IMG_2)
            mMVPager2.submitList(mModels)
        }
        mTvDel.setOnClickListener {
            if (mModels.isNotEmpty()) {
                mModels.removeAt(mModels.size - 1)
            }
            mMVPager2.submitList(mModels)
        }
    }

    private fun initMvp2() {
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        multiTransformer.addTransformer(MarginPageTransformer(20))

        mMVPager2.setModels(mModels)
            .setIndicatorShow(true)
            .setOffscreenPageLimit(1)
            //.setPageTransformer(multiTransformer)
            //.setAnimDuration(500)
            .setOrientation(MVPager2.ORIENTATION_HORIZONTAL)
            //.setUserInputEnabled(false)
            .setAutoPlay(false)
            .setPageInterval(5 * 1000L)
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    showToast("position is $position")
                }
            })
            .start()
    }
}