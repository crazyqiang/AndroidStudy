package org.ninetripods.lib_bytecode.common

import android.app.Application
import android.util.Log
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
            if (clz is Application) {
                val methods = methodName.split("&".toRegex()).toTypedArray()
                if (methods.size == 2) {
                    if (methods[1] == "onCreate") {
                        //TODO
                    } else if (methods[1] == "attachBaseContext") {
                        //TODO
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * @param thresholdTime 阈值时间
     * @param methodName 方法名
     * @param clz 类名
     */
    fun recordMethodEnd(thresholdTime: Int, methodName: String, clz: Any?) {
        synchronized(TimeCostUtil::class.java) {
            try {
                if (METHODS_MAP.containsKey(methodName)) {
                    val startTime: Long = METHODS_MAP[methodName] ?: 0L
                    val costTime = System.currentTimeMillis() - startTime
                    METHODS_MAP.remove(methodName)

                    //方法耗时超过了阈值
                    if (costTime >= thresholdTime) {
                        val threadName = Thread.currentThread().name
                        Log.e(TAG,
                            "\t methodName=>$methodName threadNam=>$threadName thresholdTime=>$thresholdTime costTime=>$costTime")
                        val stackTraceElements = Thread.currentThread().stackTrace
                        for (element in stackTraceElements) {
                            if (element.toString().contains("TimeCostUtil")) continue

                            if (element.toString()
                                    .contains("dalvik.system.VMStack.getThreadStackTrace")
                            ) continue

                            if (element.toString()
                                    .contains("java.lang.Thread.getStackTrace")
                            ) continue
                            Log.e(TAG, "at $element")
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}

class StaticMethodObject {}