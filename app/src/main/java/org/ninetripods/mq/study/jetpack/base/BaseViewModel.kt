package org.ninetripods.mq.study.jetpack.base

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class BaseViewModel : ViewModel() {

    //loading
    val loadingLiveData = MutableLiveData<Boolean>()

    //异常
    val emptyOrErrorLiveData = MutableLiveData<Int>()

    /**
     * @param block 正常逻辑
     * @param error 异常处理
     * @param showLoading 请求网络时是否展示Loading
     */
    fun launch(
        block: suspend () -> Unit,
        error: suspend (Throwable) -> Unit = { e ->
            //默认异常处理，子类可以进行覆写
            emptyOrErrorLiveData.postValue(Constants.StateError)
        }, showLoading: Boolean = true
    ) {
        if (showLoading) {
            loadStart()
        }
        //TODO  是否能用Flow优化
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                Log.e("TTT", "e is : $e")
                error(e)
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }

    open fun init(arguments: Bundle?) {}

    private fun loadStart() {
        loadingLiveData.postValue(true)
    }

    private fun loadFinish() {
        loadingLiveData.postValue(false)
    }

}