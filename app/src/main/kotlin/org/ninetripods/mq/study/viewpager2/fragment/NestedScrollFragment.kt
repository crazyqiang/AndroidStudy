package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.viewpager2.adapter.NestedFragmentAdapter
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class NestedScrollFragment : BaseFragment() {
    private val mViewPager2: ViewPager2 by id(R.id.view_pager2)

    override fun getLayoutId(): Int {
        return R.layout.activity_nested_scroll_vp2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NestedFragmentAdapter(requireActivity())
        val models = mutableListOf<VP2Model>()
        for (i in 0 until 7) {
            models.add(VP2Model(id = i, content = "fragment$i"))
        }
        adapter.setModels(models)
        mViewPager2.adapter = adapter
        //mViewPager2.offscreenPageLimit = 1

        //设置预抓取
        (mViewPager2.getChildAt(0) as RecyclerView).layoutManager?.apply {
            isItemPrefetchEnabled = true
        }
    }

}