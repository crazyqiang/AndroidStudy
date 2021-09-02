package org.ninetripods.mq.study.jetpack.lifecycle

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleService
import org.ninetripods.mq.study.jetpack.JConsts

class MyService : LifecycleService() {
    override fun onCreate() {
        Log.e(JConsts.SERVICE, "Service:onCreate")
        super.onCreate()
        lifecycle.addObserver(MyLifeCycleObserver())
    }

    override fun onStart(intent: Intent?, startId: Int) {
        Log.e(JConsts.SERVICE, "Service:onStart")
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        Log.e(JConsts.SERVICE, "Service:onDestroy")
        super.onDestroy()
    }
}