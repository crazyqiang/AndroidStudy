package org.ninetripods.lib_bytecode.asm.demo

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes.*
import org.objectweb.asm.Type

class C {
    companion object {
        var timer: Long = 0
    }

    @Throws(Exception::class)
    fun m() {
        timer -= System.currentTimeMillis()
        Thread.sleep(100)
        timer += System.currentTimeMillis()
    }
}

/**
CustomMethodVisitor: visitInsn opcode 190
CustomMethodVisitor: visitInsn opcode 4
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 5
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 6
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 177
 */
class AMethodVisitor(api: Int, mv: MethodVisitor) : MethodVisitor(api, mv) {

    override fun visitCode() {
        mv.visitCode()
        mv.visitFieldInsn(GETSTATIC, Type.getInternalName(C::class.java), "timer", "J")
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitInsn(LSUB)
        mv.visitFieldInsn(PUTSTATIC, Type.getInternalName(C::class.java), "timer", "J")
    }

    override fun visitInsn(opcode: Int) {
        println("CustomMethodVisitor: visitInsn opcode $opcode")
        super.visitInsn(opcode)
    }
}