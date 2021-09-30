package org.ninetripods.mq.study.kotlin.flow

interface ICallBack {
    fun onSuccess(sucStr: String?)
    fun onError(errorStr: String?)
}