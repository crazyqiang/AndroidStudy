package org.ninetripods.lib_bytecode.asm.demo

class MethodTimeCostTest {

    companion object {
        @JvmStatic
        fun staticTimeCostMonitor() {
            Thread.sleep(1000)
        }
    }

    fun timeCostMonitor() {
        Thread.sleep(1000)
    }


    //public static long timeCost = 0;

//    fun addTimeCostMonitor() {
//        Thread.sleep(1000)
//    }

//    var timeCost = 0L
//    fun addTimeCostMonitor() {
//        val begin = System.currentTimeMillis()
//        Thread.sleep(1000)
//        timeCost = System.currentTimeMillis() - begin
//        println("timeCost:$timeCost")
//    }

}