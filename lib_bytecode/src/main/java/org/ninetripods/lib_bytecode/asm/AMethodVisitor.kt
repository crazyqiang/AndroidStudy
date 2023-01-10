package org.ninetripods.lib_bytecode.asm

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

class CustomMethodVisitor(var api: Int, var mv: MethodVisitor) : MethodVisitor(api, mv) {

    override fun visitCode() {
        mv.visitCode()
        val opcode = GETSTATIC
        mv.visitFieldInsn(opcode, Type.getInternalName(C::class.java), "timer", "J")
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J",
            opcode == INVOKEINTERFACE)
        mv.visitInsn(LSUB)
        mv.visitFieldInsn(PUTSTATIC, Type.getInternalName(C::class.java), "timer", "J")
    }

    override fun visitInsn(opcode: Int) {
        println("CustomMethodVisitor: visitInsn opcode $opcode")
        super.visitInsn(opcode)
    }
}