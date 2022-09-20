package org.ninetripods.mq.study.viewpager2.fragment

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.viewpager2.loader.TxNewsModel
import org.ninetripods.mq.study.viewpager2.widget.VpLoadMoreView

class TxNewsFragment : BaseFragment() {
    private val mModels: MutableList<Any> = mutableListOf()
    private val mContainer: VpLoadMoreView by id(R.id.vp2_load_more)

    override fun getLayoutId(): Int {
        return R.layout.fragment_tx_news_n
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVerticalTxScroll()
    }

    private fun initVerticalTxScroll() {
        mModels.add(TxNewsModel(MConstant.IMG_3, "北京2022冬奥会", "冬奥会盛大开幕"))
        mModels.add(TxNewsModel(MConstant.IMG_1, "精美商品", "9块9包邮"))
        mModels.add(TxNewsModel(MConstant.IMG_4, "美轮美奂节目", "奥运五环缓缓升起"))
        mContainer.setData(mModels)
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