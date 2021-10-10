package org.ninetripods.mq.study.kotlin.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.ninetripods.mq.study.kotlin.ktx.log
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowViewModel : ViewModel() {
    //MutableStateFlow 可读可写
    private val _stateFlow = MutableStateFlow(DataState.Success("default"))

    //StateFlow 只可读
    val mStateFlow: StateFlow<DataState> = _stateFlow

    //flow通过stateIn转化为StateFlow
    val flowConvertStateFlow by lazy {
        flow {
            //转化为StateFlow是 emit()可以是0个或1个 或多个，当是多个时，新订阅者collect只会收到最后一个值(replay为1)
            emit("1、flow convert StateFlow")
        }
            .stateIn(
                viewModelScope, //协程作用域范围
                //----SharingStarted:控制共享的开始、结束策略---        -
                //1、SharingStarted.Eagerly, //Eagerly：马上开始，在scope作用域结束时终止
                //2、SharingStarted.Lazily, //Lazily：当订阅者出现时开始，在scope作用域结束时终止
                //3、SharingStarted.WhileSubscribed(stopTimeoutMillis: Long = 0,replayExpirationMillis: Long = Long.MAX_VALUE)
                // stopTimeoutMillis：表示最后一个订阅者结束订阅与停止上游流的时间差，默认值为0（立即停止上游流）
                // replayExpirationMillis：数据重播的超时时间。
                SharingStarted.WhileSubscribed(5000), //立即开始
                "0、initialValue" // 默认StateFlow的初始值，会发送到下游
            ).onStart { log("onStart") }
    }

    //SharedFlow
    private val _sharedFlow = MutableSharedFlow<String>(
        replay = 0,//重播给新订阅者的数量
        extraBufferCapacity = 1,//当有剩余缓冲空间时，emit不会挂起
        onBufferOverflow = BufferOverflow.DROP_OLDEST //配置缓冲区的溢出操作
    )
    val mSharedFlow: SharedFlow<String> = _sharedFlow

    //flow通过shareIn转化为SharedFlow
    val flowConvertSharedFlow by lazy {
        flow {
            emit("1、flow")
            emit("2、convert")
            emit("3、SharedFlow")
        }.shareIn(
            viewModelScope, //协程作用域范围
            //----SharingStarted:控制共享的开始、结束策略----
            //1、SharingStarted.Eagerly, //Eagerly：马上开始，在scope作用域结束时终止
            //2、SharingStarted.Lazily, //Lazily：当订阅者出现时开始，在scope作用域结束时终止
            //3、SharingStarted.WhileSubscribed(stopTimeoutMillis: Long = 0,replayExpirationMillis: Long = Long.MAX_VALUE)
            // stopTimeoutMillis：表示最后一个订阅者结束订阅与停止上游流的时间差，默认值为0（立即停止上游流）
            // replayExpirationMillis：数据重播的超时时间。
            SharingStarted.Eagerly,
            replay = 3 //重播给新订阅者的数量
        ).onStart { log("onStart") }
    }

    fun fetchStateFlowData() {
        viewModelScope.launch {
            flow {
                emit("stateFlow1")
                emit("stateFlow2")
            }.map { value -> "map:$value" }
                .onEach { value -> log("onEach:$value") }
                .collect { value -> _stateFlow.value = DataState.Success(value) }

        }
    }

    fun fetchSharedFlowData() {
        log("tryEmit: sharedFlow1")
        _sharedFlow.tryEmit("sharedFlow1")
        log("tryEmit: sharedFlow2")
        _sharedFlow.tryEmit("sharedFlow2")
        log("tryEmit: sharedFlow3")
        _sharedFlow.tryEmit("sharedFlow3")
    }

    suspend fun suspendCancelableData(): String {
        viewModelScope.launch {
            //suspendCancellableCoroutine
            suspendCancellableCoroutine<String> { continuation ->
                val callback = object : ICallBack {
                    override fun onSuccess(sucStr: String?) {
                        continuation.resume("sucStr")
                    }

                    override fun onError(errorStr: String?) {
                        continuation.resumeWithException(IllegalArgumentException("errorStr"))
                    }
                }
                callback.onSuccess("onSuccess")
                continuation.invokeOnCancellation {
                    //协程取消时调用，可以在这里进行解注册
                    log("invokeOnCancellation")
                }
                log("extraCode")
            }
        }
        return "default"
    }

}