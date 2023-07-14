package org.ninetripods.mq.study.util

import org.ninetripods.mq.study.kotlin.ktx.log

/**
 * runCatching代替try catch的各种用法
 * Created by mq on 2023/7/12
 */
class RunCatchException {

    fun exceptionTest() {
        //不需要结果
        runCatching { 100 / 0 }
            .onFailure { ex -> ex.printStackTrace() }

        //需要结果
        runCatching { 100 / 2 }
            .onSuccess { value -> log("onSuccess:$value") } //runCatching{}中执行成功，并传入执行结果
            .onFailure { exception -> log("onFailure:$exception") }//runCatching{}中执行失败，并传入exception
            //.getOrDefault(0) //获取runCatching{}中执行的结果,如果是Failure直接返回默认值
            .getOrElse { ex -> //获取runCatching{}中执行的结果,如果是Failure返回else内部的值。相比getOrDefault多了对exception的处理
                log("exception:$ex")
                100
            }
            //.getOrThrow()//获取runCatching{}中执行的结果,如果是Failure直接抛异常
            //.getOrNull() //获取runCatching{}中执行的结果,如果是Failure返回null
            //.exceptionOrNull() //如果有问题则返回exception；否则返回null
            .run {
                log("result:$this")
            }
    }
}