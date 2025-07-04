package org.ninetripods.mq.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.activity.CommonFragmentsActivity
import org.ninetripods.mq.study.activity.RoundImageActivity
import org.ninetripods.mq.study.activity.ShadowActivity
import org.ninetripods.mq.study.activity.ShapeAbleViewActivity
import org.ninetripods.mq.study.activity.XFerModeActivity
import org.ninetripods.mq.study.fragment.ColorMatrixFragment
import org.ninetripods.mq.study.fragment.Inspector3DModeFragment
import org.ninetripods.mq.study.fragment.MatrixFragment
import org.ninetripods.mq.study.fragment.MutableContextWrapperFragment
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

/**
 * View相关
 */
class ViewFragment : BaseFragment() {
    companion object {
        const val TYPE_DEFAULT = 0 //默认
        const val TYPE_CONTEXT_WRAPPER = 1 //MutableContextWrapper示例
        const val TYPE_INSPECTOR_3D = 2 //Inspector 3DMode
        const val TYPE_MATRIX = 3 //Matrix
        const val TYPE_COLOR_MATRIX = 4 //ColorMatrix
    }
    data class ViewItem(val titleName: String, val clz: Class<*>, var type: Int = TYPE_DEFAULT)

    private val recyclerView: RecyclerView by id(R.id.rv_view)

    override fun getLayoutId(): Int = R.layout.fragment_view_series

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView.layoutManager = LinearLayoutManager(context)

        // 准备数据
        val dataList = mutableListOf<ViewItem>().apply {
            add(ViewItem("图片设置圆角矩形、圆形等", RoundImageActivity::class.java))
            add(ViewItem("ShapeableImageView", ShapeAbleViewActivity::class.java))
            add(ViewItem("设置阴影", ShadowActivity::class.java))
            add(ViewItem("PorterDuffXfermode", XFerModeActivity::class.java))
            add(ViewItem("MutableContextWrapper示例", CommonFragmentsActivity::class.java, TYPE_CONTEXT_WRAPPER))
            add(ViewItem("Inspector 3DMode", CommonFragmentsActivity::class.java, TYPE_INSPECTOR_3D))
            add(ViewItem("Matrix示例", CommonFragmentsActivity::class.java, TYPE_MATRIX))
            add(ViewItem("ColorMatrix示例", CommonFragmentsActivity::class.java, TYPE_COLOR_MATRIX))
        }

        // 设置适配器
        val adapter = MyAdapter(dataList) { _, item ->
            when (item.type) {
                TYPE_COLOR_MATRIX -> {
                    ColorMatrixFragment::class.java.canonicalName?.let {
                        CommonFragmentsActivity.start(requireActivity(), it, "ColorMatrix示例")
                    }
                }
                TYPE_MATRIX -> {
                    MatrixFragment::class.java.canonicalName?.let {
                        CommonFragmentsActivity.start(requireActivity(), it, "Matrix 示例")
                    }
                }
                TYPE_INSPECTOR_3D -> {
                    Inspector3DModeFragment::class.java.canonicalName?.let {
                        CommonFragmentsActivity.start(requireActivity(), it, "Inspector 3DMode")
                    }
                }
                TYPE_CONTEXT_WRAPPER -> {
                    //MutableContextWrapper示例
                    MutableContextWrapperFragment::class.java.canonicalName?.let {
                        CommonFragmentsActivity.start(requireActivity(), it, "MutableContextWrapper示例")
                    }
                }
                else -> {
                    NavitateUtil.startActivity(activity, item.clz)
                }
            }
        }
        recyclerView.adapter = adapter
    }

    class MyAdapter(
        private val dataList: List<ViewItem>,
        private val onItemClick: (Int, ViewItem) -> Unit
    ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_tv, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val data = dataList[position]
            holder.textView.text = data.titleName

            // 设置点击事件
            holder.itemView.setOnClickListener {
                onItemClick(position, data)
            }
        }

        override fun getItemCount(): Int = dataList.size

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView.findViewById(R.id.tv_title)
        }
    }

}