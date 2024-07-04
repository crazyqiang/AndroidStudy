package org.ninetripods.mq.study.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.util.DpUtil

/**
 * 弹幕View
 */
class DanMuView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val INTERVAL = 14L
    }

    private var mRow: Int = 1 //弹幕行数
    private var rvDanMu: RecyclerView? = null
    private val slideRunnable = object : Runnable {
        override fun run() {
            rvDanMu?.let {
                it.scrollBy(5, 0) // 这里控制滚动速度
                it.postDelayed(this, INTERVAL)
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_danmu_rv, this)
        rvDanMu = findViewById(R.id.rv_dan_mu)
        if (context is ComponentActivity) {
            context.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    //log("DanMuView onResume")
                    startPlay()
                }

                override fun onPause(owner: LifecycleOwner) {
                    //log("DanMuView onPause")
                    stopPlay()
                }
            })
        }
    }

    /**
     * @param row 弹幕行数
     */
    fun setRow(row: Int) {
        this.mRow = row
    }

    /**
     * @param contentList 数据
     * @param row 设置成几行
     */
    @SuppressLint("ClickableViewAccessibility")
    fun setModels(contentList: List<String>, startFromEnd: Boolean = true) {
        if (contentList.isEmpty()) return
        val viewAdapter = BarrageAdapter(contentList, mRow, startFromEnd)
        rvDanMu?.run {
            layoutManager = StaggeredGridLayoutManager(mRow, StaggeredGridLayoutManager.HORIZONTAL)
            adapter = viewAdapter
            //屏蔽滑动
            setOnTouchListener { _, _ -> true }
        }
    }

    /**
     * 停止轮播
     */
    fun stopPlay() {
        removeCallbacks(slideRunnable)
        visibility = View.VISIBLE
    }

    /**
     * 开始轮播
     */
    fun startPlay() {
        removeCallbacks(slideRunnable)
        postDelayed(slideRunnable, INTERVAL)
        visibility = View.VISIBLE
    }

    class BarrageAdapter(
        private val dataList: List<String>,
        private val row: Int,
        private val startFromEnd: Boolean
    ) :
        RecyclerView.Adapter<BarrageAdapter.ViewDataHolder>() {

        class ViewDataHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.tvText)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDataHolder {
            return ViewDataHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.layout_danmu_1, parent, false)
            )
        }

        override fun onBindViewHolder(holder: ViewDataHolder, position: Int) {
            if (dataList.isEmpty()) return
            holder.textView.run {
                val params = layoutParams
                if (startFromEnd) {
                    //弹幕从第二屏开始滑动
                    if (position < row) {
                        //首屏空白
                        val screenWidth = DpUtil.getScreenSizeWidth(context)
                        when (position) {
                            1 -> params.width = screenWidth + 30.dp2px()
                            2 -> params.width = screenWidth + 10.dp2px()
                            else -> params.width = ViewGroup.LayoutParams.MATCH_PARENT
                        }
                        visibility = View.INVISIBLE
                    } else {
                        //弹幕从第二屏开始显示
                        val realIndex = if (position - row > 0) position - row else 0
                        val textStr = dataList[realIndex % dataList.size]
                        params.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        visibility = if (textStr.isNotEmpty()) View.VISIBLE else View.GONE
                        text = textStr// 无限循环
                    }
                    layoutParams = params
                } else {
                    //弹幕从第一屏开始滑动
                    val textStr = dataList[position % dataList.size]
                    visibility = if (textStr.isNotEmpty()) View.VISIBLE else View.GONE
                    text = textStr// 无限循环
                }
            }
        }

        override fun getItemCount(): Int {
            return Int.MAX_VALUE // 无限循环
        }
    }

}