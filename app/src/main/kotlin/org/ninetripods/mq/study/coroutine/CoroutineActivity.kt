package org.ninetripods.mq.study.coroutine

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.coroutine.fragment.CoroutineBaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id

const val FRAGMENT_BASE = 0
const val KEY_FRAGMENT_TYPE = "key_fragment_type"

class CoroutineActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_BASE

    override fun setContentView() {
        setContentView(R.layout.activity_fragment_container)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_FRAGMENT_TYPE, FRAGMENT_BASE)
        val titleName = when (mCurPos) {
            FRAGMENT_BASE -> "Coroutine基本使用"
            else -> "Coroutine基本使用"
        }
        initToolBar(mToolBar, titleName, true, false, TYPE_BLOG)
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
            FRAGMENT_BASE -> CoroutineBaseFragment()
            else -> CoroutineBaseFragment()
        }
    }

}