package org.ninetripods.mq.study.viewpager2

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.imageLoader.IClickListener
import org.ninetripods.lib_viewpager2.imageLoader.TextLoader
import org.ninetripods.lib_viewpager2.transformer.ScaleInTransformer
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import org.ninetripods.mq.study.viewpager2.adapter.ViewPager2Adapter
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class ViewPager2Activity : BaseActivity() {

    private val mViewPager2: ViewPager2 by id(R.id.vp2_fragment)

    private val mViewVp2: MVPager2 by id(R.id.vp2_view)
    private val mViewVp3: MVPager2 by id(R.id.vp2_view2)
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
            "https://cdn03.pinkoi.com/pinkoi.magz/tvzvjB9F/14650149183643.jpg",
            "https://n.sinaimg.cn/spider20201221/308/w1143h765/20201221/4bba-kfnaptu6435105.jpg",
            "https://images.zi.org.tw/bigfang/2020/02/19222035/1582122033-6062688041443908d6d864b6722c38ff.jpg"
        )
        val list = ArrayList<String>()
        for (i in colors.indices) {
            list.add(colors[i])
        }
//        val viewAdapter = MVP2Adapter<String>()
//        viewAdapter.setData(list)

        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        //设置Margin
        multiTransformer.addTransformer(MarginPageTransformer(20))

        mViewVp2.setModels(list)
            .setOnBannerClickListener(object : IClickListener {
                override fun onItemClick(position: Int) {
                    log("$position is click")
                }
            }).setOffscreenPageLimit(1)
            .setPageTransformer(multiTransformer)
            .setOrientation(ViewPager2.ORIENTATION_HORIZONTAL)
            .setItemPadding(100, 0, 100, 0)
            .setAutoPlay(true)
            .setAutoInterval(5 * 1000L)
            .start()



        mViewVp3.setModels(list)
            .setOnBannerClickListener(object : IClickListener {
                override fun onItemClick(position: Int) {
                    log("$position is click")
                }
            })
            .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
            .setAutoInterval(4000)
            .setAutoPlay(false)
            .setLoader(TextLoader())
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

        mViewVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                //log("onPageSelected: $position")
            }
        })

    }

    private var realCount: Int = 0

    /**
     * Fragment VP2
     */
    private fun initFragmentVP2() {
//        initViewPagerScrollProxy()

        val pagerAdapter = ViewPager2Adapter(this)

        val colors = arrayOf(
            "#CCFF99", "#CCFF99"
        )
        val list = ArrayList<VP2Model>()
        val count = colors.size
        realCount = count
        if (count > 1) {
            //真实数量必须大于1
            for (i in 0..count + 3) {
                when (i) {
                    0 -> {
                        val lastIndex = count - 2
                        list.add(VP2Model(lastIndex, lastIndex.toString(), colors[lastIndex]))
                    }
                    1 -> {
                        val lastIndex = count - 1
                        list.add(VP2Model(lastIndex, lastIndex.toString(), colors[lastIndex]))
                    }
                    count + 2 -> {
                        list.add(VP2Model(0, 0.toString(), colors[0]))
                    }
                    count + 3 -> {
                        list.add(VP2Model(1, 1.toString(), colors[1]))
                    }
                    else -> {
                        val realIndex = i - 2
                        list.add(VP2Model(realIndex, realIndex.toString(), colors[realIndex]))
                    }
                }
            }
        } else {
            list.add(VP2Model(0, 0.toString(), colors[0]))
        }

        pagerAdapter.setDatas(list)
        mViewPager2.adapter = pagerAdapter
        mViewPager2.setCurrentItem(2, false)

        //CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer
        val multiTransformer = CompositePageTransformer()
        //multiTransformer.addTransformer(ScaleInTransformer())
        //设置Margin
        multiTransformer.addTransformer(MarginPageTransformer(1))
        mViewPager2.setPageTransformer(multiTransformer)

        //设置离屏数量
        mViewPager2.offscreenPageLimit = 1

        /**
         * ViewPager2内部第254行,RecyclerView固定索引为0：
         * attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
         */
        val recyclerView = mViewPager2.getChildAt(0) as RecyclerView
        recyclerView.apply {
            //这里会导致离屏预加载数+1
            setPadding(100, 0, 100, 0)
            clipToPadding = false
            clipChildren = false

            //设置mCachedViews缓存大小 默认是2
            //setItemViewCacheSize(2)
        }

//        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageScrollStateChanged(state: Int) {
//                log("onPageScrollStateChanged: $state")
//                if (pagerAdapter.itemCount <= 1) return
//                if (state == ViewPager2.SCROLL_STATE_IDLE) {
//                    if (mViewPager2.currentItem == 1) {
////                        if (pagerAdapter.itemCount <= 2) {
////                            mViewPager2.setCurrentItem(realCount + 1, false)
////                        } else {
//                       // mViewPager2.setCurrentItem(realCount + 1, false)
////                        }
//                        log("realCount0: $realCount")
//                    } else if (mViewPager2.currentItem == realCount + 2) {
//                        log("realCount1: $realCount")
//                        mViewPager2.setCurrentItem(2, false)
//                    }
//                }
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
////                log("onPageScrolled: $position, $positionOffset, $positionOffsetPixels")
//            }
//
//            override fun onPageSelected(position: Int) {
//                log("onPageSelected: $position")
//            }
//        })
    }

    private fun initViewPagerScrollProxy() {
        try {
            //控制切换速度，采用反射方。法方法只会调用一次，替换掉内部的RecyclerView的LinearLayoutManager
            val recyclerView = mViewPager2.getChildAt(0) as RecyclerView
            recyclerView.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val o = recyclerView.layoutManager as LinearLayoutManager
            val proxyLayoutManger: ProxyLayoutManger = ProxyLayoutManger(this, o)
            recyclerView.layoutManager = proxyLayoutManger

            val mRecyclerView =
                RecyclerView.LayoutManager::class.java.getDeclaredField("mRecyclerView")
            mRecyclerView.isAccessible = true
            mRecyclerView[o] = recyclerView

            val layoutMangerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
            layoutMangerField.isAccessible = true
            layoutMangerField[mViewPager2] = proxyLayoutManger

            val pageTransformerAdapterField =
                ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
            pageTransformerAdapterField.isAccessible = true
            val mPageTransformerAdapter = pageTransformerAdapterField[mViewPager2]
            if (mPageTransformerAdapter != null) {
                val aClass: Class<*> = mPageTransformerAdapter.javaClass
                val layoutManager = aClass.getDeclaredField("mLayoutManager")
                layoutManager.isAccessible = true
                layoutManager[mPageTransformerAdapter] = proxyLayoutManger
            }

            val scrollEventAdapterField =
                ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
            scrollEventAdapterField.isAccessible = true
            val mScrollEventAdapter = scrollEventAdapterField[mViewPager2]
            if (mScrollEventAdapter != null) {
                val aClass: Class<*> = mScrollEventAdapter.javaClass
                val layoutManager = aClass.getDeclaredField("mLayoutManager")
                layoutManager.isAccessible = true
                layoutManager[mScrollEventAdapter] = proxyLayoutManger
            }
        } catch (e: Exception) {
            e.printStackTrace()
            log("ex1:$e")
        }
    }

    private class ProxyLayoutManger internal constructor(
        context: Context?,
        layoutManager: LinearLayoutManager
    ) :
        LinearLayoutManager(context, layoutManager.orientation, false) {
        private val layoutManager: RecyclerView.LayoutManager
        override fun performAccessibilityAction(
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State, action: Int, args: Bundle?
        ): Boolean {
            return layoutManager.performAccessibilityAction(recycler, state, action, args)
        }

        override fun onInitializeAccessibilityNodeInfo(
            recycler: RecyclerView.Recycler,
            state: RecyclerView.State, info: AccessibilityNodeInfoCompat
        ) {
            layoutManager.onInitializeAccessibilityNodeInfo(recycler, state, info)
        }

        override fun requestChildRectangleOnScreen(
            parent: RecyclerView,
            child: View, rect: Rect, immediate: Boolean,
            focusedChildVisible: Boolean
        ): Boolean {
            return layoutManager.requestChildRectangleOnScreen(
                parent,
                child,
                rect,
                immediate,
                focusedChildVisible
            )
        }

        override fun calculateExtraLayoutSpace(
            state: RecyclerView.State,
            extraLayoutSpace: IntArray
        ) {
            try {
                val method = layoutManager.javaClass.getDeclaredMethod(
                    "calculateExtraLayoutSpace",
                    state.javaClass,
                    extraLayoutSpace.javaClass
                )
                method.isAccessible = true
                method.invoke(layoutManager, state, extraLayoutSpace)
            } catch (e: Exception) {
                e.printStackTrace()
                log("ex0:$e")
            }
        }

        override fun smoothScrollToPosition(
            recyclerView: RecyclerView,
            state: RecyclerView.State,
            position: Int
        ) {
            val linearSmoothScroller: LinearSmoothScroller =
                object : LinearSmoothScroller(recyclerView.context) {
                    override fun calculateTimeForDeceleration(dx: Int): Int {
                        return ((800f * (1 - .3356)).toInt())
                    }
                }
            linearSmoothScroller.targetPosition = position
            startSmoothScroll(linearSmoothScroller)
        }

        init {
            this.layoutManager = layoutManager
        }
    }

}