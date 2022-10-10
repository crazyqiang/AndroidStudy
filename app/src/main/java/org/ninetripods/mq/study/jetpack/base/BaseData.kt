package org.ninetripods.mq.study.jetpack.base

import com.google.gson.annotations.SerializedName

class BaseData<T> {
    @SerializedName("errorCode")
    var code = -1
    @SerializedName("errorMsg")
    var msg: String? = null
    var data: T? = null
    var state: ReqState = ReqState.Error
}

enum class ReqState {
    Success, Error
}
