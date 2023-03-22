package org.ninetripods.lib_bytecode.common

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

    /**
     * 对象方法
     * @param thresholdTime 阈值
     * @param methodName 方法名
     * @param clz 类名
     */
    fun recordMethodStart(thresholdTime: Int, methodName: String, clz: Any?) {
        try {
            METHODS_MAP[methodName] = System.currentTimeMillis()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    /**
     * 静态方法
     * @param thresholdTime 阈值
     * @param methodName 方法名
     */
    fun recordStaticMethodStart(thresholdTime: Int, methodName: String){
        recordMethodStart(thresholdTime, methodName, staticMethodObj)
    }

    /**
     * 对象方法
     * @param thresholdTime 阈值时间
     * @param methodName 方法名
     * @param clz 类名
     */
    fun recordMethodEnd(thresholdTime: Int, methodName: String, clz: Any?) {
        Log.e(
            TAG,
            "\t methodName=>$methodName thresholdTime=>$thresholdTime method=>recordMethodEnd"
        )
        synchronized(TimeCostUtil::class.java) {
            try {
                if (METHODS_MAP.containsKey(methodName)) {
                    val startTime: Long = METHODS_MAP[methodName] ?: 0L
                    val costTime = System.currentTimeMillis() - startTime
                    METHODS_MAP.remove(methodName)

                    //方法耗时超过了阈值
                    if (costTime >= thresholdTime) {
                        val threadName = Thread.currentThread().name
                        Log.e(
                            TAG,
                            "\t methodName=>$methodName threadNam=>$threadName thresholdTime=>$thresholdTime costTime=>$costTime"
                        )
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    /**
     * 静态方法
     * @param thresholdTime 阈值
     * @param methodName 方法名
     */
    fun recordStaticMethodEnd(thresholdTime: Int, methodName: String) {
        recordMethodEnd(thresholdTime, methodName, staticMethodObj)
    }

}

class StaticMethodObject