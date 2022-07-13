package org.ninetripods.mq.study.kotlin.flow

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id

/**
 * flow相关
 */
class FlowDispatchActivity : BaseActivity() {

    private val mTvFlowBase: TextView by id(R.id.tv_flow_base)
    private val mTvFlowCountDown: TextView by id(R.id.tv_time_countdown)
    private val mToolBar: Toolbar by id(R.id.toolbar)

    override fun setContentView() {
        setContentView(R.layout.activity_flow_dispatch)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Flow系列", true, false)
    }

    override fun initEvents() {
        mTvFlowBase.setOnClickListener(this)
        mTvFlowCountDown.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_flow_base -> jumpTargetPage(FRAGMENT_FLOW_BASE)
            R.id.tv_time_countdown -> jumpTargetPage(FRAGMENT_FLOW_COUNT_DOWN)
        }
    }

    private fun jumpTargetPage(targetPage: Int) {
        val intent = Intent(this, FlowStudyActivity::class.java)
        intent.putExtra(KEY_FRAGMENT_TYPE, targetPage)
        startActivity(intent)
    }
}