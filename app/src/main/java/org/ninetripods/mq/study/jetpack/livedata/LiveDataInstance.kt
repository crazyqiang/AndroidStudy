package org.ninetripods.mq.study.jetpack.livedata

import androidx.lifecycle.MutableLiveData

object LiveDataInstance {
    val INSTANCE = MutableLiveData<String>()

    val INSTANCE2 = MutableLiveData<String>()

    val SWITCH = MutableLiveData<Boolean>()
}