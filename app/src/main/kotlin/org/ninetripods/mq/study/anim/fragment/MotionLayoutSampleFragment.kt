package org.ninetripods.mq.study.anim.fragment

import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment

class MotionLayoutSampleFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        //layout_circle_menu  放射状态动画
        //layout_motion_moon 月亮动画
        return R.layout.layout_circle_menu
    }
}