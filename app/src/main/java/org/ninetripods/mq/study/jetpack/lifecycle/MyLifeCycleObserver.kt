package org.ninetripods.mq.study.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import org.ninetripods.mq.study.jetpack.KConsts

/**
 * 监听LifecycleOwner生命周期变化并作出响应
 */
open class MyLifeCycleObserver : LifecycleObserver {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun connect(owner: LifecycleOwner) {
        Log.e(KConsts.LIFE_TAG, "Lifecycle.Event.ON_START:connect")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    fun disConnect() {
        Log.e(KConsts.LIFE_TAG, "Lifecycle.Event.ON_STOP:disConnect")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_ANY)
    fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
        Log.e(KConsts.LIFE_TAG, "Lifecycle.Event.ON_ANY:" + event.name)
    }
}