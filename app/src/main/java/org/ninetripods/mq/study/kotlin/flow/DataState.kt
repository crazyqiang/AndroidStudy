package org.ninetripods.mq.study.kotlin.flow

sealed class DataState {
    data class Success(val msg: String) : DataState()
    data class Error(val exception: Throwable) : DataState()
}