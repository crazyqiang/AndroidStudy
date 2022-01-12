package org.ninetripods.mq.study.viewpager2.fragment

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.viewpager2.adapter.VP2FragmentAdapter
import org.ninetripods.mq.study.viewpager2.model.VP2Model

class NestedScrollFragment : BaseFragment() {
    private val mViewPager2: ViewPager2 by id(R.id.view_pager2)

    override fun getLayoutId(): Int {
        return R.layout.activity_nested_scroll_vp2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = VP2FragmentAdapter(requireActivity())
        val models = mutableListOf<VP2Model>()
        for (i in 0 until 5) {
            models.add(VP2Model(id = i, content = "fragment$i"))
        }
        adapter.setDatas(models)
        mViewPager2.adapter = adapter
    }

}