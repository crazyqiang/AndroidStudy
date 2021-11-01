package org.ninetripods.mq.study.jetpack.mvvm.base.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.ninetripods.mq.study.MyApplication

/**
 * 判断网络状态
 */
object NetworkUtil {

    /**
     * 判断网络是否连接
     */
    fun isNetworkConnected(): Boolean {
        val manager = MyApplication.getApplication()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = manager.activeNetworkInfo
            return networkInfo != null && networkInfo.isAvailable
        } else {
            val network = manager.activeNetwork ?: return false
            val nc = manager.getNetworkCapabilities(network) ?: return false
            return nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
}