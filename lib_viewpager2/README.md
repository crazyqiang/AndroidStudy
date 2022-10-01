## 版本升级日志

**Version 1.0.0-rc3** (2022.10.01)<br>
1、MVPager2 Banner支持非轮播模式

## 一 效果图
|功能  | 示例  |
|--|--|
| **基本使用** | <img src="https://img-blog.csdnimg.cn/3be2a0c909ef4f258e41d7eb9c8a3f22.gif" width="300">  |
| **仿淘宝搜索栏上下轮播** | <img src="https://img-blog.csdnimg.cn/46946afd62884207b758e79ab845d8ae.gif" width="300">|
| **仿淘宝、京东Banner滑动查看图文详情** | <img src="https://img-blog.csdnimg.cn/d4abcaaa422c4651bd63d7580c5d71de.gif" width="300">|

### 使用方式
```
val mModels = mutableListOf(MConstant.IMG_1, MConstant.IMG_2, MConstant.IMG_3)

//多个转换动画
val multiTransformer = CompositePageTransformer()
multiTransformer.addTransformer(MarginPageTransformer(20))
multiTransformer.addTransformer(ZoomOutPageTransformer())

mMVPager2.setModels(mModels) //设置轮播数据
    .setIndicatorShow(true) //设置轮播指示器
    .setOffscreenPageLimit(1) //离屏缓存数量
    .setLoader(DefaultLoader()) //设置ItemView加载器 可以自定义Item样式
    .setPagePadding(50, 0, 50, 0) //设置一屏三页
    .setPageTransformer(multiTransformer) //转换动画
    .setOrientation(MVPager2.ORIENTATION_HORIZONTAL) //轮播方向
    .setUserInputEnabled(true) //控制是否可以触摸滑动 默认为true
    .setAutoPlay(false) //设置自动轮播
    .setPageInterval(3000L) //轮播间隔
    .setAnimDuration(500) //切换动画执行时间
    .setOnBannerClickListener(object : OnBannerClickListener {
        override fun onItemClick(position: Int) {
            //Item点击
            showToast("position is $position")
        }
     })
    .registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
      //设置页面改变时的回调
    })
    .start() //开始
```
如果需要刷新整体数据，可以像下面进行增量更新：
```
//使用DiffUtil进行增量数据更新 newList：更新后的数据Models
mMVPager2.submitList(newList)
```
**注意**：使用增量更新时，如果开发语言是`Java`，需要针对实体类重写`hashCode()`、`equals()`方法，否则增量更新可能会失效；而如果开发语言为`kotlin`，则实体类(data class xxx)不需要特殊处理，因为系统已经自动帮我们重写了这两个方法。

### 1.1 API介绍
| API  | 备注  |
|:--|:--|
| setModels(list: MutableList< String>)  | 设置轮播数据  |
| submitList(newList: MutableList< String>) | 使用DiffUtil进行增量数据更新 |
| setAutoPlay(isAutoPlay: Boolean) | 设置自动轮播 true-自动  false-手动 |
| setUserInputEnabled(inputEnable: Boolean) | 设置MVPager2是否可以滑动 true-可以滑动 false-禁止滑动 |
| setIndicatorShow(isIndicatorShow: Boolean) | 是否展示轮播指示器 true-展示 false-不展示 |
| setPageInterval(autoInterval: Long) | 设置自动轮播时间间隔 |
| setAnimDuration(animDuration: Int) | 设置轮播切换时的动画持续时间 通过反射改变系统自动切换的时间<br> **注意**：这里设置的animDuration值需要小于setPageInterval()中设置的autoInterval值 |
| setOffscreenPageLimit(@OffscreenPageLimit limit: Int) | 设置离屏缓存数量 默认是OFFSCREEN_PAGE_LIMIT_DEFAULT = -1 |
| setPagePadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) | 设置一屏多页 |
| setPageTransformer(transformer: CompositePageTransformer) | 设置ItemView切换动画， CompositePageTransformer可以同时添加多个ViewPager2.PageTransformer |
| setOnBannerClickListener(listener: OnBannerClickListener) | 设置Banner的ItemView点击 |
| registerOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) | 设置页面改变时的回调 |
| setOrientation(@ViewPager2.Orientation orientation: Int) | 设置轮播方向，横竖方向：ORIENTATION_HORIZONTAL 或 ORIENTATION_VERTICAL |
| setLoader(loader: ILoader< View>) | 设置ItemView加载器 |
| isAutoPlay() | 是否自动轮播 |

