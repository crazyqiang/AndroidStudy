package org.ninetripods.mq.study.kotlin.flow

/**
 * Created by maqiang06@zuoyebang.com on 2021/9/26
 */
interface ICallBack {
    fun onSuccess(sucStr: String?)
    fun onError(errorStr: String?)
}