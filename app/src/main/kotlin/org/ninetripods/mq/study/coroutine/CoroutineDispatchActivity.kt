package org.ninetripods.mq.study.coroutine

import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.CommonWebviewActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.flow.FRAGMENT_FLOW_BASE
import org.ninetripods.mq.study.kotlin.flow.FRAGMENT_FLOW_COUNT_DOWN
import org.ninetripods.mq.study.kotlin.flow.FlowStudyActivity
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.Constant

/**
 * 协程
 */
class CoroutineDispatchActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private val mTvBaseCoroutine: TextView by id(R.id.tv_coroutine_base)
    private val mTvFlowBase: TextView by id(R.id.tv_flow_base)
    private val mTvFlowCountDown: TextView by id(R.id.tv_time_countdown)

    override fun setContentView() {
        setContentView(R.layout.activity_coroutine_dispatch)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Kotlin基础/协程/Flow", true, false)
    }

    override fun initEvents() {
        mTvBaseCoroutine.setOnClickListener(this)
        mTvFlowBase.setOnClickListener(this)
        mTvFlowCountDown.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_coroutine_base -> CommonWebviewActivity.webviewEntrance(this, Constant.BLOG_KT_COROUTINE)
            //jumpTargetPage(FRAGMENT_BASE, CoroutineActivity::class.java)
            R.id.tv_flow_base -> jumpTargetPage(FRAGMENT_FLOW_BASE, FlowStudyActivity::class.java)
            R.id.tv_time_countdown ->
                jumpTargetPage(FRAGMENT_FLOW_COUNT_DOWN, FlowStudyActivity::class.java)
        }
    }

    private fun jumpTargetPage(targetPage: Int, clazz: Class<*>) {
        val intent = Intent(this, clazz)
        intent.putExtra(KEY_FRAGMENT_TYPE, targetPage)
        startActivity(intent)
    }
}