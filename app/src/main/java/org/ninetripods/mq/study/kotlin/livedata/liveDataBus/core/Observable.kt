package org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Observable<T> {
    /**
     * 进程内发送消息
     *
     * @param value 发送的消息
     */
    fun post(value: T)

    /**
     * 进程内发送消息，延迟发送
     *
     * @param value 发送的消息
     * @param delay 延迟毫秒数
     */
    fun postDelayValue(value: T, delay: Long)

    /**
     * 进程内发送消息，延迟发送，带生命周期
     * 如果延时发送消息的时候sender处于非激活状态，消息取消发送
     *
     * @param sender 消息发送者
     * @param value  发送的消息
     * @param delay  延迟毫秒数
     */
    fun postDelay(owner: LifecycleOwner, value: T, delay: Long)

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     *
     * @param owner    LifecycleOwner
     * @param observer 观察者
     */
    fun observe(owner: LifecycleOwner, observer: Observer<T>)

    /**
     * 注册一个Observer，生命周期感知，自动取消订阅
     * 如果之前有消息发送，可以在注册观察者时收到消息（消息同步）
     *
     * @param owner    LifecycleOwner
     * @param observer 观察者
     */
    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>)

    /**
     * 注册一个Observer，需手动解除绑定
     */
    fun observeForever(observer: Observer<T>)

    /**
     * 注册一个Observer，需手动解除绑定
     * 如果之前有消息发送，可以在注册时收到消息（消息同步）
     */
    fun observeStickyForever(observer: Observer<T>)

    /**
     * 通过observeForever或observeStickyForever注册的，需要调用该方法取消订阅
     */
    fun removeObserver(observer: Observer<T>)
}