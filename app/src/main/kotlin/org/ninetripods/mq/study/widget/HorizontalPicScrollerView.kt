package org.ninetripods.mq.study.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.OverScroller
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.widget.HorizontalPicScrollerView.Companion.TYPE_DATA
import org.ninetripods.mq.study.widget.HorizontalPicScrollerView.Companion.TYPE_LOAD_MORE
import kotlin.math.absoluteValue

/**
 * 横向滑动View
 */
class HorizontalPicScrollerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : LinearLayout(context, attrs, defStyle) {

    companion object {
        const val TYPE_DATA = 1 //正常样式
        const val TYPE_LOAD_MORE = 2 //加载更多样式

        const val PIC_LEAST_NUM = 6
    }

    private val mRvPic: RecyclerView by id(R.id.rv_pic)
    private val mLoadMoreContainer: LinearLayout by id(R.id.load_more_container)
    private var mNeedIntercept: Boolean = false //父View是否拦截事件

    private var mLastX = 0f
    private var mLastDownY = 0f
    private var mLastDownX = 0f //用于判断滑动方向

    private var mMenuWidth = 0 //加载更多View的宽度
    private var mShowMoreMenuWidth = 0 //加载更多发生变化时的宽度

    private var mLoadMoreAction: (() -> Unit)? = null
    private var mScroller: OverScroller
    private var isTouchLeft = false //是否是向左滑动

    init {
        orientation = HORIZONTAL
        View.inflate(context, R.layout.scroller_horizontal_pic, this)
        mScroller = OverScroller(context)
    }

    /**
     * @param models 要加载的数据
     * @param itemClick Item点击
     * @param loadMoreAction 查看更多
     */
    fun setData(
        models: MutableList<ItemPicInfo>,
        itemClick: (ItemPicInfo) -> Unit,
        loadMoreAction: () -> Unit,
    ) {
        this.mLoadMoreAction = loadMoreAction
        val layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        mRvPic.layoutManager = layoutManager
        val picAdapter = PicAdapter(context, loadMoreAction).apply {
            addPicList(models)
            setOnItemClickListener { item -> itemClick.invoke(item) }
        }
        mRvPic.adapter = picAdapter
        mRvPic.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                //滑动到末尾时，如果继续左滑，事件将由父view接管
                val lastVisiblePos = layoutManager.findLastCompletelyVisibleItemPosition()
                val visibleCount = layoutManager.childCount
                val totalCount = layoutManager.itemCount
                mNeedIntercept =
                    totalCount >= PIC_LEAST_NUM && visibleCount > 0 && lastVisiblePos == totalCount - 1
            }
        })
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mMenuWidth = mLoadMoreContainer.measuredWidth
        mShowMoreMenuWidth = mMenuWidth / 3 * 2
        super.onLayout(changed, l, t, r, b)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                mLastX = ev.x
                mLastDownY = ev.y
                mLastDownX = ev.x
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                isTouchLeft = mLastDownX - ev.x > 0 //判断滑动方向
                val dx = (ev.x - mLastDownX).absoluteValue
                val dy = (ev.y - mLastDownY).absoluteValue
                if (dy > dx) parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var isIntercept = false
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                val mDeltaX = mLastX - ev.x
                //向左滑动拦截，否则不拦截
                isIntercept = if (mDeltaX > 0) mNeedIntercept else false
            }
        }
        return isIntercept
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_MOVE -> {
                val mDeltaX = mLastX - ev.x
                if (mDeltaX > 0) {
                    //向左滑动
                    if (mDeltaX >= mMenuWidth || scrollX + mDeltaX >= mMenuWidth) {
                        //右边缘检测
                        scrollTo(mMenuWidth, 0)
                        return true
                    }
                } else if (mDeltaX < 0) {
                    //向右滑动
                    if (scrollX + mDeltaX <= 0) {
                        //左边缘检测
                        scrollTo(0, 0)
                        return true
                    }
                }
                scrollBy(mDeltaX.toInt(), 0)
                mLastX = ev.x
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                smoothCloseMenu()
                mNeedIntercept = false
                //执行回调
                val mDeltaX = mLastX - ev.x
                if (scrollX + mDeltaX >= mShowMoreMenuWidth) {
                    mLoadMoreAction?.invoke()
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    private fun smoothCloseMenu() {
        mScroller.forceFinished(true)
        /**
         * 左上为正，右下为负
         * startX：X轴开始位置
         * startY: Y轴结束位置
         * dx：X轴滑动距离
         * dy：Y轴滑动距离
         * duration：滑动时间
         */
        mScroller.startScroll(scrollX, 0, -scrollX, 0, 300)
        invalidate()
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX, mScroller.currY)
            invalidate()
        }
    }
}

class PicAdapter(private val mContext: Context, private val loadMoreAction: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mPicList: MutableList<ItemPicInfo> = mutableListOf()
    private var mItemClick: ((ItemPicInfo) -> Unit)? = null

    fun setOnItemClickListener(onItemClick: (ItemPicInfo) -> Unit) {
        this.mItemClick = onItemClick
    }

    fun addPicList(bookList: List<ItemPicInfo>) {
        mPicList.clear()
        mPicList.addAll(bookList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_LOAD_MORE) {
            LoadMoreHolder(
                LayoutInflater.from(mContext)
                    .inflate(R.layout.item_pic_load_more, parent, false)
            )
        } else {
            PicHolder(
                LayoutInflater.from(mContext)
                    .inflate(R.layout.item_pic_show_view, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (mPicList.isEmpty() || position > mPicList.lastIndex) return TYPE_DATA
        return mPicList[position].dataType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mPicList.isEmpty() || position > mPicList.lastIndex) return
        val model: ItemPicInfo = mPicList[position]
        if (holder is PicHolder) {
            clipToRoundView(holder.itemView)
            processOnPicInfo(holder, model)
            holder.itemView.setOnClickListener { mItemClick?.invoke(model) }
        } else if (holder is LoadMoreHolder) {
            holder.itemView.setOnClickListener { loadMoreAction.invoke() }
        }
    }

    override fun getItemCount(): Int = mPicList.size

    class LoadMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class PicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivPic: ImageView = itemView.findViewById(R.id.iv_book)
    }

    private fun processOnPicInfo(holder: PicHolder, model: ItemPicInfo) {
        //加载url
        Glide.with(holder.ivPic.context)
            .load(model.picUrl)
            .placeholder(R.drawable.icon_flower)
            .error(R.drawable.icon_flower)
            .into(holder.ivPic)
    }

    private fun clipToRoundView(view: View?) {
        view?.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                if (view == null) return
                //设置矩形
                outline?.setRoundRect(0, 0, view.width, view.height, 12.dp2px().toFloat())
            }
        }
        view?.clipToOutline = true
    }
}

data class ItemPicInfo(
    val picUrl: String = "",
    val dataType: Int = TYPE_DATA,
)

