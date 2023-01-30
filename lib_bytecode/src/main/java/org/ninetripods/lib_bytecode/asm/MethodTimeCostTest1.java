package org.ninetripods.lib_bytecode.asm;

public class MethodTimeCostTest1 {

    public void addTimeCostMonitor() {
        int aaa = 10;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
