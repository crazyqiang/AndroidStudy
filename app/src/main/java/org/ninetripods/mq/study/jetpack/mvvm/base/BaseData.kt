package org.ninetripods.mq.study.jetpack.mvvm.base

import com.google.gson.annotations.SerializedName

class BaseData<T> {
    @SerializedName("errorCode")
    var code = -1
    @SerializedName("errorMsg")
    var msg: String? = null
    var data: T? = null
    var state: State = State.Error
}

enum class State {
    Success, Error
}
