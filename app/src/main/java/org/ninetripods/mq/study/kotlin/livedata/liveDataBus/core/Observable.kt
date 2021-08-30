package org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Observable<T> {
    fun postValue(value: T)
    fun postDelayValue(value: T, delay: Long)
    fun postDelay(owner: LifecycleOwner, value: T, delay: Long)
    fun observe(owner: LifecycleOwner, observer: Observer<T>)
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>)
}