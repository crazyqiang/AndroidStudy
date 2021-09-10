package org.ninetripods.mq.study.kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import org.ninetripods.mq.study.jetpack.mvvm.base.interf.IRootView

abstract class BaseFragment : Fragment(), IRootView {

    private var mRootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false)
        }
        return mRootView
    }

    override fun rootView(): View {
        return mRootView!!
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}