## 二 核心实现思路
### 2.1 无限轮播
为了实现无限轮播，首先对原始数据进行扩充，如下图所示：<br>
<img src="https://img-blog.csdnimg.cn/450e47c4009f4db9ac21a34a05dde993.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBALeWwj-mprOW_q-i3kS0=,size_20,color_FFFFFF,t_70,g_se,x_16" width="700"><br>

在真实数据的前后各增加2条数据，添加规则已经在图片中注明了。
```
private val autoRunnable: Runnable = object : Runnable {
        override fun run() {
            if (mRealCount > 1 && mIsAutoPlay) {
                mCurPos = mCurPos % mExtendModels.size + 1
                when (mCurPos) {
                    //扩展数据之后，滑动到倒数第2条数据时，改变轮播位置
                    exSecondLastPos() -> {
                        mSelectedValid = false
                        //跳转到正数第2条数据，注意这里smoothScroll设置为false，即不会有跳转动画
                        mViewPager2.setCurrentItem(1, false)
                        //立即执行,会走到下面的else中去 最终会展示正数第3条的数据，达到无限轮播的效果
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
```
上面注释中已经将无限轮播的逻辑写明了。以上图扩展后的数据为例，当`VP2`滑动到第6条数据(`position`是5，`value`是a)时，立即跳转到第2条数据(`position`是1，`value`是c)，但是此时还未来得及展示，立即会通过`post(this)`继续执行，从而跳转到了第3条数据(`position`是2，`value`是a)，可以看到跟第6条的数据是一样的，从而达到了无限轮播的效果。当设置完上述的`Runnable`后，通过`Handler`发送`Message`开始执行循环:
```
fun startAutoPlay() {
   removeCallbacks(autoRunnable)
   postDelayed(autoRunnable, AUTO_PLAY_INTERVAL)
}
```
以上是自动轮播的实现场景，另外还有手动轮播，主要是在`ViewPager2.OnPageChangeCallback#onPageScrollStateChanged(state: Int)`回调中根据`VP2.currentItem`得到当前`Item`的位置判断下一个滑动位置的，具体跳转逻辑跟自动轮播是一样的。这里注意一点：`state `必须是`ViewPager2.SCROLL_STATE_DRAGGING`，因为这个值可以确保只在手指触摸滑动时才会触发，自动轮播时并不会触发这里的逻辑。

### 2.2 轮播动画过渡
主要通过`LayoutManager#smoothScrollToPosition()`中通过`LinearSmoothScroller#calculateTimeForScrolling()`自定义速率：
```
/**
 * 自定义LinearLayoutManager，自定义轮播速率
 */
class LayoutManagerProxy(
    val context: Context,
    private val layoutManager: LinearLayoutManager,
    private val customSwitchAnimDuration: Int = 0,
) : LinearLayoutManager(
    context, layoutManager.orientation, false
) {

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        val linearSmoothScroller =
            LinearSmoothScrollerProxy(context, customSwitchAnimDuration)
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

  internal class LinearSmoothScrollerProxy(
        context: Context,
        private val customSwitchAnimDuration: Int = 0
    ) : LinearSmoothScroller(context) {

        /**
         * 控制轮播切换速度
         */
        override fun calculateTimeForScrolling(dx: Int): Int {
            return if (customSwitchAnimDuration != 0)
                customSwitchAnimDuration
            else
                super.calculateTimeForScrolling(dx)
        }
    }
}
```
### 2.3 处理嵌套滑动冲突
上篇文章中已经介绍过如果处理滑动冲突，这里先将代码贴出来：
```
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
```
主要就是在`onInterceptTouchEvent`中通过内部拦截法`requestDisallowInterceptTouchEvent()`进行处理，如果嵌套滑动中的内部控件需要滑动时，就控制外部`父View`不拦截事件，设置为`requestDisallowInterceptTouchEvent(true)`；反之则让外部`父View`拦截事件，设置为`requestDisallowInterceptTouchEvent(false)`。

