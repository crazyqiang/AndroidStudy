package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.viewpager2.loader.TxNewsModel
import org.ninetripods.mq.study.viewpager2.widget.VpLoadMoreView

/**
 * 仿淘宝京东宝贝详情Fragment
 */
class TJBannerFragment : BaseFragment() {
    private val mModels: MutableList<Any> = mutableListOf()
    private val mContainer: VpLoadMoreView by id(R.id.vp2_load_more)

    override fun getLayoutId(): Int {
        return R.layout.fragment_tx_news_n
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVerticalTxScroll()
    }

    private fun initVerticalTxScroll() {
        mModels.add(TxNewsModel(MConstant.IMG_4, "美轮美奂节目", "奥运五环缓缓升起"))
        mModels.add(TxNewsModel(MConstant.IMG_1, "精美商品", "9块9包邮"))
        mContainer.setData(mModels) {
            showToast("打开更多页面")
        }
    }

}