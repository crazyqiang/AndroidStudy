package org.ninetripods.mq.study.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.ninetripods.mq.study.R

//左侧列表Fragment
class ItemFragment : Fragment() {
    private lateinit var mRvView: RecyclerView

    //Fragment之间通过传入同一个Activity来共享ViewModel
    private val mShareModel by lazy {
        ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(activity)
            .inflate(R.layout.layout_fragment_item, container, false)
        mRvView = view.findViewById(R.id.rv_view)
        mRvView.layoutManager = LinearLayoutManager(activity)
        mRvView.adapter = MyAdapter(mShareModel)

        return view
    }

    //构造RecyclerView的Adapter
    class MyAdapter(private val sViewModel: ShareViewModel) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

        class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mTvText: TextView = itemView.findViewById(R.id.tv_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_fragment_left, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val itemStr = "item pos:$position"
            holder.mTvText.text = itemStr
            //点击发送数据
            holder.itemView.setOnClickListener {
                sViewModel.clickItem(itemStr)
            }
        }

        override fun getItemCount(): Int {
            return 50
        }
    }
}