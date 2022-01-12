package org.ninetripods.mq.study.viewpager2

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.imageLoader.OnBannerClickListener
import org.ninetripods.lib_viewpager2.imageLoader.TextLoader
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.viewpager2.fragment.MVP2BaseUseFragment
import org.ninetripods.mq.study.viewpager2.fragment.NestedScrollFragment
import org.ninetripods.mq.study.viewpager2.fragment.SearchVScrollFragment

const val FRAGMENT_BASE = 0
const val FRAGMENT_NESTED_SCROLL = 1
const val FRAGMENT_SEARCH_V_SCROLL = 2

class ViewPager2Activity : BaseActivity() {

    private val mViewPager2: ViewPager2 by id(R.id.vp2_fragment)

    private val mViewVp2: MVPager2 by id(R.id.vp2_view)
    private val mViewVp3: MVPager2 by id(R.id.vp2_view2)
    private val mTlIndicator: TabLayout by id(R.id.tl_indicator)

    private val mToolBar: Toolbar by id(R.id.toolbar)
    private var mCurPos: Int = FRAGMENT_NESTED_SCROLL

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2)
    }

    override fun initViews() {
        initToolBar(mToolBar, "ViewPager2", true, false, TYPE_BLOG)
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
            FRAGMENT_BASE -> MVP2BaseUseFragment.newInstance()
            FRAGMENT_NESTED_SCROLL -> NestedScrollFragment()
            FRAGMENT_SEARCH_V_SCROLL -> SearchVScrollFragment.newInstance()
            else -> MVP2BaseUseFragment.newInstance()
        }
    }

    /**
     * 普通View VP2
     */
    private fun initViewVP2() {
        val textArray = listOf("锄禾日当午", "汗滴禾下土", "谁知盘中餐", "粒粒皆辛苦")
        mViewVp3.setModels(textArray)
            .setItemClickListener(object : OnBannerClickListener {
                override fun OnItemClick(position: Int) {
                    log("$position is click")
                }
            })
            .registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    //log("onPageSelected: $position")
                }
            })
            .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            .setPageInterval(5000)
            .setAnimDuration(300)
            .setAutoPlay(true)
            .setLoader(
                TextLoader().setBgColor(R.color.word_normal_color).setTextColor(R.color.white)
            )
            .start()

//        mViewVp2.adapter = viewAdapter

        //VP2关联TabLayout
//        TabLayoutMediator(mTlIndicator, mViewVp2.get()!!) { tab, position ->
//            tab.text = position.toString()
//        }.attach()

//        mViewVp2.offscreenPageLimit = 1 //设置离屏数量
        //mViewVp2.isUserInputEnabled = false //禁止滑动

        //fakeDragBy模拟滑动
//        mBtnSlide.setOnClickListener {
//            mViewVp2.beginFakeDrag()
//            if (mViewVp2.fakeDragBy(-50f)) {
//                mViewVp2.endFakeDrag()
//            }
//        }

    }
}