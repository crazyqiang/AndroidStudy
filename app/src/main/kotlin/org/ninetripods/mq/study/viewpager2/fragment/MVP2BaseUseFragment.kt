package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Build
import android.os.Bundle
import android.util.ArrayMap
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.ninetripods.sydialoglib.SYDialog
import org.ninetripods.lib_viewpager2.MVPager2
import org.ninetripods.lib_viewpager2.adapter.OnBannerClickListener
import org.ninetripods.lib_viewpager2.imageLoader.RoundImageLoader
import org.ninetripods.lib_viewpager2.transformer.DepthPageTransformer
import org.ninetripods.lib_viewpager2.transformer.ScaleInTransformer
import org.ninetripods.lib_viewpager2.transformer.ZoomOutPageTransformer
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.MConstant
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.showToast

class MVP2BaseUseFragment : BaseFragment() {
    companion object {
        const val MARGIN_TRANSFORMER = "MarginPageTransformer(左右边距30px)"
        const val ZOOM_OUT_TRANSFORMER = "ZoomOutPageTransformer"
        const val DEPTH_PAGE_TRANSFORMER = "DepthPageTransformer"
        const val SCALE_IN_TRANSFORMER = "ScaleInTransformer"
        const val MULTI_TRANSFORMER = "组合Transformer"
    }

    private var isIndicatorShow = true
    private var isHorizontal = true
    private var isMultiPageShow = true
    private val mMVPager2: MVPager2 by id(R.id.mvp_pager2)
    private val mTvOrientation: TextView by id(R.id.tv_orientation)
    private val mTvAdd: TextView by id(R.id.tv_add_one)
    private val mTvDel: TextView by id(R.id.tv_del_one)
    private val mTvPlay: TextView by id(R.id.tv_auto_play)
    private val mTvIndicator: TextView by id(R.id.tv_indicator)
    private val mTvMultiPage: TextView by id(R.id.tv_multi_page)
    private val mTvTransformer: TextView by id(R.id.tv_page_transformer)
    private val mTvItemLoader: TextView by id(R.id.tv_custom_loader)

    private val mModels = mutableListOf(MConstant.IMG_1, MConstant.IMG_2, MConstant.IMG_3)


    override fun getLayoutId(): Int {
        return R.layout.fragment_mvpager2_base
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initMvp2()
        mTvAdd.setOnClickListener {
            mModels.add(MConstant.IMG_2)
            mMVPager2.submitList(mModels)
        }
        mTvDel.setOnClickListener {
            if (mModels.isNotEmpty()) {
                mModels.removeAt(mModels.size - 1)
            }
            mMVPager2.submitList(mModels)
        }
        mTvOrientation.setOnClickListener {
            if (isHorizontal) {
                mMVPager2.setOrientation(MVPager2.ORIENTATION_VERTICAL).start()
                isHorizontal = false
                mTvOrientation.text = "水平方向滑动"
            } else {
                mMVPager2.setOrientation(MVPager2.ORIENTATION_HORIZONTAL).start()
                isHorizontal = true
                mTvOrientation.text = "竖直方向滑动"
            }
        }
        mTvPlay.setOnClickListener {
            if (mMVPager2.isAutoPlay()) {
                mMVPager2.setAutoPlay(false).start()
                mTvPlay.text = "开始自动轮播"
            } else {
                mMVPager2.setAutoPlay(true).start()
                mTvPlay.text = "停止自动轮播"
            }
        }
        mTvMultiPage.setOnClickListener {
            if (isMultiPageShow) {
                mMVPager2.setPagePadding(60, 0, 60, 0).start()
                mTvMultiPage.text = "一屏一页"
                isMultiPageShow = false
            } else {
                mMVPager2.setPagePadding(0, 0, 0, 0).start()
                mTvMultiPage.text = "一屏三页"
                isMultiPageShow = true
            }
        }

        mTvIndicator.setOnClickListener {
            if (isIndicatorShow) {
                mMVPager2.setIndicatorShow(false).start()
                isIndicatorShow = false
                mTvIndicator.text = "打开轮播指示器"
            } else {
                mMVPager2.setIndicatorShow(true).start()
                isIndicatorShow = true
                mTvIndicator.text = "关闭轮播指示器"
            }

        }
        mTvTransformer.setOnClickListener {
            //转换动画
            showTransformDialog()
        }

        mTvItemLoader.setOnClickListener {
            //自定义Item样式
            mMVPager2.setLoader(RoundImageLoader()).start()
        }
    }

