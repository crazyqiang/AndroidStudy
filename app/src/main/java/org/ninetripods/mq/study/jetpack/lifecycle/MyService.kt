package org.ninetripods.mq.study.jetpack.lifecycle

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import org.ninetripods.mq.study.jetpack.KConsts

class MyService : LifecycleService() {
    override fun onCreate() {
        Log.e(KConsts.SERVICE, "Service:onCreate")
        super.onCreate()
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