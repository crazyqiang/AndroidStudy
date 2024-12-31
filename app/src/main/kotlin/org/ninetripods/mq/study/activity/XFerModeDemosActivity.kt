package org.ninetripods.mq.study.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.activity.XFerModeActivity.Companion.FRAGMENT_X_BASIC
import org.ninetripods.mq.study.activity.XFerModeActivity.Companion.FRAGMENT_X_GGK
import org.ninetripods.mq.study.fragment.XFerModeBasicFragment
import org.ninetripods.mq.study.fragment.XFerModeGGKFragment
import org.ninetripods.mq.study.kotlin.ktx.id

class XFerModeDemosActivity : BaseActivity() {
    companion object {
        const val KEY_POS = "key_position"
        const val KEY_NAME = "key_name"
        fun start(context: Context, position: Int, name: String) {
            val intent = Intent(context, XFerModeDemosActivity::class.java).apply {
                putExtra(KEY_POS, position)
                putExtra(KEY_NAME, name)
            }
            context.startActivity(intent)
        }
    }

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos = FRAGMENT_X_BASIC
    private var mTitleName = ""

    override fun setContentView() {
        setContentView(R.layout.activity_xfermode_demo)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_POS, FRAGMENT_X_BASIC)
        mTitleName = intent.getStringExtra(KEY_NAME) ?: ""
        initToolBar(mToolBar, mTitleName, true, false)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, getFragment(mCurPos))
            .commitAllowingStateLoss()
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            FRAGMENT_X_BASIC -> XFerModeBasicFragment()
            FRAGMENT_X_GGK -> XFerModeGGKFragment()
            else -> XFerModeBasicFragment()
        }
    }
}