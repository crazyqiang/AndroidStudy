package org.ninetripods.mq.study.kotlin.util

import android.annotation.SuppressLint
import android.app.Application

object AppUtil {
    private var mApplication: Application? = null

    @SuppressLint("PrivateApi")
    fun getApplication(): Application? {
        if (mApplication == null) {
            try {
                mApplication = Class.forName("android.app.ActivityThread")
                    .getMethod("currentApplication")
                    .invoke(null, null as Array<Any?>?) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return mApplication
    }
}