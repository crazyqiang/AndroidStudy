package org.ninetripods.mq.study.function

class forAndforEachWithLabel {


    /**
     * for循环中使用，执行结果：
     * //break
     * i: 1
     * i: 2
     * 循环外继续执行
     * //continue
     * i: 1
     * i: 2
     * i: 4
     * i: 5
     * 循环外继续执行
     * //return
     * i: 1
     * i: 2
     *
     */
    fun forControl() {
        for (i in 1..5) {
            if (i == 3) break //break continue return
            println("i: $i")
        }
        println("循环外继续执行")
    }

    /**
     * 嵌套for循环中使用
     * //break
     * i: 1
     * j: 1
     * j: 2
     * 循环外继续执行
     *
     * //continue
     * i: 1
     * j: 1
     * j: 2
     *
     * i: 2
     * j: 1
     * j: 2
     * 循环外继续执行
     */
    fun forNestedControl() {
        loop@ for (i in 1..2) {
            println("i: $i")
            for (j in 1..5) {
                if (j == 3) break@loop //break continue
                println("j: $j")
            }
        }
        println("循环外继续执行")
    }

    /**
     * Label标签
     */
    fun labelExam() {
        loop@ for (i in 1..5) {
            //...
        }
    }


    /**
     * forEach中退出循环,模拟break的
     */
    fun forEachControl() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop
                println("it:$it")
            }
            println("循环外继续执行")
        }
    }
}