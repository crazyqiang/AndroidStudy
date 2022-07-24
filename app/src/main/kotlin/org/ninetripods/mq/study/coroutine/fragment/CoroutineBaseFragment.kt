package org.ninetripods.mq.study.coroutine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment

/**
 * 协程的基本使用
 */
class CoroutineBaseFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_coroutine_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            suspendFuc()
        }
    }

    private suspend fun suspendFuc(): String {
        delay(1000)
        return "value"
    }
}