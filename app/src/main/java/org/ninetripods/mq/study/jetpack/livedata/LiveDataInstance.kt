package org.ninetripods.mq.study.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**
 * 使用object来声明单例模式
 */
object LiveDataInstance {
    //MutableLiveData是抽象类LiveData的具体实现类
    val INSTANCE = MutableLiveData<String>()

    val INSTANCE2 = MutableLiveData<String>()

    //用来切换对应的发送源
    val SWITCH = MutableLiveData<Boolean>()
}