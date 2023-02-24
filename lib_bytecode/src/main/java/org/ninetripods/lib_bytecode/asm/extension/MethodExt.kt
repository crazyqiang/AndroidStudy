package org.ninetripods.lib_bytecode.asm.extension

import groovy.lang.Closure
import org.gradle.util.ConfigureUtil

/**
 * Created by mq on 2023/2/21
 */
class MethodExt(
    var stackMethod: StackMethodExt = StackMethodExt(),
    var normalMethod: NormalMethodExt = NormalMethodExt(),
) {
    fun stackMethod(closure: Closure<StackMethodExt?>?) {
        ConfigureUtil.configure(closure, stackMethod)
    }

    fun normalMethod(closure: Closure<NormalMethodExt?>?) {
        ConfigureUtil.configure(closure, normalMethod)
    }

}

class NormalMethodExt(
    var thresholdTime: Int = 500, //阈值
    var packageNames: MutableSet<String> = mutableSetOf(), //普通函数插桩包名集合
    var blackMethods: MutableSet<String> = mutableSetOf(), //黑名单
) {
    fun thresholdTime(thresholdTime: Int) {
        this.thresholdTime = thresholdTime
    }

    fun packageNames(packageNames: MutableSet<String>) {
        this.packageNames = packageNames
    }

    fun blackMethods(methodBlacklist: MutableSet<String>) {
        this.blackMethods = methodBlacklist
    }
}

class StackMethodExt(
    var thresholdTime: Int = 5,
    var enterMethods: MutableSet<String> = mutableSetOf(),
    var blackMethods: MutableSet<String> = mutableSetOf(),
) {
    fun thresholdTime(thresholdTime: Int) {
        this.thresholdTime = thresholdTime
    }

    fun enterMethods(enterMethods: MutableSet<String>) {
        this.enterMethods = enterMethods
    }

    fun blackMethods(blackMethods: MutableSet<String>) {
        this.blackMethods = blackMethods
    }
}