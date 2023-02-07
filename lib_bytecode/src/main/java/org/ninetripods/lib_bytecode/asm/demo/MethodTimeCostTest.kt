package org.ninetripods.lib_bytecode.asm.demo

class MethodTimeCostTest {

    fun addTimeCostMonitor() {
        Thread.sleep(1000)
    }

//    var timeCost = 0L
//    fun addTimeCostMonitor() {
//        val begin = System.currentTimeMillis()
//        Thread.sleep(1000)
//        timeCost = System.currentTimeMillis() - begin
//        println("timeCost:$timeCost")
//    }

}