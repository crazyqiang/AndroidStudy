package org.ninetripods.lib_viewpager2

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OffscreenPageLimit
import org.ninetripods.lib_viewpager2.adapter.MVP2Adapter
import org.ninetripods.lib_viewpager2.adapter.SIDE_NUM
import org.ninetripods.lib_viewpager2.nestedScroll.log
import org.ninetripods.lib_viewpager2.imageLoader.DefaultLoader
import org.ninetripods.lib_viewpager2.imageLoader.ILoader
import org.ninetripods.lib_viewpager2.imageLoader.OnBannerClickListener
import org.ninetripods.lib_viewpager2.proxy.LayoutManagerProxy

class MVPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    //轮播指示器相关
    private var mLlIndicator: LinearLayoutCompat
    private var mIndicatorImgSelectedResId = R.drawable.circle_indicator_selected
    private var mIndicatorUnselectedResId = R.drawable.circle_indicator_unseclected
    private var mIndicatorImgs = ArrayList<ImageView>()
    private var mIndicatorImgSize: Int = 0
    private var mLastPosition: Int = 0
    private var mShowIndicator: Boolean = false //是否展示轮播指示器

    //ViewPager2
    private lateinit var mViewPager2: ViewPager2
    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var mRealCount: Int = 0 //VP2真实数量
    private lateinit var mVP2Adapter: MVP2Adapter<String>
    private var mModels: List<String> = ArrayList()
    private var mExtendModels: ArrayList<String> = ArrayList()
    private var mCurPos = SIDE_NUM //当前滑动到的位置
    private var mClickListener: OnBannerClickListener? = null
    private var mOffScreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
    private var mIsAutoPlay = true //自动轮播
    private var AUTO_PLAY_INTERVAL = 5 * 1000L//自动轮播时间间隔
    private var mCustomSwitchAnimDuration = 0 //自定义轮播切换持续时间
    private var mOrientation = ViewPager2.ORIENTATION_HORIZONTAL
    private var mPagerTransformer: CompositePageTransformer? = null
    private var mLoader: ILoader<View>? = null
    private var mSelectedValid: Boolean = true //滑动回调是否有效 默认有效
    private var mUserInputEnable: Boolean = true //设置VP2是否可以滑动
    private var mItemPaddingLeft: Int = 0 //Item之间的padding间隔
    private var mItemPaddingRight: Int = 0
    private var mItemPaddingTop: Int = 0
    private var mItemPaddingBottom: Int = 0

    private val autoRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mRealCount > 1 && mIsAutoPlay) {
                mCurPos = mCurPos % mExtendModels.size + 1
                log("mCurPos:$mCurPos , total: ${exFirstLastPos()}")
                when (mCurPos) {
                    exSecondLastPos() -> {
                        mSelectedValid = false
                        mViewPager2.setCurrentItem(1, false)
                        post(this)
                    }
                    else -> {
                        mSelectedValid = true
                        mViewPager2.currentItem = mCurPos
                        postDelayed(this, AUTO_PLAY_INTERVAL)
                    }
                }
            }
        }

    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_mvpager2, this)
        mViewPager2 = findViewById(R.id.vp_pager2)
        mLlIndicator = findViewById(R.id.ll_circle_indicator)
        mIndicatorImgSize = context.resources.displayMetrics.widthPixels / 75
    }

    /**
     * 设置数据
     * @param list 轮播数据Models
     */
    fun setModels(list: List<String>): MVPager2 {
        this.mModels = list
        this.mRealCount = mModels.size
        extendOriginModels()
        return this
    }

    /**
     * 设置自动轮播
     * @param isAutoPlay true-自动  false-手动
     */
    fun setAutoPlay(isAutoPlay: Boolean): MVPager2 {
        mIsAutoPlay = isAutoPlay
        return this
    }

    /**
     *设置ViewPager2是否可以滑动
     */
    fun setUserInputEnabled(inputEnable: Boolean): MVPager2 {
        this.mUserInputEnable = inputEnable
        return this
    }

    /**
     * 是否展示轮播指示器
     */
    fun setIndicatorShow(isIndicatorShow: Boolean): MVPager2 {
        this.mShowIndicator = isIndicatorShow
        return this
    }

    /**
     * 设置自动轮播时间间隔
     * @param autoInterval Page切换的时间间隔
     */
    fun setPageInterval(autoInterval: Long): MVPager2 {
        AUTO_PLAY_INTERVAL = autoInterval
        return this
    }

    /**
     * 设置轮播切换时的动画持续时间 通过反射改变系统自动切换的时间 注意：这里设置的switchDuration值需要小于
     * @see MVPager2.setPageInterval()中设置的autoInterval值
     * @param animDuration 动画切换持续时间
     */
    fun setAnimDuration(animDuration: Int): MVPager2 {
        this.mCustomSwitchAnimDuration = animDuration
        return this
    }

    /**
     * 设置离屏缓存数量 默认是OFFSCREEN_PAGE_LIMIT_DEFAULT = -1
     * @param limit 离屏缓存数量
     */
    fun setOffscreenPageLimit(@OffscreenPageLimit limit: Int): MVPager2 {
        mOffScreenPageLimit = limit
        return this
    }

    /**
     * 设置一屏多页
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    fun setPagePadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0): MVPager2 {
        mItemPaddingLeft = left
        mItemPaddingTop = top
        mItemPaddingRight = right
        mItemPaddingBottom = bottom
        return this
    }

    /**
     * 设置自定义动画
     * @param transformer CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer
     */
    fun setPageTransformer(transformer: CompositePageTransformer): MVPager2 {
        mPagerTransformer = transformer
        return this
    }

    /**
     * 设置Banner的Item点击
     * @param listener
     */
    fun setItemClickListener(listener: OnBannerClickListener): MVPager2 {
        this.mClickListener = listener
        return this
    }

    /**
     * 设置页面改变时的回调
     * @param callback 回调
     */
    fun registerOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback): MVPager2 {
        this.mOnPageChangeCallback = callback
        return this
    }

    /**
     * 设置轮播方向
     * @param orientation 横竖方向：ViewPager2.ORIENTATION_HORIZONTAL 或 ViewPager2.ORIENTATION_VERTICAL
     */
    fun setOrientation(@ViewPager2.Orientation orientation: Int): MVPager2 {
        mOrientation = orientation
        return this
    }

    /**
     * 设置View
     * @param loader View加载
     */
    fun setLoader(loader: ILoader<View>): MVPager2 {
        this.mLoader = loader
        return this
    }

    fun start() {
        initMVPager2()
        if (mCustomSwitchAnimDuration != 0) {
            initVP2LayoutManagerProxy()
        }
        initIndicator()
        mVP2Adapter = MVP2Adapter()
        mVP2Adapter.setModels(mExtendModels)
        mVP2Adapter.setImageLoader(if (mLoader != null) mLoader else DefaultLoader())
        mVP2Adapter.setOnItemClickListener(mClickListener)
        mViewPager2.adapter = mVP2Adapter
        mViewPager2.setCurrentItem(SIDE_NUM, false)
        if (mIsAutoPlay) startAutoPlay()
    }

    fun get(): ViewPager2 {
        return mViewPager2
    }

    /**
     * 初始化VP2
     */
    private fun initMVPager2() {
        mViewPager2.offscreenPageLimit = mOffScreenPageLimit
        mViewPager2.orientation = mOrientation
        if (mPagerTransformer != null) {
            mViewPager2.setPageTransformer(mPagerTransformer)
        }
        mViewPager2.isUserInputEnabled = mUserInputEnable
        //ViewPager2源码第254行,RecyclerView固定索引为0：
        //attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
        val innerRecyclerView = mViewPager2.getChildAt(0) as RecyclerView
        innerRecyclerView.apply {
            //这里会导致离屏预加载数+1
            setPadding(mItemPaddingLeft, mItemPaddingTop, mItemPaddingRight, mItemPaddingBottom)
            clipToPadding = false
            clipChildren = false
            //设置mCachedViews缓存大小 默认是2
            //setItemViewCacheSize(2)
        }

        mViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                mOnPageChangeCallback?.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
                //log("onPageScrollStateChanged: $state")
                //ViewPager2.SCROLL_STATE_DRAGGING 手指触摸滑动时才会触发
                if (mRealCount > 1 && (state == ViewPager2.SCROLL_STATE_DRAGGING)) {
                    when (mViewPager2.currentItem) {
                        exSecondPositive() -> {
                            //向左滑动，滑动到正数第2个时 自动将转换到倒数第3的位置(该位置为真实数量的最后一个)
                            mSelectedValid = false
                            mViewPager2.setCurrentItem(exThreeLastPos(), false)
                        }
                        exSecondLastPos() -> {
                            //向右滑动，滑动到倒数第2个时 自动将转换到正数第3的位置(该位置为真实数量的第一个)
                            mSelectedValid = false
                            mViewPager2.setCurrentItem(SIDE_NUM, false)
                        }
                        else -> {
                            mSelectedValid = true
                        }
                    }
                }
                if (mSelectedValid) {
                    mOnPageChangeCallback?.onPageScrollStateChanged(state)
                }
            }

            override fun onPageSelected(position: Int) {
                log("onPageSelected: $position , mSelectedValid: $mSelectedValid")
                mCurPos = position
                if (mSelectedValid) {
                    mOnPageChangeCallback?.onPageSelected(position)
                    mIndicatorImgs[getRealPosition(mLastPosition)].setImageResource(
                        mIndicatorUnselectedResId
                    )
                    mIndicatorImgs[getRealPosition(position)].setImageResource(
                        mIndicatorImgSelectedResId
                    )
                    mLastPosition = position
                }
            }
        })
    }

    /**
     * 代理LayoutManager 用来自定义轮播动画时长
     */
    private fun initVP2LayoutManagerProxy() {
        try {
            val innerRV = mViewPager2.getChildAt(0) as RecyclerView
            val originLManager = innerRV.layoutManager as LinearLayoutManager
            val layoutManagerProxy =
                LayoutManagerProxy(context, originLManager, mCustomSwitchAnimDuration)
            innerRV.layoutManager = layoutManagerProxy

            val mRecyclerView =
                RecyclerView.LayoutManager::class.java.getDeclaredField("mRecyclerView")
            mRecyclerView.isAccessible = true
            mRecyclerView.set(originLManager, innerRV)

            val layoutManagerField = ViewPager2::class.java.getDeclaredField("mLayoutManager")
            layoutManagerField.isAccessible = true
            layoutManagerField.set(mViewPager2, layoutManagerProxy)

            val originTransformerAdapter =
                ViewPager2::class.java.getDeclaredField("mPageTransformerAdapter")
            originTransformerAdapter.isAccessible = true
            val mPageTransformerAdapter = originTransformerAdapter[mViewPager2]
            if (mPageTransformerAdapter != null) {
                val originLayoutManager =
                    mPageTransformerAdapter.javaClass.getDeclaredField("mLayoutManager")
                originLayoutManager.isAccessible = true
                originLayoutManager.set(mPageTransformerAdapter, layoutManagerProxy)
            }

            val originEventAdapter =
                ViewPager2::class.java.getDeclaredField("mScrollEventAdapter")
            originEventAdapter.isAccessible = true
            val mScrollEventAdapter = originEventAdapter[mViewPager2]
            if (mScrollEventAdapter != null) {
                val originLayoutManager =
                    mScrollEventAdapter.javaClass.getDeclaredField("mLayoutManager")
                originLayoutManager.isAccessible = true
                originLayoutManager.set(mScrollEventAdapter, layoutManagerProxy)
            }
        } catch (ex: Exception) {
            log(ex.toString())
        }
    }

    /**
     * 初始化轮播指示器
     */
    private fun initIndicator() {
        mIndicatorImgs.clear()
        mLlIndicator.removeAllViews()
        for (i in 0 until mRealCount) {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            val params: LinearLayoutCompat.LayoutParams =
                LinearLayoutCompat.LayoutParams(mIndicatorImgSize, mIndicatorImgSize)
            params.leftMargin = 10
            params.rightMargin = 10
            imageView.setImageResource(if (i == 0) mIndicatorImgSelectedResId else mIndicatorUnselectedResId)
            mIndicatorImgs.add(imageView)
            mLlIndicator.addView(imageView, params)
        }
        mLlIndicator.visibility = if (mRealCount > 1 && mShowIndicator) View.VISIBLE else View.GONE
    }

    /**
     * 获取数据对应的真实位置
     * @param exPosition 扩展数据中的位置
     */
    private fun getRealPosition(exPosition: Int): Int {
        var realPos = (exPosition - SIDE_NUM) % mRealCount
        if (realPos < 0) realPos += mRealCount
        return realPos
    }

    private fun startAutoPlay() {
        removeCallbacks(autoRunnable)
        postDelayed(autoRunnable, AUTO_PLAY_INTERVAL)
    }

    private fun stopAutoPlay() {
        removeCallbacks(autoRunnable)
    }

    /**
     * 扩展原有数据
     */
    private fun extendOriginModels() {
        mExtendModels.clear()
        if (mRealCount > 1) {
            //真实数量必须大于1
            for (i in 0..exFirstLastPos()) {
                when (i) {
                    0 -> {
                        mExtendModels.add(mModels[mRealCount - 2])
                    }
                    1 -> {
                        mExtendModels.add(mModels[mRealCount - 1])
                    }
                    exSecondLastPos() -> {
                        mExtendModels.add(mModels[0])
                    }
                    exFirstLastPos() -> {
                        mExtendModels.add(mModels[1])
                    }
                    else -> {
                        mExtendModels.add(mModels[i - 2])
                    }
                }
            }
        } else {
            mExtendModels.add(mModels[0])
        }
        log("mExtendModels:$mExtendModels")
    }

    //扩展之后的倒数第1条数据
    private fun exFirstLastPos(): Int = mRealCount + 3

    //扩展之后的倒数第2条数据
    private fun exSecondLastPos(): Int = mRealCount + 2

    //扩展之后的倒数第3条数据
    private fun exThreeLastPos(): Int = mRealCount + 1

    //正数第2条数据
    private fun exSecondPositive(): Int = 1

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mIsAutoPlay) {
            val action = ev.action
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
                startAutoPlay()
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (hasWindowFocus) {
            if (mIsAutoPlay) startAutoPlay()
        } else {
            if (mIsAutoPlay) stopAutoPlay()
        }
    }

}