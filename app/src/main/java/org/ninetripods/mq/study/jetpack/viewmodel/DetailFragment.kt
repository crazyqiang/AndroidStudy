package org.ninetripods.mq.study.jetpack.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.ninetripods.mq.study.R

//右侧详情页Fragment
class DetailFragment : Fragment() {
    private lateinit var mTvDetail: TextView

    //Fragment之间通过传入同一个Activity来共享ViewModel
    private val mShareModel by lazy {
        ViewModelProvider(requireActivity()).get(ShareViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context)
            .inflate(R.layout.layout_fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvDetail = view.findViewById(R.id.tv_detail)
        //注册Observer并监听数据变化
        mShareModel.itemLiveData.observe(requireActivity(), { itemStr ->
            mTvDetail.text = itemStr
        })
    }
}