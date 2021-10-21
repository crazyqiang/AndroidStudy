package org.ninetripods.mq.study.jetpack.mvvm.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.jetpack.mvvm.base.singleEvent.SingleLiveData

abstract class BaseViewModel : ViewModel() {

    //loading
    val loadingLiveData = SingleLiveData<Boolean>()

    //异常
    val errorLiveData = SingleLiveData<String>()

    //正常
    val normalLiveData = SingleLiveData<Boolean>()

    open fun init(arguments: Bundle?) {}

    /**
     * LiveData迁移到Flow：https://juejin.cn/post/6979008878029570055
     * @param showLoading 请求网络时是否展示Loading
     * @param liveData 发送最终数据
     * @param handleEx 异常处理
     * @param request 正常逻辑
     */
    fun <T : Any> launchRequestWithFlow(
        showLoading: Boolean = true,
        liveData: MutableLiveData<T>? = null,
        handleEx: suspend (String) -> Unit = { errMsg ->
            //默认异常处理，子类可以进行覆写
            errorLiveData.postValue(errMsg)
        }, request: suspend () -> BaseData<T>
    ) {
        viewModelScope.launch {
            var baseData = BaseData<T>()
            flow {
                baseData = request()
                when (baseData.state) {
                    State.Success -> emit(baseData)
                    State.Error -> throw IllegalArgumentException(baseData.msg)
                }
            }.flowOn(Dispatchers.IO)
                .onStart { if (showLoading) loadStart() }
                .onEmpty {
                    //当流完成却没有发出任何元素时回调
                    throw IllegalArgumentException("出错了")
                }
                .onCompletion {
                    //流取消或结束时调用
                    if (showLoading) loadFinish()
                }
                .catch { exception ->
                    run {
                        //catch对此操作符之前的流进行异常处理
                        if (showLoading) loadFinish()
                        exception.message?.let { handleEx(it) }
                    }
                }
                .collect {
                    normalLiveData.postValue(true)
                    liveData?.postValue(baseData.data)
                }
        }
    }

    /**
     * @param request 正常逻辑
     * @param liveData 发送最终数据
     * @param error 异常处理
     * @param showLoading 请求网络时是否展示Loading
     */
    fun <T : Any> launchRequest(
        showLoading: Boolean = true,
        liveData: MutableLiveData<T>? = null,
        error: suspend (String) -> Unit = { errMsg ->
            //默认异常处理，子类可以进行覆写
            errorLiveData.postValue(errMsg)
        }, request: suspend () -> BaseData<T>
    ) {
        //是否展示Loading
        if (showLoading) {
            loadStart()
        }
        viewModelScope.launch {
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    State.Success -> {
                        normalLiveData.postValue(true)
                        liveData?.postValue(baseData.data)
                    }
                    State.Error -> baseData.msg?.let { error(it) }
                }
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