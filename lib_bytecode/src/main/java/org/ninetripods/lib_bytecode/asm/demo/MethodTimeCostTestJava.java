package org.ninetripods.lib_bytecode.asm.demo;

public class MethodTimeCostTestJava {
//    public static long timeCost = 0;

        public void addTimeCostMonitor() throws InterruptedException {
        Thread.sleep(1000);
    }
    // ASM插入之后的效果
//    public void addTimeCostMonitor() throws InterruptedException {
//        timeCost -= System.currentTimeMillis();
//        Thread.sleep(1000L);
//        timeCost += System.currentTimeMillis();
//        System.out.println("===timeCost:===" + timeCost);
//    }

}
