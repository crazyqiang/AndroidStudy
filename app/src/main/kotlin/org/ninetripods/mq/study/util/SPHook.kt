package org.ninetripods.mq.study.util

import android.os.Build
import org.ninetripods.mq.study.kotlin.ktx.log
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue

object SPHook {
    private const val CLASS_QUEUED_WORK = "android.app.QueuedWork"
    private const val FIELD_FINISHERS_8_UP = "sFinishers"
    private const val FIELD_PENDING_WORK_FINISHERS_8_DOWN = "sPendingWorkFinishers"

    fun optimizeSpTask() {
        if (Build.VERSION.SDK_INT < 26) {
            reflectSPendingWorkFinishers()
        } else {
            reflectSFinishers()
        }
    }

    /**
     * 8.0以上 Reflect finishers
     *
     */
    private fun reflectSFinishers() {
        try {
            val clz = Class.forName(CLASS_QUEUED_WORK)
            val field = clz.getDeclaredField(FIELD_FINISHERS_8_UP)
            field.isAccessible = true
            val queue = field.get(clz) as? LinkedList<Runnable>
            if (queue != null) {
                val linkedListProxy = LinkedListProxy(queue)
                field.set(queue, linkedListProxy)
                log("hook success")
            }
        } catch (ex: Exception) {
            log("hook error:${ex}")
        }
    }

    /**
     * 8.0以下 Reflect pending work finishers
     */
    private fun reflectSPendingWorkFinishers() {
        try {
            val clz = Class.forName(CLASS_QUEUED_WORK)
            val field = clz.getDeclaredField(FIELD_PENDING_WORK_FINISHERS_8_DOWN)
            field.isAccessible = true
            val queue = field.get(clz) as? ConcurrentLinkedQueue<Runnable>
            if (queue != null) {
                val proxy = ConcurrentLinkedQueueProxy(queue)
                field.set(queue, proxy)
                log("hook success")
            }
        } catch (ex: Exception) {
            log("hook error:${ex}")
        }
    }

    /**
     * 在8.0以上apply()中QueuedWork.addFinisher(awaitCommit), 需要代理的是LinkedList，如下：
     * # private static final LinkedList<Runnable> sFinishers = new LinkedList<>()
     */
    private class LinkedListProxy(private val sFinishers: LinkedList<Runnable>) :
        LinkedList<Runnable>() {

        override fun add(element: Runnable): Boolean {
            return sFinishers.add(element)
        }

        override fun remove(element: Runnable): Boolean {
            return sFinishers.remove(element)
        }

        override fun isEmpty(): Boolean = true

        /**
         * 代理的poll()方法，永远返回空，这样UI线程就可以避免被阻塞，继续执行了
         */
        override fun poll(): Runnable? {
            return null
        }
    }

    /**
     * 在8.0以下代理
     * // The set of Runnables that will finish or wait on any async activities started by the application.
     * private static final ConcurrentLinkedQueue<Runnable> sPendingWorkFinishers = new ConcurrentLinkedQueue<Runnable>();
     */

    private class ConcurrentLinkedQueueProxy(private val sPendingWorkFinishers: ConcurrentLinkedQueue<Runnable>) :
        ConcurrentLinkedQueue<Runnable>() {

        override fun add(element: Runnable?): Boolean {
            return sPendingWorkFinishers.add(element)
        }

        override fun remove(element: Runnable?): Boolean {
            return sPendingWorkFinishers.remove(element)
        }

        override fun isEmpty(): Boolean = true

        /**
         * 代理的poll()方法，永远返回空，这样UI线程就可以避免被阻塞，继续执行了
         */
        override fun poll(): Runnable? {
            return null
        }
    }

}