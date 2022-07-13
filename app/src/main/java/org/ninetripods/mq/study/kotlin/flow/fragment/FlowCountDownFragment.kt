package org.ninetripods.mq.study.kotlin.flow.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.flow.widget.CountDownCircleView
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast

/**
 * Flow实现一个倒计时功能
 */
class FlowCountDownFragment : BaseFragment() {
    private val mCountDownView: CountDownCircleView by id(R.id.count_down_view)
    private val mBtnStart: Button by id(R.id.btn_start)

    override fun getLayoutId(): Int = R.layout.fragment_flow_countdown

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBtnStart.setOnClickListener {
            mCountDownView.startCountDown(10) {
                showToast("倒计时结束")
            }
        }
    }

}