package org.ninetripods.mq.study.jetpack.compose

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class ComposeVModel : BaseViewModel(){
    //StateFlow UI层通过该引用观察数据变化
    private val _wanFlow = MutableStateFlow<List<WanModel>>(ArrayList())
    val mWanFlow: StateFlow<List<WanModel>> = _wanFlow

    /**
     * 使用场景：一次性消费场景，比如弹窗，需求是在UI层只弹一次，即使App切到后台再切回来，也不会重复订阅(不会多次弹窗)；
     * 如果使用SharedFlow/StateFlow，UI层使用的lifecycle.repeatOnLifecycle、Flow.flowWithLifecycle，则在App切换前后台时，UI层会重复订阅
     * Channel使用特点：
     * 1、每个消息只有一个订阅者可以收到，用于一对一的通信
     * 2、第一个订阅者可以收到collect之前的事件，即粘性事件
     */
    private val _channel = Channel<List<WanModel>>()
    val channelFlow = _channel.receiveAsFlow()

    //LiveData UI层通过该引用观察数据变化
    val mWanLiveData = MutableLiveData<List<WanModel>>()

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    /**
     * Flow方式
     */
    fun getWanInfoByFlow(wanId: String = "") = requestDataWithFlow(modelFlow = _wanFlow) {
        mWanRepo.requestWanData(wanId)
    }

    /**
     * Channel方式 一对一
     */
    fun getWanInfoByChannel(wanId: String = "") = requestDataWithSingleFlow(channel = _channel) {
        mWanRepo.requestWanData(wanId)
    }

    /**
     * LiveData方式
     */
    fun getWanInfo(wanId: String = "") {
        launchRequest(liveData = mWanLiveData) { mWanRepo.requestWanData(wanId) }
    }
}


