package org.ninetripods.mq.study.coroutine.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.base.BaseFragment
import org.ninetripods.mq.study.kotlin.ktx.log
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * 协程的基本使用
 */
class CoroutineBaseFragment : BaseFragment() {
    private val mainScope = MainScope()

    override fun getLayoutId(): Int {
        return R.layout.fragment_coroutine_base
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // supervisorJobFunc() //Job & SupervisorJob示例
        coroutineDispatcherFunc() //CoroutineDispatcher切换线程示例

        /**
         * CoroutineContext可以设置以下内容:
         * 1、Job：控制协程的生命周期。
         * 2、CoroutineDispatcher：将工作分派到适当的线程。
         * 3、CoroutineName：协程的名称，可用于调试。
         * 4、CoroutineExceptionHandler：处理未捕获的异常。
         */
//        val exceptionHandler =
//                CoroutineExceptionHandler { context, throwable -> print("throwable:$throwable") }
//
//        lifecycleScope.launch(
//                context = Dispatchers.Main + Job() + CoroutineName("MyCoroutines") + exceptionHandler,
//                start = CoroutineStart.LAZY) {
//            suspendFuc()
//        }
    }

    /**
     * CoroutineDispatcher 将工作分派到适当的线程
     */
    private fun coroutineDispatcherFunc() {
        /**
         * 1、Dispatchers.Main.immediate 如果在UI线程，不会做特殊处理；如果是在子线程，会通过Handler转发到主线程
         * 2、Dispatchers.IO
         * 3、Dispatchers.DEFAULT
         * 4、Dispatchers.Unconfined
         */
        val deferred = mainScope.async(Dispatchers.IO) {

        }
        lifecycleScope.launch {
            deferred.await()
        }
    }

    /**
     * Job & SupervisorJob
     */
    private fun supervisorJobFunc() {
        //----------------Job的生命周期----------------
        //NOTE:需要在onDestroy中关闭协程
        val mainJob = mainScope.launch(
            start = CoroutineStart.LAZY) {
            log("isActive2: ${this.coroutineContext.job.isActive}, isCancelled: ${this.coroutineContext.job.isCancelled}, isCompleted: ${this.coroutineContext.job.isCompleted} ")
        }
        log("isActive1: ${mainJob.isActive}, isCancelled: ${mainJob.isCancelled}, isCompleted: ${mainJob.isCompleted} ")
        mainJob.start()
        mainJob.invokeOnCompletion {
            log("isActive4: ${mainJob.isActive}, isCancelled: ${mainJob.isCancelled}, isCompleted: ${mainJob.isCompleted} ")
        }
        log("isActive3: ${mainJob.isActive}, isCancelled: ${mainJob.isCancelled}, isCompleted: ${mainJob.isCompleted} ")

        //----------------子Job中使用SupervisorJob----------------
        val exceptionHandler =
            CoroutineExceptionHandler { context, throwable -> log("throwable:$throwable") }

        GlobalScope.launch(exceptionHandler) {
            //子Job1, 注意这里传入的是 SupervisorJob，当发生异常时，兄弟协程(job2/job3)不会被取消；如果是Job，那么兄弟协程也会被取消
            launch(SupervisorJob()) {
                delay(200)
                throw NullPointerException()
            }
            //子Job2
            launch {
                delay(300)
                log("child2 execute successfully")
            }
            //子Job3
            launch {
                delay(400)
                log("child3 execute successfully")
            }
            delay(600)
            log("parent execute successfully")
        }
    }

    private suspend fun suspendFuc(): String {
        delay(1000)
        return "value"
    }

    class MyElement : AbstractCoroutineContextElement(MyElement) {
        public companion object Key : CoroutineContext.Key<MyElement>
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}