package org.ninetripods.mq.study.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Fragment之间通过传入同一个Activity来共享ViewModel
 */
class ShareViewModel : ViewModel() {

    val itemLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //点击左侧Fragment中的Item发送数据
    fun clickItem(infoStr: String) {
        itemLiveData.value = infoStr
    }
}