    private fun initMvp2() {
        mMVPager2.setModels(mModels)
            .setIndicatorShow(true) //设置轮播指示器
            .setOffscreenPageLimit(1) //离屏缓存数量
            //.setPageTransformer(multiTransformer) //转换动画
            .setOrientation(MVPager2.ORIENTATION_HORIZONTAL) //轮播方向
            //.setUserInputEnabled(true) //控制是否可以触摸滑动
            .setAutoPlay(false) //设置自动轮播
            .setPageInterval(3000L) //轮播间隔
            .setAnimDuration(300) //切换动画执行时间
            .setOnBannerClickListener(object : OnBannerClickListener {
                override fun onItemClick(position: Int) {
                    //Item点击
                    showToast("position is $position")
                }
            })
            .start()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun showTransformDialog() {
        //在这里添加文案
        val transforms = mutableListOf<String>()
        transforms.add(MARGIN_TRANSFORMER)
        transforms.add(ZOOM_OUT_TRANSFORMER)
        transforms.add(DEPTH_PAGE_TRANSFORMER)
        transforms.add(SCALE_IN_TRANSFORMER)
        transforms.add(MULTI_TRANSFORMER)

        //通过map获取最终的ViewPager2.PageTransformer
        val transformMap = ArrayMap<String, ViewPager2.PageTransformer>()
        transformMap[MARGIN_TRANSFORMER] = MarginPageTransformer(30)
        transformMap[ZOOM_OUT_TRANSFORMER] = ZoomOutPageTransformer()
        transformMap[DEPTH_PAGE_TRANSFORMER] = DepthPageTransformer()
        transformMap[SCALE_IN_TRANSFORMER] = ScaleInTransformer()
        transformMap[MULTI_TRANSFORMER] = getMultiTransformer()

        SYDialog.Builder(context)
            .setDialogView(R.layout.layout_mvp_pager_transform)
            .setGravity(Gravity.BOTTOM)
            .setScreenWidthP(1.0f)
            .setAnimStyle(R.style.AnimUp)
            .setBuildChildListener { dialog, parent, layoutRes ->
                val recyclerView: RecyclerView = parent.findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL, false
                )
                val transformAdapter = TransformerAdapter(transforms)
                transformAdapter.setOnItemClickListener(object :
                    TransformerAdapter.OnItemListener {
                    override fun onItemClick(key: String) {
                        //获取对应的ViewPager2.PageTransformer
                        val targetTransformer = transformMap[key]
                        val multiTransformer = CompositePageTransformer()
                        multiTransformer.addTransformer(targetTransformer!!)
                        mMVPager2.setPageTransformer(multiTransformer).start()
                        dialog.dismiss()
                    }

                })
                recyclerView.adapter = transformAdapter
            }
            .show()
    }

    /**
     * 组合Transformer
     */
    private fun getMultiTransformer(): CompositePageTransformer {
        val multiTransformer = CompositePageTransformer()
        multiTransformer.addTransformer(ScaleInTransformer())
        multiTransformer.addTransformer(MarginPageTransformer(10))
        return multiTransformer
    }

    class TransformerAdapter(private val models: MutableList<String>) :
        RecyclerView.Adapter<TransformerAdapter.TransformHolder>() {
        private var listener: OnItemListener? = null

        interface OnItemListener {
            fun onItemClick(key: String)
        }

        fun setOnItemClickListener(listener: OnItemListener) {
            this.listener = listener
        }

        class TransformHolder(val container: View) : RecyclerView.ViewHolder(container)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformHolder {
            val textView = TextView(parent.context).apply {
                layoutParams =
                    ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            }
            textView.gravity = Gravity.CENTER
            textView.textSize = 16f
            textView.setTextColor(parent.context.resources.getColor(R.color.white))
            textView.setPadding(0, 30, 0, 30)
            return TransformHolder(textView).apply {
                textView.setOnClickListener {
                    listener?.onItemClick(models[bindingAdapterPosition])
                }
            }
        }

        override fun onBindViewHolder(holder: TransformHolder, position: Int) {
            (holder.container as TextView).text = models[position]
        }

        override fun getItemCount(): Int = models.size
    }
}