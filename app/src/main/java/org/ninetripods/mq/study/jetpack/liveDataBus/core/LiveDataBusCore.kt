package org.ninetripods.mq.study.jetpack.liveDataBus.core

import android.os.Handler
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.ExternalLiveData
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.ninetripods.mq.study.jetpack.liveDataBus.utils.ThreadUtils


/**
 * fork from: https://github.com/JeremyLiao/LiveEventBus
 */
class LiveDataBusCore private constructor(){
    companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
        val instance: LiveDataBusCore by lazy {
            LiveDataBusCore()
        }
    }

    private val bus: HashMap<String, LiveEvent<Any>> = HashMap()
    private val observableConfigs: HashMap<String, ObservableConfig> = HashMap()

    fun <T> with(key: String): Observable<T> {
        if (!bus.containsKey(key)) {
            bus[key] = LiveEvent(key)
        }
        return bus[key] as Observable<T>
    }

    fun config(key: String): ObservableConfig? {
        if (!observableConfigs.containsKey(key)) {
            observableConfigs[key] = ObservableConfig()
        }
        return observableConfigs[key]
    }


    private class LiveEvent<T>(key: String) : Observable<T> {
        private var liveData: LifecycleLiveData<T> = LifecycleLiveData(key)
        private val observeMap: HashMap<Observer<*>, ObserverWrapper<T>> = HashMap()

        override fun post(value: T) {
            if (ThreadUtils.isMainThread()) {
                postInternal(value)
            } else {
                mainHandler.post(PostValueTask(value))
            }
        }

        override fun postDelayValue(value: T, delay: Long) {
            mainHandler.postDelayed(PostValueTask(value), delay)
        }

        override fun postDelay(owner: LifecycleOwner, value: T, delay: Long) {
            mainHandler.postDelayed(PostLifeValueTask(value, owner), delay)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            if (ThreadUtils.isMainThread()) {
                observeInternal(owner, observer)
            } else {
                mainHandler.post {
                    observeInternal(owner, observer)
                }
            }
        }

        override fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
            if (ThreadUtils.isMainThread()) {
                observeStickyInternal(owner, observer)
            } else {
                mainHandler.post {
                    observeStickyInternal(owner, observer)
                }
            }
        }

        override fun observeForever(observer: Observer<T>) {
            if (ThreadUtils.isMainThread()) {
                observeForeverInternal(observer)
            } else {
                mainHandler.post {
                    observeForeverInternal(observer)
                }
            }
        }

        override fun observeStickyForever(observer: Observer<T>) {
            if (ThreadUtils.isMainThread()) {
                observeStickyForeverInternal(observer)
            } else {
                mainHandler.post {
                    observeStickyForeverInternal(observer)
                }
            }
        }

        override fun removeObserver(observer: Observer<T>) {
            if (ThreadUtils.isMainThread()) {
                removeObserverInternal(observer)
            } else {
                mainHandler.post {
                    removeObserverInternal(observer)
                }
            }
        }

        @MainThread
        private fun observeForeverInternal(observer: Observer<T>) {
            val observerWrapper = ObserverWrapper(observer)
            observerWrapper.preventNextEvent = liveData.version > ExternalLiveData.START_VERSION
            observeMap[observer] = observerWrapper
            liveData.observeForever(observerWrapper)
        }

        @MainThread
        private fun observeStickyForeverInternal(observer: Observer<T>) {
            val observerWrapper = ObserverWrapper(observer)
            observeMap[observer] = observerWrapper
            liveData.observeForever(observerWrapper)
        }

        @MainThread
        private fun postInternal(value: T) {
            liveData.value = value
        }

        /**
         * 非粘性
         */
        @MainThread
        private fun observeInternal(owner: LifecycleOwner, observer: Observer<T>) {
            val observerWrapper = ObserverWrapper(observer)
            //liveData.version > ExternalLiveData.START_VERSION 说明liveData里发送过消息，version值已经不是初始值，
            //如果是后注册的观察者，则屏蔽当前消息，观察者不执行；如果是先注册的观察者，则不受影响
            observerWrapper.preventNextEvent = liveData.version > ExternalLiveData.START_VERSION
            liveData.observe(owner, observerWrapper)
        }

        /**
         * 粘性
         */
        @MainThread
        private fun observeStickyInternal(owner: LifecycleOwner, observer: Observer<T>) {
            val observerWrapper = ObserverWrapper(observer)
            liveData.observe(owner, observerWrapper)
        }

        @MainThread
        private fun removeObserverInternal(observer: Observer<T>) {
            val realObserver = if (observeMap.containsKey(observer)) {
                observeMap.remove(observer)
            } else {
                observer
            }
            realObserver?.let { liveData.removeObserver(it) }
        }

        inner class LifecycleLiveData<T>(val key: String) : ExternalLiveData<T>() {
            override fun removeObserver(observer: Observer<in T>) {
                super.removeObserver(observer)
                if (!liveData.hasObservers()) {
                    instance.bus.remove(key)
                    instance.observableConfigs.remove(key)
                }
            }

            override fun observerActiveLevel(): Lifecycle.State {
                return if (lifecycleObserverAlwaysActive()) Lifecycle.State.CREATED else Lifecycle.State.STARTED
            }

            private fun lifecycleObserverAlwaysActive(): Boolean {
                if (instance.observableConfigs.containsKey(key)) {
                    val config: ObservableConfig? = instance.observableConfigs[key]
                    return config?.lifecycleObserverAlwaysActive ?: false
                }
                return false
            }
        }

        inner class PostValueTask(val newValue: T) : Runnable {
            override fun run() {
                postInternal(newValue)
            }
        }

        inner class PostLifeValueTask(val newValue: T, val owner: LifecycleOwner?) : Runnable {
            override fun run() {
                if (owner != null) {
                    if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                        postInternal(newValue)
                    }
                }
            }
        }
    }

    /**
     * Observer装饰者模式
     */
    class ObserverWrapper<T>(
            private val observer: Observer<T>,
            var preventNextEvent: Boolean = false
    ) : Observer<T> {
        override fun onChanged(t: T) {
            if (preventNextEvent) {
                preventNextEvent = false
                return
            }
            observer.onChanged(t)
        }
    }
}