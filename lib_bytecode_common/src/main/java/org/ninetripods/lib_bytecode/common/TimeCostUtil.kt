package org.ninetripods.lib_bytecode.common

import android.app.Application
import java.util.concurrent.ConcurrentHashMap

/**
 * 全局方法耗时Util
 */
object TimeCostUtil {
    private const val TAG = "METHOD_COST"

    private val staticMethodObj by lazy { StaticMethodObject() }

    /**
     * 方法Map，其中key：方法名，value：耗时时间
     */
    private val METHODS_MAP by lazy { ConcurrentHashMap<String, Long>() }

    fun recordMethodStart(methodName: String, clz: Any?) {
        try {
           METHODS_MAP[methodName] = System.currentTimeMillis()
            if (clz is Application) {}
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun recordMethodEnd() {

    }

}

class StaticMethodObject {}