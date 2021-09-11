package org.ninetripods.mq.study.jetpack.lifecycle

import androidx.lifecycle.MutableLiveData
import org.ninetripods.mq.study.jetpack.mvvm.base.BaseViewModel

class NameViewModel : BaseViewModel() {
    //数据来源
    private var nameRepo: NameRepository = NameRepository()

    //User数据
    val nameLiveData: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            loadUserData()
        }
    }

    //加载数据
    fun loadUserData() {
        //展示Loading
        loadingLiveData.postValue(true)
        nameRepo.loadDataFromRepo(object : NameRepository.UserBack {
            override fun onSuccess(user: User) {
                //关闭Loading
                loadingLiveData.postValue(false)
                nameLiveData.postValue(user)
            }

            override fun onFailed(msg: User) {
                loadingLiveData.postValue(false)
                nameLiveData.postValue(msg)
            }
        })
    }
}