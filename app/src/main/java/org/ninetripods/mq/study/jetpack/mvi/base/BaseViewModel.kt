package org.ninetripods.mq.study.jetpack.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseData
import org.ninetripods.mq.study.jetpack.mvvm.base.http.NetworkUtil

/**
 * ViewModel基类
 */
abstract class BaseViewModel<State : IState, Event : IEvent, Effect : IEffect> : ViewModel() {

    //loading
    private val _loadingChannel = Channel<Boolean>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    //异常
    private val _errorChannel = Channel<String>()
    val errorFlow = _errorChannel.receiveAsFlow()

    //正常
    private val _normalChannel = Channel<Boolean>()
    val normalFlow = _normalChannel.receiveAsFlow()

    private val _viewState = MutableStateFlow(initState())
    val viewState: StateFlow<State> = _viewState

    /**
     * 事件带来的副作用，通常是 一次性事件 且 一对一的订阅关系
     * 例如：弹Toast、导航Fragment等
     * Channel特点
     * 1.每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2.第一个订阅者可以收到 collect 之前的事件
     */
    private val _viewEffect: Channel<Effect> = Channel()
    val viewEffect = _viewEffect.receiveAsFlow()

    abstract fun initState(): State
    abstract fun dispatch(event: Event)

    protected fun sendState(copy: State.() -> State) {
        _viewState.update { _viewState.value.copy() }
    }

    protected fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _viewEffect.send(effect)
        }
    }

    fun <T : Any> requestDataWithFlow(
        showLoading: Boolean = true,
//        modelFlow: MutableStateFlow<T>? = _viewState,
        handleEx: suspend (String) -> Unit /**/ = { errMsg ->
            //默认异常处理，子类可以进行覆写
            _errorChannel.send(errMsg)
        },
        request: suspend () -> BaseData<T>,
        sucFunction: (T) -> Unit,
    ) {
        viewModelScope.launch {
            if (!NetworkUtil.isNetworkConnected()) {
                _errorChannel.send("网络未连接")
                return@launch
            }
            //是否展示Loading
            if (showLoading) {
                loadStart()
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    org.ninetripods.mq.study.jetpack.mvvm.base.State.Success -> {
                        _normalChannel.send(true)
                        baseData.data?.let { sucFunction(it) }
                    }
                    org.ninetripods.mq.study.jetpack.mvvm.base.State.Error -> baseData.msg?.let {
                        error(it)
                    }
                }
            } catch (e: Exception) {
                e.message?.let { handleEx(it) }
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }

    private suspend fun loadStart() {
        _loadingChannel.send(true)
    }

    private suspend fun loadFinish() {
        _loadingChannel.send(false)
    }

}