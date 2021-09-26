package org.ninetripods.mq.study.kotlin.flow

import android.view.View
import android.widget.TextView
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

class FlowStudyActivity : BaseActivity() {

    private val mTvContent: TextView by id(R.id.tv_content)

    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    override fun setContentView() {
        setContentView(R.layout.activity_flow_study)
    }

    @ExperimentalCoroutinesApi
    val flow = callbackFlow {
        val callback = object : ICallBack {
            override fun onSuccess(sucStr: String) {
                offer(sucStr)
            }

            override fun onError(errorStr: String) {
                offer(errorStr)
            }
        }
        callback.onSuccess("success")
        awaitClose()
    }

    @ExperimentalCoroutinesApi
    fun startFlow(view: View) {
        flow.flowOn(Dispatchers.IO).onEmpty { }
            .onStart { }
            .catch { exception -> {} }
    }

    @ExperimentalCoroutinesApi
    private fun registerEvent() {
        lifecycleScope.launch {
            flow.collect {
                mTvContent.text = it
            }
        }
    }

    override fun initEvents() {

        lifecycleScope.launch {

            launch {
                for (k in 1..3) {
                    println("I'm not blocked $k")
                    delay(100)
                }
            }
            simple().collect { value ->
                println(value)
            }
        }
    }

}