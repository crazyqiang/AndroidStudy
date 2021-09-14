package org.ninetripods.mq.study.jetpack.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel
import org.ninetripods.mq.study.jetpack.mvvm.base.State
import org.ninetripods.mq.study.jetpack.mvvm.model.WanModel
import org.ninetripods.mq.study.jetpack.mvvm.repo.WanRepository

class WanViewModel : BaseViewModel() {
    //LiveData
    val mWanLiveData = MutableLiveData<List<WanModel>>()

    //Repository中间层 管理所有数据来源 包括本地的及网络的
    private val mWanRepo = WanRepository()

    fun getWanInfo(wanId: String = "") {
        launchRequest {
            val result = mWanRepo.requestWanData(wanId)
            when (result.state) {
                State.Success -> mWanLiveData.postValue(result.data)
                State.Error -> errorLiveData.postValue(result.msg)
            }
        }
    }
}