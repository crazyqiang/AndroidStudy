package org.ninetripods.mq.study.jetpack.mvvm.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.jetpack.mvvm.base.singleEvent.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    //loading
    val loadingLiveData = SingleLiveData<Boolean>()

    //异常
    val errorLiveData = SingleLiveData<String>()

    open fun init(arguments: Bundle?) {}

    /**
     * @param request 正常逻辑
     * @param error 异常处理
     * @param showLoading 请求网络时是否展示Loading
     */
    fun launchRequest(
        showLoading: Boolean = true,
        error: suspend (String) -> Unit = { errMsg ->
            //默认异常处理，子类可以进行覆写
            errorLiveData.postValue(errMsg)
        }, request: suspend () -> Unit
    ) {
        //是否展示Loading
        if (showLoading) {
            loadStart()
        }
        //TODO  是否能用Flow代替
        viewModelScope.launch(Dispatchers.IO) {
            try {
                request()
            } catch (e: Exception) {
                error(e.message ?: "")
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }

    private fun loadStart() {
        loadingLiveData.postValue(true)
    }

    private fun loadFinish() {
        loadingLiveData.postValue(false)
    }
}