`MotionEvent.ACTION_DOWN`状态时一定不能让`父View`拦截，否则后续事件都不会传入`子View`中了；`MotionEvent.ACTION_MOVE`状态时根据`VP2`的方向及滑动距离判断，当是`横向滑动`且`X轴距离>Y轴距离`或当是`竖直滑动`且`Y轴距离>X轴距离`时，都会控制`父View`不拦截事件。
### 2.4 配合DiffUtil增量更新
```
class PageDiffUtil(private val oldModels: List<Any>, private val newModels: List<Any>) :
    DiffUtil.Callback() {

    /**
     * 旧数据
     */
    override fun getOldListSize(): Int = oldModels.size

    /**
     * 新数据
     */
    override fun getNewListSize(): Int = newModels.size

    /**
     * DiffUtil调用来决定两个对象是否代表相同的Item。true表示两个Item相同(表示View可以复用)，false表示不相同(View不可以复用)
     * 例如，如果你的项目有唯一的id，这个方法应该检查它们的id是否相等。
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModels[oldItemPosition]::class.java == newModels[newItemPosition]::class.java
    }

    /**
     * 比较两个Item是否有相同的内容(用于判断Item的内容是否发生了改变)，
     * 该方法只有当areItemsTheSame (int, int)返回true时才会被调用。
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModels[oldItemPosition] == newModels[newItemPosition]
    }

    /**
     * 该方法执行时机：areItemsTheSame(int, int)返回true 并且 areContentsTheSame(int, int)返回false
     * 该方法返回Item中的变化数据，用于只更新Item中变化数据对应的UI
     */
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}
```
调用方：
```
    /**
     * use[DiffUtil] 增量更新数据
     * @param newList 新数据
     */
    fun submitList(newList: MutableList<String>) {
        //传入新旧数据进行比对
        val diffUtil = PageDiffUtil(mModels, newList)
        //经过比对得到差异结果
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        //NOTE:注意这里要重新设置Adapter中的数据
        setModels(newList)
        //将数据传给adapter，最终通过adapter.notifyItemXXX更新数据
        diffResult.dispatchUpdatesTo(this)
    }
```
### 2.5 自定义Item样式
首先定义一个接口，接口中的两个方法分别用来创建`ItemView`及对`ItemView`进行赋值：
```
interface ILoader<T : View> {
    fun createView(context: Context): T
    fun display(context: Context, content: Any, targetView: T)
}
```
 `ItemView`基类，默认创建的是`ImageView`：
```
abstract class BaseLoader : ILoader<View> {

    override fun createView(context: Context): View {
        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return imageView
    }
}
```
默认`DefaultLoader`继承自`BaseLoader`并在`display()`中通过`Glide`加载`ImageView`：
```
/**
 * 默认为ImageView加载
 */
class DefaultLoader : BaseLoader() {

    override fun createView(context: Context): View {
        return super.createView(context)
    }

    override fun display(context: Context, content: Any, targetView: View) {
        Glide.with(context).load(content).into(targetView as ImageView)
    }
}
```
当然，如果不想加载`ImageView`，可以在子类中进行重写，比如我们想创建的`ItemView`是一个`TextView`，可以像下面这么写：
```
/**
 * TextView视图
 */
class TextLoader : BaseLoader() {

    @ColorRes
    private var mBgColor: Int = R.color.white

    @ColorRes
    private var mTextColor: Int = R.color.black
    private var mTextGravity: Int = Gravity.CENTER
    private var mTextSize: Float = 14f

    override fun createView(context: Context): View {
        val frameLayout = FrameLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(context.resources.getColor(mBgColor))
        }
        val textView = TextView(context).apply {
            gravity = mTextGravity
            setTextColor(context.resources.getColor(mTextColor))
            textSize = mTextSize
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        frameLayout.addView(textView)
        return frameLayout
    }

    override fun display(context: Context, content: Any, targetView: View) {
        val frameLayout = targetView as FrameLayout
        val childView = frameLayout.getChildAt(0)
        if (childView is TextView) {
            childView.text = content.toString()
        }
    }

    fun setBgColor(@ColorRes bgColor: Int): TextLoader {
        this.mBgColor = bgColor
        return this
    }

    fun setTextColor(@ColorRes textColor: Int): TextLoader {
        this.mTextColor = textColor
        return this
    }

    fun setGravity(gravity: Int): TextLoader {
        this.mTextGravity = gravity
        return this
    }

    fun setTextSize(textSize: Float): TextLoader {
        this.mTextSize = textSize
        return this
    }
}
```
最终是在`RecyclerView.Adapter`中如下调用：
```
class MVP2Adapter : RecyclerView.Adapter<MVP2Adapter.PageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        //创建要显示的ItemView
        var itemShowView = mLoader?.createView(parent.context)
        return PageViewHolder(itemShowView)
        }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
          val contentStr = mModels[position]
          //ItemView展示数据
          mLoader?.display(holder.itemShowView.context, contentStr, holder.itemShowView)
    }
}
```
`通过接口的方式将具体实现进行隔离，对扩展开放，对修改关闭，达到了开闭效果`。调用方如果想自定义`Item`样式，可以自行实现`ILoader`并实现自己想要的样式即可。
