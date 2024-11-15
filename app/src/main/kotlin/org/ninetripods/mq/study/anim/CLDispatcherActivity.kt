package org.ninetripods.mq.study.anim

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.utils.widget.ImageFilterView
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

class CLDispatcherActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)

    //    private val mBtn: Button by id(R.id.btn_clk)
//    private val mMLayout: MotionLayout by id(R.id.motion_layout)
//    private var mBtnSwitch = false
    private var mF: ImageFilterView? = null


    private val mTvImgFilter: TextView by id(R.id.tv_img_filter)
    private val mTvMotionSample: TextView by id(R.id.tv_tx_motion_sample)

    override fun getLayoutId(): Int = R.layout.layout_activity_motionlayout_total

    override fun initViews() {
        initToolBar(mToolBar, "ConstraintLayout系列", true, false, TYPE_BLOG)
    }

    override fun initEvents() {
        mTvImgFilter.setOnClickListener(this)
        mTvMotionSample.setOnClickListener(this)
//        mBtn.setOnClickListener {
//            mBtnSwitch = !mBtnSwitch
//            if (mBtnSwitch) {
//                mMLayout.transitionToEnd()
//            } else {
//                mMLayout.transitionToStart()
//            }
//        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_img_filter -> jumpTargetPage(FRAGMENT_IMG_FILTER)
            R.id.tv_tx_motion_sample -> jumpTargetPage(FRAGMENT_MOTION_LAYOUT_SAMPLE)
        }
    }

    private fun jumpTargetPage(targetPage: Int) {
        val intent = Intent(this, ConstraintLayoutActivity::class.java)
        intent.putExtra(KEY_CL_FRAGMENT_TYPE, targetPage)
        startActivity(intent)
    }


}