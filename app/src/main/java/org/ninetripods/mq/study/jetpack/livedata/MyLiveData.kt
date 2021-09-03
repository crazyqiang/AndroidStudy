package org.ninetripods.mq.study.jetpack.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import java.math.BigDecimal

class MyLiveData private constructor(symbol: String) : LiveData<BigDecimal?>() {

    //活跃的观察者（LifecycleOwner）数量从 0 变为 1 时调用
    override fun onActive() {
    }

    //活跃的观察者（LifecycleOwner）数量从 1 变为 0 时调用。这不代表没有观察者了，可能是全都不活跃了。可以使用hasObservers()检查是否有观察者。
    override fun onInactive() {
    }

    companion object {
        private var sInstance //单实例
                : MyLiveData? = null

        //获取单例
        @MainThread
        operator fun get(symbol: String): MyLiveData? {
            if (sInstance == null) {
                sInstance = MyLiveData(symbol)
            }
            return sInstance
        }
    }
}