package org.ninetripods.mq.study.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.dp2px
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.createDivider
import org.ninetripods.mq.study.widget.XfermodeImgView
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_CIRCLE
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_DIAMOND
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_HEART
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_LEAF
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_ORIGIN
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_ROUND
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_ROUND_SUB
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_STAR
import org.ninetripods.mq.study.widget.XfermodeImgView.Companion.SHAPE_TRI

/**
 * 图片处理
 */
class XFerModeIMGFragment : BaseFragment() {

    data class ViewItem(val titleName: String, val position: Int)

    private val recyclerView: RecyclerView by id(R.id.rv_view)

    override fun getLayoutId(): Int {
        return R.layout.layout_rv
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        context?.let { recyclerView.createDivider(it, 5.dp2px().toFloat()) }

        // 准备数据
        val dataList = mutableListOf<ViewItem>().apply {
            add(ViewItem("原图", SHAPE_ORIGIN))
            add(ViewItem("圆角矩形", SHAPE_ROUND))
            add(ViewItem("圆角角度不一致", SHAPE_ROUND_SUB))
            add(ViewItem("圆形", SHAPE_CIRCLE))
            add(ViewItem("五角星", SHAPE_STAR))
            add(ViewItem("心形", SHAPE_HEART))
            add(ViewItem("叶子", SHAPE_LEAF))
            add(ViewItem("三角形", SHAPE_TRI))
            add(ViewItem("菱形", SHAPE_DIAMOND))
        }
        // 设置适配器
        val adapter = MyAdapter(dataList) { _, item -> }
        recyclerView.adapter = adapter
    }

    class MyAdapter(
        private val dataList: List<ViewItem>,
        private val onItemClick: (Int, ViewItem) -> Unit
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_xfermode_img, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = dataList[position]
            val context = holder.itemView.context
            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.icon_cat_w)
            holder.img.setImageBitmap(bitmap, data.position)
            // 设置点击事件
            holder.itemView.setOnClickListener {
                onItemClick(position, data)
            }
        }

        override fun getItemCount(): Int = dataList.size

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val img: XfermodeImgView = itemView.findViewById(R.id.iv_xfer_img)
        }
    }
}

