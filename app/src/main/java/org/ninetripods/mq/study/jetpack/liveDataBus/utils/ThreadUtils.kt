package org.ninetripods.mq.study.jetpack.liveDataBus.utils

import android.os.Looper

object ThreadUtils {

    /**
     * 是否是在主线程
     */
    fun isMainThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}