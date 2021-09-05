package org.ninetripods.mq.study.kotlin.livedata.liveDataBus

import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.LiveDataBusCore
import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.Observable
import org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core.ObservableConfig

/**
 * fork from: https://github.com/JeremyLiao/LiveEventBus
 */
class LiveDataBus {

    /**
     * get observable by key with type
     *
     * @param key key
     * @param type type
     * @param <T> T
     * @return Observable
     */
    operator fun <T> get(key: String, type: Class<T>): Observable<T> {
        return LiveDataBusCore.instance.with(key)
    }

    /**
     * get observable by key
     *
     * @param key String
     * @param <T> T
     * @return Observable
     */
    operator fun <T> get(key: String): Observable<T> {
        return get(key, Any::class.java) as Observable<T>
    }

    /**
     * use the inner class Config to set params
     * first of all, call config to get the Config instance
     * then, call the method of Config to config LiveEventBus
     * call this method in Application.onCreate
     * @param key String
     * @return Config
     */
    fun config(key: String): ObservableConfig? {
        return LiveDataBusCore.instance.config(key)
    }
}