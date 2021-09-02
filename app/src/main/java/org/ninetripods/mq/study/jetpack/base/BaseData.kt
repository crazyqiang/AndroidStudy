package org.ninetripods.mq.study.jetpack.base

class BaseData<T> {
    var code = -1
    var msg: String? = null
    var data: T? = null
    var state: State = State.Error

    var errorCode = -1
    var errorMsg = ""
}

enum class State {
    Success, Empty, Error
}

/**
 * 密封类
 */
//sealed class Response<out R> {
//    data class Success<out T : Any>(val data: T) : Response<T>()
//    data class Empty(val data: String = "") : Response<Nothing>()
//    data class Error(val exception: Exception) : Response<Nothing>()
//}