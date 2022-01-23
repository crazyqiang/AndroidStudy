package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.lib_viewpager2.imageLoader.TextLoader
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

    private var isIndicatorShow = true
    private var isHorizontal = true
    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mTvOrientation: TextView by id(R.id.tv_orientation)
    private val mTvAdd: TextView by id(R.id.tv_add_one)
    private val mTvDel: TextView by id(R.id.tv_del_one)
    private val mTvPlay: TextView by id(R.id.tv_auto_play)
    private val mTvIndicator: TextView by id(R.id.tv_indicator)
    private val mTvMultiPage: TextView by id(R.id.tv_multi_page)
    private val mTvTransformer: TextView by id(R.id.tv_page_transformer)
    private val mTvItemLoader: TextView by id(R.id.tv_custom_loader)

    private val mModels = mutableListOf(MConstant.IMG_1, MConstant.IMG_2, MConstant.IMG_3)


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
        mTvOrientation.setOnClickListener {
            if (isHorizontal) {
                mMVPager2.setOrientation(MVPager2.ORIENTATION_VERTICAL).start()
                isHorizontal = false
                mTvOrientation.text = "水平方向滑动"
            } else {
                mMVPager2.setOrientation(MVPager2.ORIENTATION_HORIZONTAL).start()
                isHorizontal = true
                mTvOrientation.text = "竖直方向滑动"
            }
        }
        mTvPlay.setOnClickListener {
            if (mMVPager2.isAutoPlay()) {
                mMVPager2.setAutoPlay(false).start()
                mTvPlay.text = "开始自动轮播"
            } else {
                mMVPager2.setAutoPlay(true).start()
                mTvPlay.text = "停止自动轮播"
            }
        }
        mTvMultiPage.setOnClickListener {
            mMVPager2.setPagePadding(50, 0, 50, 0).start()
        }

        mTvIndicator.setOnClickListener {
            if (isIndicatorShow) {
                mMVPager2.setIndicatorShow(false).start()
                isIndicatorShow = false
                mTvIndicator.text = "打开轮播指示器"
            } else {
                mMVPager2.setIndicatorShow(true).start()
                isIndicatorShow = true
                mTvIndicator.text = "关闭轮播指示器"
            }

        }
        mTvTransformer.setOnClickListener {
            //转换动画
            val multiTransformer = CompositePageTransformer()
            multiTransformer.addTransformer(ScaleInTransformer())
            multiTransformer.addTransformer(MarginPageTransformer(20))
            mMVPager2.setPageTransformer(multiTransformer).start()
        }

        mTvItemLoader.setOnClickListener {
            //自定义Item样式
            mMVPager2.setLoader(TextLoader()).start()
        }
    }

    private fun initMvp2() {
        mMVPager2.setModels(mModels)
            .setIndicatorShow(true) //设置轮播指示器
            .setOffscreenPageLimit(1) //离屏缓存数量
            //.setPageTransformer(multiTransformer) //转换动画
            .setOrientation(MVPager2.ORIENTATION_HORIZONTAL) //轮播方向
            //.setUserInputEnabled(true) //控制是否可以触摸滑动
            .setAutoPlay(false) //设置自动轮播
            .setPageInterval(5000L) //轮播间隔
            .setAnimDuration(500) //切换动画执行时间
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    //Item点击
                    showToast("position is $position")
                }
            })
            .start()
    }
}