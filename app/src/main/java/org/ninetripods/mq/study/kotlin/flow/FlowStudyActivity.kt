package org.ninetripods.mq.study.kotlin.flow

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.BaseActivity
import org.ninetripods.mq.study.R
import org.ninetripods.mq.study.kotlin.ktx.id
import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * Kotlin Flow用法
 */
class FlowStudyActivity : BaseActivity() {

    private val mTvSend: TextView by id(R.id.tv_flow)
    private val mBtnContent1: Button by id(R.id.btn_content1)
    private val mBtnContent2: Button by id(R.id.btn_content2)
    private val mToolBar: Toolbar by id(R.id.toolbar)

    private val mBtnSF: Button by id(R.id.btn_state_flow)
    private val mTvSF: TextView by id(R.id.tv_state_flow)

    private val mBtnShareF: Button by id(R.id.btn_share_flow)
    private val mTvShareF: TextView by id(R.id.tv_share_flow)

    private lateinit var mFlowModel: FlowViewModel
    private lateinit var mSimpleFlow: Flow<String>

    override fun setContentView() {
        setContentView(R.layout.activity_flow_study)
    }

    override fun initViews() {
        initToolBar(mToolBar, "Kotlin Flow", true)
        mFlowModel = ViewModelProvider(this).get(FlowViewModel::class.java)
        initFlow()
    }

    @ExperimentalCoroutinesApi
//    val mFlow: Flow<String?> = callbackFlow {
//        val callback = object : ICallBack {
//            override fun onSuccess(sucStr: String?) {
//                offer(sucStr)
//            }
//
//            override fun onError(errorStr: String?) {
//                offer(errorStr)
//            }
//        }
//        callback.onSuccess("onSuccess")
//        awaitClose()
//    }

    private fun initFlow() {
        var sendNum = 0
        lifecycleScope.launch {
            mSimpleFlow = flow {
                sendNum++
                emit("sendValue:$sendNum")
            }.flowOn(Dispatchers.IO)
                .onEmpty { log("onEmpty") }
                .onStart { log("onStart") }
                .onEach { log("onEach: $it") }
                .onCompletion { log("onCompletion") }
                .catch { exception -> exception.message?.let { log(it) } }
        }

        mFlowModel.fetchStateFlowData()

        //SharedFlow
        lifecycleScope.launch {
            mFlowModel.mSharedFlow.collect { value ->
                log("collect: $value")
                mTvShareF.text = value
            }
        }
    }

    override fun initEvents() {
        //---------------simpleFlow---------------
        mBtnContent1.setOnClickListener {
            lifecycleScope.launch {
                mSimpleFlow.collect {
                    mTvSend.text = it
                    mBtnContent1.text = it
                }
            }
        }
        mBtnContent2.setOnClickListener {
            lifecycleScope.launch {
                mSimpleFlow.collect {
                    mTvSend.text = it
                    mBtnContent2.text = it
                }
            }
        }

        //---------------StateFlow---------------
        mBtnSF.setOnClickListener {
            //StateFlow
            lifecycleScope.launch {
                mFlowModel.mStateFlow.collect { state ->
                    log(state.toString())
                    when (state) {
                        is DataState.Success -> mTvSF.text = state.msg
                        is DataState.Error -> state.exception.message?.let { it -> mTvSF.text = it }
                    }
                }
            }
        }

        //---------------SharedFlow---------------
        mBtnShareF.setOnClickListener {
            mFlowModel.fetchSharedFlowData()
        }
    }

}