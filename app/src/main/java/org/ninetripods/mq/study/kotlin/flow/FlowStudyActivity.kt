package org.ninetripods.mq.study.kotlin.flow

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log
import java.lang.IllegalArgumentException

class FlowStudyActivity : BaseActivity() {

    private val mTvContent: TextView by id(R.id.tv_content)
    private val mToolBar: Toolbar by id(R.id.toolbar)

//    suspend fun simple(): Flow<Int> = flow {
//        for (i in 1..3) {
//            delay(100)
//            emit(i)
//        }
//    }

    override fun setContentView() {
        setContentView(R.layout.activity_flow_study)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Kotlin Flow", true)
    }

    @ExperimentalCoroutinesApi
    val mFlow = callbackFlow<String?> {
        val callback = object : ICallBack {
            override fun onSuccess(sucStr: String?) {
                offer(sucStr)
            }

            override fun onError(errorStr: String?) {
                offer(errorStr)
            }
        }
        callback.onSuccess("onSuccess")
        awaitClose()
    }

    @ExperimentalCoroutinesApi
    fun startFlow(view: View) {
        lifecycleScope.launch {
            log(Thread.currentThread().name)
            flow{
                emit("111")
            }
            //mFlow
                    .flowOn(Dispatchers.Main)
                    .onEmpty {
                        log("onEmpty")
                        emit("onEmpty")
                    }
                    .onStart {
                        log("onStart")
                        //throw IllegalArgumentException("error")
                    }
                    .onEach {
                        log("onEach: $it")
                    }
                    .onCompletion {
                        log("onCompletion")
                        //emit("onCompletion")
                    }
                    .catch { exception -> exception.message?.let { log(it) } }
                    .collect {
                        log("collect")
                        mTvContent.text = it
                    }
        }
    }

    private fun registerEvent() {
//        lifecycleScope.launch(Dispatchers.Main) {
//            mFlow.collect {
//                log("collect")
//                mTvContent.text = it
//            }
//        }
    }

    override fun initEvents() {
        registerEvent()
//        lifecycleScope.launch {
//            launch {
//                for (k in 1..3) {
//                    println("I'm not blocked $k")
//                    delay(100)
//                }
//            }
//            simple().collect { value ->
//                println(value)
//            }
//        }
    }

}