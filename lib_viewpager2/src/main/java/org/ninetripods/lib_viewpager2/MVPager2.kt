package org.ninetripods.lib_viewpager2

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OffscreenPageLimit
import org.ninetripods.lib_viewpager2.adapter.MVP2Adapter
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.lib_viewpager2.adapter.SIDE_NUM
import org.ninetripods.lib_viewpager2.imageLoader.DefaultLoader
import org.ninetripods.lib_viewpager2.imageLoader.ILoader
import org.ninetripods.lib_viewpager2.proxy.LayoutManagerProxy
import kotlin.math.absoluteValue

fun log(message: String) {
    if (!BuildConfig.DEBUG) return
    Log.d("MVPager2", message)
}

class MVPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : FrameLayout(context, attrs, defStyle) {

    companion object {
        const val ORIENTATION_HORIZONTAL = ViewPager2.ORIENTATION_HORIZONTAL
        const val ORIENTATION_VERTICAL = ViewPager2.ORIENTATION_VERTICAL

        //指示器gravity位置：上中下
        private const val INDICATOR_GRAVITY_TOP = 0
        private const val INDICATOR_GRAVITY_CENTER = 1
        private const val INDICATOR_GRAVITY_BOTTOM = 2
    }

    //轮播指示器相关
    @DrawableRes
    private var mIndicatorImgSelectedResId = R.drawable.circle_indicator_selected

    @DrawableRes
    private var mIndicatorUnselectedResId = R.drawable.circle_indicator_unseclected
    private var mClIndicator: ConstraintLayout
    private var mLlIndicator: LinearLayoutCompat
    private var mIndicatorImgSize: Int = 0
    private var mIndicatorMargin: Float = 0F
    private var mShowIndicator: Boolean = false //是否展示轮播指示器
    private var mIndicatorInside: Boolean = true //指示器是否在Banner内部
    private var mIndicatorBgHeight: Float = 0F
    private var mIndicatorWHRatio: Float = 2F //指示器宽高比
    private var mPrePosition = 0
    private var mIndicatorGravity: Int = INDICATOR_GRAVITY_CENTER //指示器Gravity

    //ViewPager2
    private lateinit var mViewPager2: ViewPager2
    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null
    private var mRealCount: Int = 0 //VP2真实数量
    private var mVP2Adapter: MVP2Adapter? = null
    private var mModels = mutableListOf<Any>()
    private var mExtendModels = mutableListOf<Any>()
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
    private var isLoop: Boolean = true //VP2是否是可以循环轮播的
    private var mItemPaddingLeft: Int = 0 //Item之间的padding间隔
    private var mItemPaddingRight: Int = 0
    private var mItemPaddingTop: Int = 0
    private var mItemPaddingBottom: Int = 0
    private var mTouchSlop = 0 //最小滑动距离
    private var mInitialX = 0f //初始化X轴坐标
    private var mInitialY = 0f //初始化Y轴坐标

