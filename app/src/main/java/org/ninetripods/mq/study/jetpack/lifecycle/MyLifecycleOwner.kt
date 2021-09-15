package org.ninetripods.mq.study.jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * 自定义LifecycleOwner
 */
class MyLifecycleOwner : LifecycleOwner {

    private lateinit var registry: LifecycleRegistry

    fun init() {
        registry = LifecycleRegistry(this)
        registry.currentState = Lifecycle.State.CREATED
    }

    fun onStart() {
        registry.currentState = Lifecycle.State.STARTED
    }

    fun onResume() {
        registry.currentState = Lifecycle.State.RESUMED
    }

    fun onPause() {
        registry.currentState = Lifecycle.State.STARTED
    }

    fun onStop() {
        registry.currentState = Lifecycle.State.CREATED
    }

    fun onDestroy() {
        registry.currentState = Lifecycle.State.DESTROYED
    }

    override fun getLifecycle(): Lifecycle {
        return registry
    }
}