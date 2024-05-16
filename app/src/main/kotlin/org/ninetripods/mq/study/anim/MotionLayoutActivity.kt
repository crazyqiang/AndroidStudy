package org.ninetripods.mq.study.anim

import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.motion.widget.MotionLayout
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * MotionLayout动画
 */
class MotionLayoutActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mBtn: Button by id(R.id.btn_clk)
    private val mMLayout: MotionLayout by id(R.id.motion_layout)
    private var mBtnSwitch = false

    override fun getLayoutId(): Int = R.layout.layout_activity_motionlayout

    override fun initViews() {
        initToolBar(mToolBar, "MotionLayout", true, false, TYPE_BLOG)
    }

    override fun initEvents() {
//        mBtn.setOnClickListener {
//            mBtnSwitch = !mBtnSwitch
//            if (mBtnSwitch) {
//                mMLayout.transitionToEnd()
//            } else {
//                mMLayout.transitionToStart()
//            }
//        }
    }
}