    private val autoRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mRealCount > 1 && mIsAutoPlay) {
                mCurPos = mCurPos % mExtendModels.size + 1
                //log("autoScroll: mCurPos is $mCurPos , total is ${exFirstLastPos()}")
                when (mCurPos) {
                    //扩展数据之后，滑动到倒数第2条数据时，改变轮播位置
                    exSecondLastPos() -> {
                        mSelectedValid = false
                        //跳转到正数第2条数据，注意这里smoothScroll设置为false，即不会有跳转动画
                        mViewPager2.setCurrentItem(1, false)
                        //立即执行,会走到下面的else中去 最终会展示正数第3条的数据，达到无限轮播的效果
                        post(this)
                    }
                    exFirstLastPos() -> {
                        //向右滑动，滑动到倒数第1个时 自动将转换到正数第4的位置(该位置为真实数量的第2个)
                        mSelectedValid = false
                        mViewPager2.setCurrentItem(SIDE_NUM, false)
                        post(this)
                    }
                    else -> {
                        mSelectedValid = true
                        mViewPager2.currentItem = mCurPos
                        //延迟执行
                        postDelayed(this, AUTO_PLAY_INTERVAL)
                    }
                }
            }
        }

    }

    init {
        //获取自定义值
        val ta = context.obtainStyledAttributes(attrs, R.styleable.MVPager2)
        mIndicatorInside = ta.getBoolean(R.styleable.MVPager2_indicator_inside_banner, true)
        mIndicatorBgHeight = ta.getDimension(R.styleable.MVPager2_indicator_bg_height, 0F)
        mIndicatorImgSelectedResId = ta.getResourceId(
            R.styleable.MVPager2_indicator_drawable_selected, R.drawable.circle_indicator_selected
        )
        mIndicatorUnselectedResId = ta.getResourceId(
            R.styleable.MVPager2_indicator_drawable_unselected,
            R.drawable.circle_indicator_unseclected
        )
        mIndicatorImgSize = ta.getDimension(
            R.styleable.MVPager2_indicator_radius,
            (context.resources.displayMetrics.widthPixels / 80).toFloat()
        ).toInt()
        mIndicatorMargin = ta.getDimension(R.styleable.MVPager2_indicator_margin, 20F)
        mIndicatorWHRatio = ta.getFloat(R.styleable.MVPager2_indicator_w_h_ratio, 2f)
        mIndicatorGravity =
            ta.getInt(R.styleable.MVPager2_indicator_gravity, INDICATOR_GRAVITY_CENTER)
        ta.recycle()

        LayoutInflater.from(context).inflate(R.layout.layout_mvpager2, this)
        mViewPager2 = findViewById(R.id.vp_pager2)
        mLlIndicator = findViewById(R.id.ll_circle_indicator)
        mClIndicator = findViewById(R.id.cl_indicator)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    /**
     * @param isLoop 是否支持循环
     */
    fun setLoop(isLoop: Boolean): MVPager2 {
        this.isLoop = isLoop
        return this
    }

    /**
     * 设置数据
     * @param list 轮播数据Models
     */
    fun setModels(list: List<Any>): MVPager2 {
        this.mModels.clear()
        this.mModels.addAll(list)
        this.mRealCount = mModels.size
        return this
    }

    /**
     * 使用DiffUtil进行增量数据更新
     * @param newList 更新后的数据Models
     */
    fun submitList(newList: List<Any>) {
        if (mVP2Adapter == null) return
        if (newList.isEmpty()) {
            //展示默认图片
            showMainView(false)
            return
        }
        if (mIsAutoPlay) stopAutoPlay()
        this.mModels.clear()
        this.mModels.addAll(newList)
        this.mRealCount = mModels.size
        extendOriginModels()
        mVP2Adapter?.submitList(mExtendModels) //增量更新
        if (mIsAutoPlay) startAutoPlay()
        initIndicator()
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
     *设置ViewPager2是否可以滑动 默认为true
     * true-可以滑动 false-禁止滑动
     */
    fun setUserInputEnabled(inputEnable: Boolean): MVPager2 {
        this.mUserInputEnable = inputEnable
        return this
    }

    /**
     * 是否展示轮播指示器
     * true-展示 false-不展示
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
     * 设置轮播切换时的动画持续时间 通过反射改变系统自动切换的时间 注意：这里设置的animDuration值需要小于
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
     * 设置ItemView切换动画
     * @param transformer CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer
     */
    fun setPageTransformer(transformer: CompositePageTransformer): MVPager2 {
        mPagerTransformer = transformer
        return this
    }

    /**
     * 设置Banner的ItemView点击
     * @param listener
     */
    fun setOnBannerClickListener(listener: OnBannerClickListener): MVPager2 {
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
     * @param orientation 横竖方向：ORIENTATION_HORIZONTAL 或 ORIENTATION_VERTICAL
     */
    fun setOrientation(@ViewPager2.Orientation orientation: Int): MVPager2 {
        mOrientation = orientation
        return this
    }

    /**
     * 设置ItemView加载器
     * @param loader View加载
     */
    fun setLoader(loader: ILoader<View>): MVPager2 {
        this.mLoader = loader
        return this
    }

    fun start() {
        extendOriginModels() //如果是循环模式，则会先扩展数据
        initMVPager2()
        if (mCustomSwitchAnimDuration != 0) {
            initVP2LayoutManagerProxy()
        }
        post {
            if (mIndicatorBgHeight != 0F) {
                //设置了指示器高度
                mClIndicator.layoutParams.height = mIndicatorBgHeight.toInt()
                if (!mIndicatorInside) {
                    //指示器在Banner外部 重新设置mViewPager2的高度
                    mViewPager2.layoutParams.height = height - mIndicatorBgHeight.toInt()
                }
            } else {
                //未设置指示器高度 走默认指示器高度
                if (!mIndicatorInside) {
                    //指示器在Banner外部 重新设置mViewPager2的高度  mClIndicator.height:默认的指示器高度
                    mViewPager2.layoutParams.height = height - mClIndicator.height
                }
            }
            mViewPager2.requestLayout()
        }
        mVP2Adapter = MVP2Adapter()
        mVP2Adapter?.let {
            it.setLoop(isLoop)
            it.setModels(mExtendModels)
            it.setImageLoader(if (mLoader != null) mLoader else DefaultLoader())
            it.setOnItemClickListener(mClickListener)
        }
        mViewPager2.adapter = mVP2Adapter
        mViewPager2.setCurrentItem(if (isLoop) SIDE_NUM else 0, false)
        if (mIsAutoPlay) startAutoPlay()
        initIndicator()
    }

    fun get(): ViewPager2 {
        return mViewPager2
    }

    /**
     * 是否自动轮播
     */
    fun isAutoPlay(): Boolean = mIsAutoPlay

    /**
     * 初始化VP2
     */
    private fun initMVPager2() {
        mViewPager2.apply {
            offscreenPageLimit = mOffScreenPageLimit
            orientation = mOrientation
            isUserInputEnabled = mUserInputEnable
            if (mPagerTransformer != null) {
                setPageTransformer(mPagerTransformer)
            }
            //ViewPager2源码第254行,RecyclerView固定索引为0：
            //attachViewToParent(mRecyclerView, 0, mRecyclerView.getLayoutParams());
            val innerRecyclerView = mViewPager2.getChildAt(0) as RecyclerView
            innerRecyclerView.apply {
                overScrollMode = View.OVER_SCROLL_NEVER //去掉滑动到边缘时的光晕效果
                //这里会导致离屏预加载数+1
                if (mItemPaddingLeft != 0 || mItemPaddingTop != 0 || mItemPaddingRight != 0 || mItemPaddingBottom != 0) {
                    setPadding(
                        mItemPaddingLeft, mItemPaddingTop, mItemPaddingRight, mItemPaddingBottom
                    )
                }
                clipToPadding = false
                clipChildren = false
                //设置mCachedViews缓存大小 默认是2
                //setItemViewCacheSize(2)
            }
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                    mOnPageChangeCallback?.onPageScrolled(
                        position,
                        positionOffset,
                        positionOffsetPixels
                    )
                }

                override fun onPageScrollStateChanged(state: Int) {
                    //ViewPager2.SCROLL_STATE_DRAGGING 手指触摸滑动时才会触发
                    if (mRealCount > 1 && (state == ViewPager2.SCROLL_STATE_DRAGGING) && isLoop) {
                        when (mViewPager2.currentItem) {
                            exFirstPositive() -> {
                                //向左滑动，滑动到正数第1个时 自动将转换到倒数第4的位置(该位置为真实数量的倒数第2个)
                                mSelectedValid = false
                                mViewPager2.setCurrentItem(exFourLastPos(), false)
                            }
                            exSecondPositive() -> {
                                //向左滑动，滑动到正数第2个时 自动将转换到倒数第3的位置(该位置为真实数量的最后1个)
                                mSelectedValid = false
                                mViewPager2.setCurrentItem(exThreeLastPos(), false)
                            }
                            exSecondLastPos() -> {
                                //向右滑动，滑动到倒数第2个时 自动将转换到正数第3的位置(该位置为真实数量的第1个)
                                mSelectedValid = false
                                mViewPager2.setCurrentItem(SIDE_NUM, false)
                            }
                            exFirstLastPos() -> {
                                //向右滑动，滑动到倒数第1个时 自动将转换到正数第4的位置(该位置为真实数量的第2个)
                                mSelectedValid = false
                                mViewPager2.setCurrentItem(SIDE_NUM + 1, false)
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
                    log("onPageSelected: $position ,total: ${mExtendModels.size}," +
                            " mSelectedValid: $mSelectedValid, mPrePosition:$mPrePosition")
                    mCurPos = position
                    if (mSelectedValid) {
                        mOnPageChangeCallback?.onPageSelected(position)
                        updateIndicator(mPrePosition, position)
                        mPrePosition = position
                    }
                }
            })
        }
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
        mLlIndicator.removeAllViews()
        when (mIndicatorGravity) {
            INDICATOR_GRAVITY_TOP -> mLlIndicator.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            INDICATOR_GRAVITY_CENTER -> mLlIndicator.gravity = Gravity.CENTER
            INDICATOR_GRAVITY_BOTTOM -> mLlIndicator.gravity =
                Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        }
        var mCurPos = getRealPosition(mViewPager2.currentItem)
        if (mCurPos >= mRealCount) {
            //兜底
            mCurPos = 0
            mViewPager2.setCurrentItem(mCurPos, false)
        }
        for (i in 0 until mRealCount) {
            val imageView = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                var mFinalWidth = mIndicatorImgSize
                if (i == mCurPos) {
                    //选中的指示器的宽度
                    mFinalWidth = (mIndicatorImgSize * mIndicatorWHRatio).toInt()
                }
                val linearLayoutParams: LinearLayoutCompat.LayoutParams =
                    LinearLayoutCompat.LayoutParams(mFinalWidth, mIndicatorImgSize)
                linearLayoutParams.leftMargin = mIndicatorMargin.toInt()
                layoutParams = linearLayoutParams
                setImageResource(if (i == mCurPos) mIndicatorImgSelectedResId else mIndicatorUnselectedResId)
            }
            mLlIndicator.addView(imageView)
        }
        mLlIndicator.visibility = if (mRealCount > 1 && mShowIndicator) View.VISIBLE else View.GONE
    }

    /**
     * 更新指示器选中与未选中时的状态
     * @param prePos 上次指示器位置
     * @param nowPos 当前指示器位置
     */
    private fun updateIndicator(prePos: Int, nowPos: Int) {
        if (prePos == nowPos) return
        val nowRealPos = getRealPosition(nowPos)
        val preRealPos = getRealPosition(prePos)
        if (mLlIndicator.isEmpty() || nowRealPos >= mRealCount || preRealPos >= mRealCount || nowRealPos == preRealPos) return
        val selectedView = mLlIndicator.getChildAt(nowRealPos) as ImageView
        selectedView.apply {
            layoutParams.width = (mIndicatorImgSize * mIndicatorWHRatio).toInt()
            layoutParams.height = mIndicatorImgSize
            setImageResource(mIndicatorImgSelectedResId)
        }

        val unSelectedView = mLlIndicator.getChildAt(preRealPos) as ImageView
        unSelectedView.apply {
            layoutParams.width = mIndicatorImgSize
            layoutParams.height = mIndicatorImgSize
            setImageResource(mIndicatorUnselectedResId)
        }
        mLlIndicator.requestLayout()
    }

    /**
     * 获取数据对应的真实位置
     * @param exPosition 扩展数据中的位置
     */
    private fun getRealPosition(exPosition: Int): Int {
        if (mRealCount == 0) return mRealCount
        if (!isLoop) return exPosition //如果不是循环模式 当前位置就是真实位置
        var realPos = (exPosition - SIDE_NUM) % mRealCount
        if (realPos < 0) realPos += mRealCount
        return realPos
    }

    private fun startAutoPlay() {
        removeCallbacks(autoRunnable)
        if (!isLoop) return
        postDelayed(autoRunnable, AUTO_PLAY_INTERVAL)
    }

    private fun stopAutoPlay() {
        removeCallbacks(autoRunnable)
    }

    /**
     * 扩展原有数据
     */
    private fun extendOriginModels() {
        if (mRealCount == 0) {
            //传入的数据为空，展示默认图
            showMainView(false)
            return
        }
        mExtendModels.clear()
        showMainView(true)
        if (!isLoop) {
            //非循环模式下 mExtendModels == mModels
            mExtendModels.addAll(mModels)
            return
        }
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
    }

    private fun showMainView(isVisible: Boolean = true) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    //扩展之后的倒数第1条数据
    private fun exFirstLastPos(): Int = mRealCount + 3

    //扩展之后的倒数第2条数据
    private fun exSecondLastPos(): Int = mRealCount + 2

    //扩展之后的倒数第3条数据
    private fun exThreeLastPos(): Int = mRealCount + 1

    //扩展之后的倒数第4条数据
    private fun exFourLastPos(): Int = mRealCount

    //正数第2条数据
    private fun exSecondPositive(): Int = 1

    //正数第1条数据
    private fun exFirstPositive(): Int = 0

    /**
     * 手指触摸时停止自动轮播
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (mUserInputEnable && isLoop) {
            val action = ev.action
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
                if (mIsAutoPlay) startAutoPlay()
            } else if (action == MotionEvent.ACTION_DOWN) {
                if (mIsAutoPlay) removeCallbacks(autoRunnable)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 处理嵌套滑动冲突
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        handleInterceptTouchEvent(ev)
        return super.onInterceptTouchEvent(ev)
    }

    private fun handleInterceptTouchEvent(ev: MotionEvent) {
        val orientation = mViewPager2.orientation
        if (mRealCount <= 0 || !mUserInputEnable) {
            parent.requestDisallowInterceptTouchEvent(false)
            return
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                mInitialX = ev.x
                mInitialY = ev.y
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = (ev.x - mInitialX).absoluteValue
                val dy = (ev.y - mInitialY).absoluteValue
                if (dx > mTouchSlop || dy > mTouchSlop) {
                    val disallowIntercept =
                        (orientation == ViewPager2.ORIENTATION_HORIZONTAL && dx > dy)
                                || (orientation == ViewPager2.ORIENTATION_VERTICAL && dx < dy)
                    parent.requestDisallowInterceptTouchEvent(disallowIntercept)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
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