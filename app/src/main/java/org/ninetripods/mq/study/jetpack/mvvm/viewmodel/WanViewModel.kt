package org.ninetripods.mq.study.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class WanViewModel : BaseViewModel() {
    //LiveData UI层通过该引用观察数据变化
    val mWanLiveData = MutableLiveData<List<WanModel>>()

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    /**
     * LiveData方式
     */
    fun getWanInfo(wanId: String = "") {
        launchRequest(liveData = mWanLiveData) { mWanRepo.requestWanData(wanId) }
    }

    /**
     * LiveData+Flow方式
     */
    fun getWanInfoByFlow(wanId: String = "") {
        launchRequestWithFlow(liveData = mWanLiveData) {
            mWanRepo.requestWanData(wanId)
        }
    }
}