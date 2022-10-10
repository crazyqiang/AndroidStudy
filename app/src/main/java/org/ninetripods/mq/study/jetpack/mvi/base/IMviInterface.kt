package org.ninetripods.mq.study.jetpack.mvi.base

interface IUiState //重复性事件 可以多次刷新
interface ISingleUiState //一次性事件，不支持多次消费
interface IEvent //View层分发事件

open class EmptySingleUiState : ISingleUiState