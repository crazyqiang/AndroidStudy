package org.ninetripods.mq.study.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.DanMuView

/**
 * 弹幕Activity
 */
class DanMuAnimActivity : BaseActivity() {

    private val mDanMuView: DanMuView by id(R.id.danMuView)
    private val mToolBar: Toolbar by id(R.id.toolbar)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dan_mu_anim)
        initToolBar(mToolBar, "弹幕", true, false)
        initData()
    }

    private fun initData() {
        val danMuList = ArrayList<String>()
        for (i in 0..50) {
            danMuList.add("我是一个大大的弹幕$i")
        }
        mDanMuView.setRow(3) //设置行数
        mDanMuView.setModels(danMuList) //设置数据
    }

    fun startDanMu(view: View) {
        mDanMuView.startPlay()
    }

    fun stopDanMu(view: View) {
        mDanMuView.stopPlay()
    }

}