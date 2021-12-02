package org.ninetripods.mq.study

import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id

class ViewPager2Fragment(val position: Int = 0) : BaseFragment() {

    private val mTvContent: TextView by id(R.id.tv_content)

    override fun getLayoutId(): Int {
        return R.layout.fragment_slide
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvContent.text = position.toString()
    }
}