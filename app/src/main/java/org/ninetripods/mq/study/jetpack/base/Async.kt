package org.ninetripods.mq.study.jetpack.base

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
}
