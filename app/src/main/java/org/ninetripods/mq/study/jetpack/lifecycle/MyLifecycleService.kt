package org.ninetripods.mq.study.jetpack.lifecycle

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import org.ninetripods.mq.study.jetpack.KConsts

/**
 * 自定义LifecycleService
 */
class MyLifecycleService : LifecycleService() {

    override fun onCreate() {
        Log.e(KConsts.SERVICE, "Service:onCreate")
        super.onCreate()
        //通过getLifecycle()添加生命周期Observer
        lifecycle.addObserver(MyLifeCycleObserver())
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Log.e(KConsts.SERVICE, "Service:onStart")
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        Log.e(KConsts.SERVICE, "Service:onDestroy")
        super.onDestroy()
    }
}