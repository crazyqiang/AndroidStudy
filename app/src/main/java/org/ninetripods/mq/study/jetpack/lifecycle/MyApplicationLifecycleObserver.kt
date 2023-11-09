package org.ninetripods.mq.study.jetpack.lifecycle

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.ninetripods.mq.study.jetpack.KConsts
import org.ninetripods.mq.study.kotlin.ktx.showToast

/**
 * 监听Application的生命周期，可以用来判断应用前后台判断
 */
class MyApplicationLifecycleObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        Log.e(KConsts.LIFE_APPLICATION_TAG, "onAppForeground")
        showToast("应用处于前台")
    }

    override fun onStop(owner: LifecycleOwner) {
        Log.e(KConsts.LIFE_APPLICATION_TAG, "onAppBackground")
        showToast("应用处于后台")
    }
}