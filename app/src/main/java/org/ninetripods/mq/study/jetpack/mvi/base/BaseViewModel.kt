package org.ninetripods.mq.study.jetpack.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel基类
 */
abstract class BaseViewModel<State : IState, Event : IEvent, Effect : IEffect> : ViewModel() {

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

}