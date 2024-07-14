package org.ninetripods.mq.study.activity

import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.kotlin.ktx.showToast
import org.ninetripods.mq.study.widget.HorizontalPicScrollerView
import org.ninetripods.mq.study.widget.HorizontalPicScrollerView.Companion.TYPE_DATA
import org.ninetripods.mq.study.widget.HorizontalPicScrollerView.Companion.TYPE_LOAD_MORE
import org.ninetripods.mq.study.widget.ItemPicInfo

class HorizontalSlideActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mSlideView: HorizontalPicScrollerView by id(R.id.hz_slide_pic_view)

    override fun setContentView() {
        setContentView(R.layout.activity_horizontal_scorll)
        initToolBar(mToolBar, "左滑查看更多", true, false)
        val list = mutableListOf<ItemPicInfo>()
        (0..7).forEach { _ ->
            list.add(ItemPicInfo("https://img.china.alibaba.com/img/ibank/2013/101/139/834931101_1920026967.jpg", TYPE_DATA))
        }
        list.add(ItemPicInfo(dataType = TYPE_LOAD_MORE))
        log("list数据:${list.size}，$list")
        mSlideView.setData(list, { item ->
            //item Click
            showToast("点击图片")
        }, {
            //查看更多
            showToast("查看更多")
        })
    }
}