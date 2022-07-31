package org.ninetripods.mq.study.coroutine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * 协程的基本使用
 */
class CoroutineBaseFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_coroutine_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val exceptionHandler =
            CoroutineExceptionHandler { context, throwable -> print("throwable:$throwable") }

        lifecycleScope.launch(
            context = Dispatchers.Main + Job() + CoroutineName("MyCoroutines") + exceptionHandler,
            start = CoroutineStart.LAZY) {
            suspendFuc()
        }
    }

    private suspend fun suspendFuc(): String {
        delay(1000)
        return "value"
    }

    class MyElement : AbstractCoroutineContextElement(MyElement) {
        public companion object Key : CoroutineContext.Key<MyElement>
    }
}