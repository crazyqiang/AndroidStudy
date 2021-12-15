package org.ninetripods.mq.study.viewpager2.adapter

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

class VP2Fragment(val position: Int = 0) : BaseFragment() {

    private val mTvContent: TextView by id(R.id.tv_content)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        log("pos$position: onAttach()")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("pos$position: onCreate()")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_slide
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvContent.text = position.toString()
        log("pos$position: onViewCreated()")
    }

    override fun onStart() {
        super.onStart()
        log("pos$position: onStart()")
    }

    override fun onResume() {
        super.onResume()
        log("pos$position: onResume()")
    }

    override fun onPause() {
        super.onPause()
        log("pos$position: onPause()")
    }

    override fun onStop() {
        super.onStop()
        log("pos$position: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        log("pos$position: onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("pos$position: onDestroy()")
    }

    override fun onDetach() {
        super.onDetach()
        log("pos$position: onDetach()")
    }

}