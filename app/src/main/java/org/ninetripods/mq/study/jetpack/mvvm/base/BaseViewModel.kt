package org.ninetripods.mq.study.jetpack.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.ninetripods.mq.study.jetpack.mvvm.base.http.NetworkUtil

abstract class BaseViewModel : ViewModel() {

    //loading
    private val _loadingChannel = Channel<Boolean>()
    val loadingFlow = _loadingChannel.receiveAsFlow()

    //异常
    private val _errorChannel = Channel<String>()
    val errorFlow = _errorChannel.receiveAsFlow()

    //正常
    private val _normalChannel = Channel<Boolean>()
    val normalFlow = _normalChannel.receiveAsFlow()


    /**
     * LiveData迁移到Flow：https://juejin.cn/post/6979008878029570055
     * @param showLoading 请求网络时是否展示Loading
     * @param liveData 发送最终数据
     * @param handleEx 异常处理
     * @param request 正常逻辑
     */
    fun <T : Any> requestDataWithFlow(
        showLoading: Boolean = true,
        modelFlow: MutableStateFlow<T>? = null,
        handleEx: suspend (String) -> Unit /**/ = { errMsg ->
            //默认异常处理，子类可以进行覆写
            _errorChannel.send(errMsg)
        },
        request: suspend () -> BaseData<T>,
    ) {
        viewModelScope.launch {
            if (!NetworkUtil.isNetworkConnected()) {
                _errorChannel.send("网络未连接")
                return@launch
            }
            //是否展示Loading
            if (showLoading) {
                loadStart()
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    State.Success -> {
                        _normalChannel.send(true)
                        baseData.data?.let { modelFlow?.emit(it) }
                    }
                    State.Error -> baseData.msg?.let { error(it) }
                }
            } catch (e: Exception) {
                e.message?.let { handleEx(it) }
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }

    /**
     * 使用场景：一次性消费场景，比如弹窗，需求是在UI层只弹一次，即使App切到后台再切回来，也不会重复订阅(不会多次弹窗)；
     * 如果使用SharedFlow/StateFlow，UI层使用的lifecycle.repeatOnLifecycle、Flow.flowWithLifecycle，则在App切换前后台时，UI层会重复订阅
     * Channel使用特点：
     * 1、每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2、第一个订阅者可以收到collect之前的事件，即粘性事件
     */
    fun <T : Any> requestDataWithSingleFlow(
        showLoading: Boolean = true,
        channel: Channel<T>? = null,
        handleEx: suspend (String) -> Unit /**/ = { errMsg ->
            //默认异常处理，子类可以进行覆写
            _errorChannel.send(errMsg)
        },
        request: suspend () -> BaseData<T>,
    ) {
        viewModelScope.launch {
            if (!NetworkUtil.isNetworkConnected()) {
                _errorChannel.send("网络未连接")
                return@launch
            }
            //是否展示Loading
            if (showLoading) {
                loadStart()
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    State.Success -> {
                        _normalChannel.send(true)
                        baseData.data?.let { channel?.send(it) }
                    }
                    State.Error -> baseData.msg?.let { error(it) }
                }
            } catch (e: Exception) {
                e.message?.let { handleEx(it) }
            } finally {
                if (showLoading) {
                    loadFinish()
                }
            }
        }
    }


    /**
     * LiveData迁移到Flow：https://juejin.cn/post/6979008878029570055
     * @param showLoading 请求网络时是否展示Loading
     * @param liveData 发送最终数据
     * @param handleEx 异常处理
     * @param request 正常逻辑
     */
    fun <T : Any> launchRequestWithFlow1(
        showLoading: Boolean = true,
        stateFlow: MutableStateFlow<T?>? = null,
        handleEx: suspend (String) -> Unit /**/ = { errMsg ->
            //默认异常处理，子类可以进行覆写
            _errorChannel.send(errMsg)
        },
        request: suspend () -> BaseData<T>,
    ) {
        viewModelScope.launch {
            if (!NetworkUtil.isNetworkConnected()) {
                _errorChannel.send("网络未连接")
                return@launch
            }
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
                    _normalChannel.send(true)
                    stateFlow?.emit(baseData.data)
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
            _errorChannel.send(errMsg)
        },
        request: suspend () -> BaseData<T>,
    ) {
        viewModelScope.launch {
            if (!NetworkUtil.isNetworkConnected()) {
                _errorChannel.send("网络未连接")
                return@launch
            }
            //是否展示Loading
            if (showLoading) {
                loadStart()
            }
            val baseData: BaseData<T>
            try {
                baseData = request()
                when (baseData.state) {
                    State.Success -> {
                        _normalChannel.send(true)
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

    private suspend fun loadStart() {
        _loadingChannel.send(true)
    }

    private suspend fun loadFinish() {
        _loadingChannel.send(false)
    }
}