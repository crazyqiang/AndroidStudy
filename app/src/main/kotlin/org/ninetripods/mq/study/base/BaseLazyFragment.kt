package org.ninetripods.mq.study.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * 懒加载Fragment
 */
abstract class BaseLazyFragment : Fragment() {

    private var mIsFirstLoad = true //是否是首次加载

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            val rootView = inflater.inflate(getLayoutId(), container, false)
            initViews(rootView)
            return rootView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (mIsFirstLoad) {
            initData()
            mIsFirstLoad = false
        }
    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected fun initViews(view: View) {}

    protected fun initData() {}

}