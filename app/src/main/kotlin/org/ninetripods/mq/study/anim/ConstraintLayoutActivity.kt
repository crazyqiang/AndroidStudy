package org.ninetripods.mq.study.anim

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.anim.fragment.ImgFilterViewFragment
import org.ninetripods.mq.study.kotlin.ktx.id

const val FRAGMENT_IMG_FILTER = 0
const val KEY_CL_FRAGMENT_TYPE = "key_fragment_type"
/**
 * ConstraintLayout系列
 */
class ConstraintLayoutActivity : BaseActivity() {
    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_IMG_FILTER

    override fun setContentView() {
        setContentView(R.layout.activity_fragment_container)
    }

    override fun initViews() {
        mCurPos = intent.getIntExtra(KEY_CL_FRAGMENT_TYPE, FRAGMENT_IMG_FILTER)
        val titleName = when (mCurPos) {
            FRAGMENT_IMG_FILTER -> "ImageFilterView"
            else -> "ImageFilterView"
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
            FRAGMENT_IMG_FILTER -> ImgFilterViewFragment()
            else -> ImgFilterViewFragment()
        }
    }

}