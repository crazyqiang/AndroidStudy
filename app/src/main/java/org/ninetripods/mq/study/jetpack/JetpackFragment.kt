package org.ninetripods.mq.study.jetpack

import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.jetpack.mvvm.DrinkMvvmActivity
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

private const val ARG_PARAM1 = "param1"

class JetpackFragment : BaseFragment() {

    private var param1: String? = null
    private val mTvMvvm: TextView by id(R.id.tv_mvvm)

    companion object {
        @JvmStatic
        fun newInstance(param1: String = "") =
            JetpackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_jetpack
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mTvMvvm.setOnClickListener {
            NavitateUtil.startActivity(activity, DrinkMvvmActivity::class.java)
        }
    }


}