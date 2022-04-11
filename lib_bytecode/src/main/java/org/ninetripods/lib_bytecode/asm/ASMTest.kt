package org.ninetripods.lib_bytecode.asm

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

/**
 * ASM使用示例
 */
object ASMTest {

    @JvmStatic
    fun main(args: Array<String>) {
        println("ASM示例")
        val classReader = ClassReader("java.lang.Runnable")

//        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
//        val classVisitor = CustomClassVisitor(Opcodes.ASM5, classWriter)

        val classVisitor = CustomClassVisitor(Opcodes.ASM9)
        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
        produceClass()
    }

    /**
     * 产生类
    package pkg;
    public interface Comparable extends Mesurable {
    int LESS = -1;
    int EQUAL = 0;
    int GREATER = 1;
    int compareTo(Object o);
    }
     */
    private fun produceClass() {
        val cw = ClassWriter(0)
        //access：修饰字段
        //signature: 泛型
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
            "pkg/Comparable", null, "java/lang/Object", arrayOf("pkg/Mesurable"))
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
            null, -1).visitEnd()
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
            null, 0).visitEnd()
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
            null, 1).visitEnd()

        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
            "(Ljava/lang/Object;)I", null, null).visitEnd()
        cw.visitEnd()
        val b = cw.toByteArray()
        println(b.toString())
    }
}