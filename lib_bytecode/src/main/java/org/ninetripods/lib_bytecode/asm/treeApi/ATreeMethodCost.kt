package org.ninetripods.lib_bytecode.asm.treeApi

import org.ninetripods.lib_bytecode.BConstant
import org.objectweb.asm.ClassReader
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.IntInsnNode

/**
 * ASM Tree API
 * blog: https://juejin.cn/post/6844904118700474375
 * 优缺点：
 * 1、优点：处理简单类时修改比较简单，改动较少
 * 2、缺点：不适合处理复杂逻辑，代码复用困难
 */
object ATreeMethodCost {

    fun processByTreeApi(bytes: ByteArray) {
        val classNode = ClassNode(BConstant.ASM9)
        val classReader = ClassReader(bytes)
        classReader.accept(classNode, ClassReader.EXPAND_FRAMES)

        //遍历field
        classNode.fields.forEach { field ->
            println(field.name + "," + field.desc)
            field.access = Opcodes.ACC_PUBLIC
        }
        //生成字段，相当于 public static int xmkp = 10
        val fieldNode = FieldNode(Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC, "xmkp", "I", null, 10)
        classNode.fields.add(fieldNode)

        //遍历method
        classNode.methods.forEach { methodNode ->
            //instructions表示操作码列表
            methodNode.instructions.forEach {
                if (it.opcode == Opcodes.SIPUSH && (it as IntInsnNode).operand == 16) {

                }
            }
        }


    }

}