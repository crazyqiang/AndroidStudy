package org.ninetripods.mq.study.kotlin.livedata.liveDataBus

import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.LiveDataBusCore
import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.Observable
import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.ObservableConfig

/**
 * fork from: https://github.com/JeremyLiao/LiveEventBus
 */
class LiveDataBus {

    operator fun <T> get(key: String, type: Class<T>): Observable<T>? {
        return LiveDataBusCore.instance.with(key)
    }

    operator fun <T> get(key: String): Observable<T>? {
        return get(key, Any::class.java) as? Observable<T>
    }

    fun config(key: String): ObservableConfig? {
        return LiveDataBusCore.instance.config(key)
    }
}