package org.ninetripods.mq.study.viewpager2.adapter

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.imageLoader.OnBannerClickListener
import org.ninetripods.lib_viewpager2.transformer.ScaleInTransformer
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

class NestedScrollItemFragment(val position: Int = 0) : BaseFragment() {

    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mRvTop: RecyclerView by id(R.id.rv_top)
    private val mRvBottom: RecyclerView by id(R.id.rv_bottom)
    private val mTvPagePos: TextView by id(R.id.tvPagePos)

    override fun getLayoutId(): Int {
        return R.layout.fragment_slide
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMvp2()
        mRvTop.setUpRecyclerView(RecyclerView.HORIZONTAL)
        mRvBottom.setUpRecyclerView(RecyclerView.VERTICAL)
        mTvPagePos.text = "Page$position"
    }

    private fun initMvp2() {
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        multiTransformer.addTransformer(MarginPageTransformer(20))

        mMVPager2.setModels(MConstant.urls)
            .setItemClickListener(object : OnBannerClickListener {
                override fun OnItemClick(position: Int) {
                    log("$position is click")
                }
            })
            .setIndicatorShow(true)
            .setOffscreenPageLimit(1)
            .setPageTransformer(multiTransformer)
            //.setAnimDuration(500)
            .setOrientation(MVPager2.HORIZONTAL)
            //.setUserInputEnabled(false)
            .setAutoPlay(true)
            .setPageInterval(5 * 1000L)
            .start()
    }

    private fun RecyclerView.setUpRecyclerView(orientation: Int) {
        layoutManager = LinearLayoutManager(context, orientation, false)
        adapter = RvAdapter(orientation)
    }

    class RvAdapter(private val orientation: Int) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
        class ViewHolder(val tv: TextView) : RecyclerView.ViewHolder(tv)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val tv = TextView(parent.context)
            tv.layoutParams = matchParent().apply {
                if (orientation == RecyclerView.HORIZONTAL) {
                    width = WRAP_CONTENT
                } else {
                    height = WRAP_CONTENT
                }
            }
            tv.textSize = 20f
            tv.gravity = Gravity.CENTER
            tv.setPadding(20, 55, 20, 55)
            return ViewHolder(tv)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            with(holder) {
                tv.text = "Item$bindingAdapterPosition"
                tv.setBackgroundResource(CELL_COLORS[position % CELL_COLORS.size])
            }
        }

        override fun getItemCount(): Int = 20
    }
}

internal fun matchParent(): ViewGroup.LayoutParams {
    return ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
}

internal val PAGE_COLORS = listOf(
    R.color.yellow_300,
    R.color.green_300,
    R.color.teal_300,
    R.color.blue_300
)

internal val CELL_COLORS = listOf(
    R.color.grey_100,
    R.color.grey_300
)
