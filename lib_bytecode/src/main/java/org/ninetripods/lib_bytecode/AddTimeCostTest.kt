package org.ninetripods.lib_bytecode

class AddTimeCostTest {

    fun addTimeCostMonitor() {
        var aaa = 10
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