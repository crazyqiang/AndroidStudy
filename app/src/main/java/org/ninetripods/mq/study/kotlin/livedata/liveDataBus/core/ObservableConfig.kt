package org.ninetripods.mq.study.kotlin.livedata.liveDataBus.core

class ObservableConfig {

    //生命周期观察者是否始终处理活动状态，为true时activity介绍事件的状态为 >=CREATED
    //false时接收事件的状态为 >=STARTED
    var lifecycleObserverAlwaysActive: Boolean = false
    var autoClear: Boolean = false

    /**
     * lifecycleObserverAlwaysActive
     * set if then observer can always receive message
     * true: observer can always receive message
     * false: observer can only receive message when resumed
     *
     * @param active boolean
     * @return ObservableConfig
     */
    fun lifecycleObserverAlwaysActive(active: Boolean): ObservableConfig {
        lifecycleObserverAlwaysActive = active
        return this
    }

    /**
     * @param clear boolean
     * @return true: clear livedata when no observer observe it
     * false: not clear livedata unless app was killed
     */
    fun autoClear(clear: Boolean): ObservableConfig {
        autoClear = clear
        return this
    }
}