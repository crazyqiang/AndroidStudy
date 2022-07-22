package org.ninetripods.mq.study.viewpager2.fragment

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.viewpager2.loader.TxNewsLoader
import org.ninetripods.mq.study.viewpager2.loader.TxNewsModel

class TxNewsFragment : BaseFragment() {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mModels: MutableList<Any> = mutableListOf()

    override fun getLayoutId(): Int {
        return R.layout.fragment_tx_news
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVerticalTxScroll()
    }

    private fun initVerticalTxScroll() {
        mModels.add(TxNewsModel(MConstant.IMG_3, "北京2022冬奥会", "冬奥会盛大开幕"))
        mModels.add(TxNewsModel(MConstant.IMG_1, "精美商品", "9块9包邮"))
        mModels.add(TxNewsModel(MConstant.IMG_4, "美轮美奂节目", "奥运五环缓缓升起"))
        mModels.add("更多新闻")
        mMVPager2.setModels(mModels)
            .setLoop(false) //非循环模式
            .setIndicatorShow(true)
            .setLoader(TxNewsLoader(mModels))
            .setPagePadding(40, 0, 60, 0)
            .setPageTransformer(CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(15))
            })
            .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
            .setAutoPlay(false)
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    showToast(mModels[position].toString())
                }
            })
            .registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val innerRecyclerView = mMVPager2.get().getChildAt(0) as RecyclerView
                    if (position == mModels.lastIndex) {
                        innerRecyclerView.setPadding(300, 0, 300, 0)
                    } else {
                        innerRecyclerView.setPadding(40, 0, 60, 0)
                    }
                }
            })
            .start()
    }

    /**
     * 5.0以上设置图片圆角
     */
    private fun clipToRoundView(view: View?) {
        if (Build.VERSION.SDK_INT >= 21) {
            view?.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    if (view == null) return
                    //设置矩形
//                    outline?.setRoundRect(0, 0, view.width, view.height,
//                        ScreenUtil.dp2px(context, 16F).toFloat())
                }
            }
            view?.clipToOutline = true
        }
    }
}