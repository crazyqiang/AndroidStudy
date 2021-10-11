package org.ninetripods.mq.study.kotlin.flow

import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
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
    private val mBtnConvertSF: Button by id(R.id.btn_convert_state_flow)
    private val mTvConvertSF: TextView by id(R.id.tv_convert_state_flow)

    private val mBtnShareF: Button by id(R.id.btn_share_flow)
    private val mTvShareF: TextView by id(R.id.tv_share_flow)
    private val mBtnConvertF: Button by id(R.id.btn_convert)
    private val mTvConvertF: TextView by id(R.id.tv_convert)

    private val mBtnScc: Button by id(R.id.btn_scc)
    private val mTvScc: TextView by id(R.id.tv_scc)
    private val mBtnCallbackFlow: Button by id(R.id.btn_callback_flow)
    private val mTvCallbackFlow: TextView by id(R.id.tv_callback_flow)

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

        //StateFlow
        mFlowModel.fetchStateFlowData()

        //TODO SharedFlow
        //extraBufferCapacity=1 缓存数量为1， onBufferOverflow = BufferOverflow.DROP_OLDEST丢弃的是最老的值
        // 注意 这里设置的是Dispatchers.IO 即发送与接收不在一个线程中
        lifecycleScope.launch(Dispatchers.IO) {
            mFlowModel.mSharedFlow.collect { value ->
                log("collect: $value")
                withContext(Dispatchers.Main) {
                    mTvShareF.text = value
                }
            }
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
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

        /**
         * flow通过stateIn转化为StateFlow
         */
        mBtnConvertSF.setOnClickListener {
            lifecycleScope.launch {
                val builder = StringBuilder()
                //使用repeatOnLifecycle 当State小于STARTED时暂停收集 配合上游的SharingStarted.WhileSubscribed一块使用
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mFlowModel.flowConvertStateFlow.collect {
                        log(it)
                        builder.append(it).append("\n")
                        mTvConvertSF.text = builder.toString()
                    }
                }
                //NOTE:跟Flow.flowWithLifecycle效果一致
//                mFlowModel.flowConvertStateFlow
//                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
//                    .collect {
//                        log(it)
//                        builder.append(it).append("\n")
//                        mTvConvertSF.text = builder.toString()
//                    }
            }
        }

        //---------------SharedFlow---------------
        mBtnShareF.setOnClickListener {
            mFlowModel.fetchSharedFlowData()
        }
        /**
         * flow通过shareIn转化为SharedFlow
         */
        mBtnConvertF.setOnClickListener {
            val builder: StringBuilder = StringBuilder()
            lifecycleScope.launch {
                mFlowModel.flowConvertSharedFlow.collect {
                    log(it)
                    builder.append(it).append("\n")
                    mTvConvertF.text = builder.toString()
                }
            }
        }

        /**
         * suspendCancellableCoroutine将回调转化为协程使用
         */
        mBtnScc.setOnClickListener {
            lifecycleScope.launch {
                val result = mFlowModel.suspendCancelableData()
                log(result)
                mTvScc.text = result
            }
        }

        /**
         * callbackFlow使用举例
         */
        mBtnCallbackFlow.setOnClickListener {
            lifecycleScope.launch {
                //将两个callbackFlow串联起来 先搜索目的地，然后到达目的地
                mFlowModel.getSearchCallbackFlow()
                    .flatMapConcat {
                        mFlowModel.goDesCallbackFlow(it)
                    }.collect {
                        mTvCallbackFlow.text = it ?: "error"
                    }

            }
        }
    }

}