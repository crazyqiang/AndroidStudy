package org.ninetripods.mq.study.kotlin.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * LiveData扩展函数封装
 */
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (t: T) -> Unit) {
    liveData.observe(this, { observer(it) })
}
