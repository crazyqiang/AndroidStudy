package org.ninetripods.lib_bytecode

import org.objectweb.asm.Opcodes

fun log(message: String = "") {
    println(message)
}

/**
 * 存储一些常量值
 */
object BConstant {
    const val ASM9 = Opcodes.ASM9
}