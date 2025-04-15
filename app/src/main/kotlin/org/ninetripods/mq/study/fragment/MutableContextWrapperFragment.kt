package org.ninetripods.mq.study.fragment

import android.content.Context
import android.content.ContextWrapper
import android.content.MutableContextWrapper
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.activity.CommonFragmentsActivity
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * MutableContextWrapper示例
 */
class MutableContextWrapperFragment : BaseFragment() {
    private val mContainer1: FrameLayout by id(R.id.fl_container1)
    private val mContainer2: FrameLayout by id(R.id.fl_container2)

    override fun getLayoutId(): Int {
        return R.layout.layout_mutable_context_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = requireContext()
        addChild(context, mContainer1, R.string.button_text_1)
        addChild(context, mContainer2, R.string.button_text_2)
    }

    private fun addChild(context: Context, parentView: ViewGroup, textId: Int) {
        val button = LayoutInflater.from(context).inflate(R.layout.layout_child_button, parentView, false)
        (button as? Button)?.run {
            setText(textId)
            setOnClickListener {
                MutableContextWrapperFragment2::class.java.canonicalName?.let {
                    CommonFragmentsActivity.start(requireActivity(), it, "MutableContextWrapper示例New")
                }
            }
        }
        parentView.addView(button)
    }

}

