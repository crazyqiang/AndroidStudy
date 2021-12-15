package org.ninetripods.mq.study.viewpager2

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.viewpager2.adapter.VP2Adapter
import org.ninetripods.mq.study.viewpager2.adapter.ViewPager2Adapter
import org.ninetripods.mq.study.viewpager2.model.VP2Model
import org.ninetripods.mq.study.viewpager2.transformer.ScaleInTransformer

class ViewPager2Activity : BaseActivity() {

    private val mViewPager2: ViewPager2 by id(R.id.vp2_fragment)

    private val mViewVp2: ViewPager2 by id(R.id.vp2_view)
    private val mTlIndicator: TabLayout by id(R.id.tl_indicator)
    private val mBtnSlide: Button by id(R.id.btn_slide)

    override fun setContentView() {
        setContentView(R.layout.activity_view_pager2)
    }

    /**
     * NOTE: 查看Log日志时将下面的方法都注释，只留需要调试的那个
     */
    override fun initEvents() {
        initViewVP2()
//        initFragmentVP2()
    }

    /**
     * 普通View VP2
     */
    private fun initViewVP2() {
        val colors = arrayOf(
            "#CCFF99", "#41F1E5", "#8D41F1",
            "#FF99CC", "#CCFF99", "#41F1E5", "#8D41F1", "#FF99CC"
        )
        val list = ArrayList<VP2Model>()
        for (i in colors.indices) {
            list.add(VP2Model(i, i.toString(), colors[i]))
        }
        val viewAdapter = VP2Adapter()
        viewAdapter.setData(list)
        mViewVp2.adapter = viewAdapter

        //VP2关联TabLayout
        TabLayoutMediator(mTlIndicator, mViewVp2) { tab, position ->
            tab.text = position.toString()
        }.attach()

        mViewVp2.offscreenPageLimit = 1 //设置离屏数量
        //mViewVp2.isUserInputEnabled = false //禁止滑动

        //fakeDragBy模拟滑动
//        mBtnSlide.setOnClickListener {
//            mViewVp2.beginFakeDrag()
//            if (mViewVp2.fakeDragBy(-50f)) {
//                mViewVp2.endFakeDrag()
//            }
//        }

        mViewVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                log("onPageSelected: $position")
            }
        })

    }

    /**
     * Fragment VP2
     */
    private fun initFragmentVP2() {
        val pagerAdapter = ViewPager2Adapter(this)
        mViewPager2.adapter = pagerAdapter

        //CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        //设置Margin
        multiTransformer.addTransformer(MarginPageTransformer(10))
        mViewPager2.setPageTransformer(multiTransformer)

        //设置离屏数量
        //mViewPager2.offscreenPageLimit = 1

        /**
         * ViewPager2内部第254行,RecyclerView固定索引为0：
         * attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
         */
        val recyclerView = mViewPager2.getChildAt(0) as RecyclerView
        recyclerView.apply {
            //这里会导致离屏预加载数+1
            setPadding(20, 0, 20, 0)
            clipToPadding = false

            //设置mCachedViews缓存大小 默认是2
            //setItemViewCacheSize(2)
        }

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                log("curPos: $position")
            }
        })
    }

}