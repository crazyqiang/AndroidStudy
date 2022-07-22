package org.ninetripods.mq.study.kotlin.flow

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.CommonWebviewActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.flow.fragment.FlowBaseFragment
import org.ninetripods.mq.study.kotlin.flow.fragment.FlowCountDownFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.Constant

const val FRAGMENT_FLOW_BASE = 0
const val FRAGMENT_FLOW_COUNT_DOWN = 1
const val KEY_FRAGMENT_TYPE = "key_fragment_type"

/**
 * Kotlin Flow用法
 */
class FlowStudyActivity : BaseActivity() {

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_FLOW_BASE

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_FRAGMENT_TYPE, FRAGMENT_FLOW_BASE)
        val titleName = when (mCurPos) {
            FRAGMENT_FLOW_BASE -> "Kotlin Flow"
            FRAGMENT_FLOW_COUNT_DOWN -> "Kotlin Flow实现倒计时功能"
            else -> "Kotlin Flow"
        }
        initToolBar(mToolBar, titleName, true, true, TYPE_BLOG)
    }

    override fun initEvents() {
        val targetFragment = createTargetFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, targetFragment)
            .commitAllowingStateLoss()
    }

    private fun createTargetFragment(): Fragment {
        return when (mCurPos) {
            FRAGMENT_FLOW_BASE -> FlowBaseFragment()
            FRAGMENT_FLOW_COUNT_DOWN -> FlowCountDownFragment()
            else -> FlowBaseFragment()
        }
    }

    override fun openWebview() {

        val blogUrl = when (mCurPos) {
            FRAGMENT_FLOW_BASE -> Constant.BLOG_JETPACK_FLOW
            FRAGMENT_FLOW_COUNT_DOWN -> Constant.BLOG_JETPACK_FLOW_COUNT_DOWN
            else -> Constant.BLOG_JETPACK_FLOW
        }
        CommonWebviewActivity.webviewEntrance(this, blogUrl)
    }

}