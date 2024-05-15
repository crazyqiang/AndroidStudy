package org.ninetripods.mq.study.coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

class AsyncAwaitFuc {

    private suspend fun fetchData1(): String {
        delay(1000) //模拟网络请求耗时
        return "data1"
    }

    private suspend fun fetchData2(): String {
        delay(1500) //模拟网络请求耗时
        return "data2"
    }

    fun main() = runBlocking {
        /**
         * 1、并行请求 async{}或者async(start=CoroutineStart.DEFAULT){}
         * 2、串行请求 async(start=CoroutineStart.LAZY){}
         */
        val totalTime = measureTimeMillis {
            val deferred1: Deferred<String> = async(start = CoroutineStart.LAZY) { fetchData1() }
            val deferred2: Deferred<String> = async(start = CoroutineStart.LAZY) { fetchData2() }

            //并发等待两个任务完成，并获取结果
            val result1 = deferred1.await()
            val result2 = deferred2.await()
            println("result1：$result1,result2：$result2")
        }
        println("totalTime:$totalTime")
    }

    /**
     * 超时任务
     */
    fun timeOutExample() = runBlocking {
        val deferredResult = withTimeoutOrNull(500) {
            //超过500ms，直接返回null
            async { fetchData1() }.await()
        }

        if (deferredResult != null) {
            println("result：$deferredResult")
        } else {
            println("time out")
        }
    }
}