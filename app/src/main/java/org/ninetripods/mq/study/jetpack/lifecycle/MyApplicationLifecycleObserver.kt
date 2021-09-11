package org.ninetripods.mq.study.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.ninetripods.mq.study.jetpack.KConsts

class MyApplicationLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(value = Lifecycle.Event.ON_START)
    fun onAppForeground() {
        Log.e(KConsts.LIFE_APPLICATION_TAG, "onAppForeground")
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_STOP)
    fun onAppBackground() {
        Log.e(KConsts.LIFE_APPLICATION_TAG, "onAppBackground")
    }
}