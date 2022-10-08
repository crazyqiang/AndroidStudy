package org.ninetripods.mq.study.jetpack

import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.coroutine.CoroutineDispatchActivity
import org.ninetripods.mq.study.jetpack.datastore.DataStoreActivity
import org.ninetripods.mq.study.jetpack.lifecycle.LifecycleActivity
import org.ninetripods.mq.study.jetpack.livedata.LiveDataActivity
import org.ninetripods.mq.study.jetpack.mvi.MviExampleActivity
import org.ninetripods.mq.study.jetpack.mvvm.MvvmExampleActivity
import org.ninetripods.mq.study.jetpack.viewmodel.ViewModelActivity
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.util.NavitateUtil

private const val ARG_PARAM1 = "param1"

/**
 * Kotlin & Jetpack
 */
class JetpackAndKtFragment : BaseFragment() {

    private var param1: String? = null
    private val mTvLifecycle: TextView by id(R.id.tv_lifecycle)
    private val mTvLiveData: TextView by id(R.id.tv_livedata)
    private val mTvViewModel: TextView by id(R.id.tv_viewmodel)
    private val mTvMvvm: TextView by id(R.id.tv_mvvm)
    private val mTvMvi: TextView by id(R.id.tv_mvi)
    private val mTvDataStore: TextView by id(R.id.tv_datastore)
    private val mTvKotlin: TextView by id(R.id.tv_kt)

    companion object {
        @JvmStatic
        fun newInstance(param1: String = "") =
            JetpackAndKtFragment().apply {
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
        mTvKotlin.setOnClickListener {
            NavitateUtil.startActivity(activity, CoroutineDispatchActivity::class.java)
        }
        mTvLifecycle.setOnClickListener {
            NavitateUtil.startActivity(activity, LifecycleActivity::class.java)
        }
        mTvLiveData.setOnClickListener {
            NavitateUtil.startActivity(activity, LiveDataActivity::class.java)
        }
        mTvViewModel.setOnClickListener {
            NavitateUtil.startActivity(activity, ViewModelActivity::class.java)
        }
        mTvMvvm.setOnClickListener {
            NavitateUtil.startActivity(activity, MvvmExampleActivity::class.java)
        }
        mTvMvi.setOnClickListener {
            NavitateUtil.startActivity(activity, MviExampleActivity::class.java)
        }
        mTvDataStore.setOnClickListener {
            NavitateUtil.startActivity(activity, DataStoreActivity::class.java)
        }
    }


}