package org.ninetripods.mq.study.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.ninetripods.mq.study.jetpack.KConsts

/**
 * 监听LifecycleOwner生命周期变化并作出响应
 */
open class MyLifeCycleObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Log.e(KConsts.LIFE_TAG, "Lifecycle.Event.ON_START:connect")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.e(KConsts.LIFE_TAG, "Lifecycle.Event.ON_STOP:disConnect")
    }
}