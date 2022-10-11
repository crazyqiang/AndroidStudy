package org.ninetripods.mq.study.jetpack.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.jetpack.base.BaseData
import org.ninetripods.mq.study.jetpack.base.ReqState

/**
 * ViewModel基类
 *
 * @param UiState 重复性事件，View层可以多次接收并刷新
 * @param SingleUiState 一次性事件，View层不支持多次消费 如弹Toast，导航Activity等
 * @param Event  View层分发事件
 */
abstract class BaseViewModel<UiState : IUiState, SingleUiState : ISingleUiState> :
    ViewModel() {
//
//    //loading
//    private val _loadingChannel = Channel<Boolean>()
//    val loadingFlow = _loadingChannel.receiveAsFlow()
//
//    //异常
//    private val _errorChannel = Channel<String>()
//    val errorFlow = _errorChannel.receiveAsFlow()
//
//    //正常
//    private val _normalChannel = Channel<Boolean>()
//    val normalFlow = _normalChannel.receiveAsFlow()

    /**
     * 可以重复消费的事件
     */
    private val _viewState = MutableStateFlow(initUiState())
    val viewState: StateFlow<UiState> = _viewState

    /**
     * 事件带来的副作用，通常是 一次性事件 且 一对一的订阅关系
     * 例如：弹Toast、导航Fragment等
     * Channel特点
     * 1.每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2.第一个订阅者可以收到 collect 之前的事件
     */
    private val _singleUiState: Channel<SingleUiState> = Channel()
    val singleUiState = _singleUiState.receiveAsFlow()

    protected abstract fun initUiState(): UiState

    protected fun sendState(copy: UiState.() -> UiState) {
        _viewState.update { _viewState.value.copy() }
    }

    protected fun sendSingleUiState(effect: SingleUiState) {
        viewModelScope.launch {
            _singleUiState.send(effect)
        }
    }

    /**
     * @param showLoading 是否展示Loading
     * @param request 请求数据
     * @param successCallback 请求成功
     * @param failCallback 请求失败，处理异常逻辑
     */
    protected fun <T : Any> requestDataWithFlow(
        showLoading: Boolean = true,
        request: suspend () -> BaseData<T>,
        successCallback: (T) -> Unit,
        failCallback: suspend (String) -> Unit = { errMsg ->
            //默认异常处理，子类可以进行覆写
//            _errorChannel.send(errMsg)
        },
    ) {
        viewModelScope.launch {
            //是否展示Loading
            if (showLoading) {
                loadStart()
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    ReqState.Success -> {
//                        _normalChannel.send(true)
                        baseData.data?.let { successCallback(it) }
                    }
                    ReqState.Error -> baseData.msg?.let {
                        error(it)
                    }
                }
            } catch (e: Exception) {
                e.message?.let { failCallback(it) }
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }

    private suspend fun loadStart() {
//        _loadingChannel.send(true)
    }

    private suspend fun loadFinish() {
//        _loadingChannel.send(false)
    }

}