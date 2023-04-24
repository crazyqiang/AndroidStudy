package org.ninetripods.mq.study.function

/**
 * 高阶函数：高阶函数reduce()、fold()详解
 * wiki: https://mp.weixin.qq.com/s/iAfmf7ferFIsAv1Oae_s5A
 * Created by mq on 2023/4/20
 */
class ReduceAndFold {

    /**
     * 执行结果：
     * acc:1, i:2
     * acc:3, i:3
     * acc:6, i:4
     * acc:10, i:5
     * sum is 15
     */
    fun reduceAdd() {
        val list = listOf(1, 2, 3, 4, 5)
        val sum = list.reduce { acc, i ->
            println("acc:$acc, i:$i")
            acc + i
        }
        println("sum is $sum")  // 15
    }

    fun reduceAppend() {
        val strings = listOf("apple", "banana", "orange", "pear")
        val result = strings.reduce { acc, s -> "$acc, $s" }
        println(result) // apple, banana, orange, pear
    }

    /**
     * 执行结果：
     * acc:10, i:1
     * acc:11, i:2
     * acc:13, i:3
     * acc:16, i:4
     * acc:20, i:5
     * sum is 25
     */
    fun foldAdd() {
        val numbers = listOf(1, 2, 3, 4, 5)
        val sum = numbers.fold(10) { acc, i -> acc + i }
        println(sum) // 25
    }

    fun foldAppend(){
        val strings = listOf("apple", "banana", "orange", "pear")
        val result = strings.fold("Fruits:") { acc, s -> "$acc $s" }
        println(result) // Fruits: apple banana orange pear
    }
}