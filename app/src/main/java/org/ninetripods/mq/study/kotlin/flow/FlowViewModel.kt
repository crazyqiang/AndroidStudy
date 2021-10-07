package org.ninetripods.mq.study.kotlin.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.kotlin.ktx.log

class FlowViewModel : ViewModel() {
    //MutableStateFlow 可读可写
    private val _stateFlow = MutableStateFlow(DataState.Success("default"))

    //StateFlow 只可读
    val mStateFlow: StateFlow<DataState> = _stateFlow

    //SharedFlow
    private val _sharedFlow = MutableSharedFlow<String>(
        replay = 0,//重播给新订阅者的数量
        extraBufferCapacity = 1,//当有剩余缓冲空间时，emit不会挂起
        onBufferOverflow = BufferOverflow.SUSPEND //配置缓冲区的溢出操作
    )
    val mSharedFlow: SharedFlow<String> = _sharedFlow

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
//        viewModelScope.launch {
//            delay(5000)
            log("tryEmit: sharedFlow1")
            _sharedFlow.tryEmit("sharedFlow1")
            log("tryEmit: sharedFlow2")
            _sharedFlow.tryEmit("sharedFlow2")
        //}
    }

}