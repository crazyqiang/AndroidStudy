package org.ninetripods.lib_bytecode.asm.treeApi

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

/**
 *
 * Created by mq on 2023/2/24
 */
class AOutLibClassNode(val api: Int, private val classWriter: ClassWriter) : ClassNode(api) {
    private val mThresholdTime = 500

    companion object {
        const val owner = "org/ninetripods/lib_bytecode/common/TimeCostUtil"
        const val descripter = "Lorg/ninetripods/lib_bytecode/common/TimeCostUtil"
    }

    override fun visitEnd() {
        //super.visitEnd()
        processTimeCost()
        //允许classWriter访问ClassNode类中的信息
        accept(classWriter)
    }

    private fun processTimeCost(clzName: String? = "", methodName: String? = "", access: Int = 0) {
        for (methodNode: MethodNode in methods) {
            if (methodNode.name.equals("<init>") || methodNode.name.equals("<clinit>")) continue
            val instructions = methodNode.instructions

            //方法开头插入 timer -= System.currentTimeMillis();
            val clzName = name
            val methodName = methodNode.name
            val access = methodNode.access
            instructions.insert(createMethodStartInstructions(clzName, methodName, access))

            //退出方法之前 插入timer += System.currentTimeMillis();
            methodNode.instructions.forEach { insnNode ->
                val opcode = insnNode.opcode
                if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                    val endInstructions = createMethodEndInstructions(clzName, methodName, access)
                    methodNode.instructions.insertBefore(insnNode, endInstructions)
                }
            }
            //methodNode.maxStack += 4
        }

        //val acc = Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC
        //fields.add(FieldNode(acc, "timer", "J", null, 1))
    }

    /**
     * 在method中创建入口指令集
     */
    private fun createMethodStartInstructions(
        clzName: String?,
        methodName: String?,
        access: Int,
    ): InsnList {
        val isStaticMethod = access and Opcodes.ACC_STATIC != 0
        return InsnList().apply {
            if (isStaticMethod) {
                add(FieldInsnNode(Opcodes.GETSTATIC, owner, "INSTANCE", descripter))
                //操作数栈中传入下面两个参数
                add(IntInsnNode(Opcodes.SIPUSH, mThresholdTime))
                add(LdcInsnNode("$clzName&$methodName"))
                add(
                    MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        owner,
                        "recordStaticMethodStart",
                        "(ILjava/lang/String;)V",
                        false
                    )
                )
            } else {
                add(FieldInsnNode(Opcodes.GETSTATIC, owner, "INSTANCE", descripter))
                //操作数栈中传入对应的三个入参
                add(IntInsnNode(Opcodes.SIPUSH, mThresholdTime))
                add(LdcInsnNode("$clzName&$methodName"))
                add(VarInsnNode(Opcodes.ALOAD, 0))
                //将上面的三个参数传入下面的方法中
                add(
                    MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        owner,
                        "recordMethodStart",
                        "(ILjava/lang/String;Ljava/lang/Object;)V",
                        false
                    )
                )
            }
        }
    }

    /**
     * 在method中退出时的指令集
     */
    private fun createMethodEndInstructions(
        clzName: String?,
        methodName: String?,
        access: Int,
    ): InsnList {
        val isStaticMethod = access and Opcodes.ACC_STATIC != 0
        return InsnList().apply {
            if (isStaticMethod) {
                add(FieldInsnNode(Opcodes.GETSTATIC, owner, "INSTANCE", descripter))
                //调用
                add(IntInsnNode(Opcodes.SIPUSH, mThresholdTime))
                add(LdcInsnNode("$clzName&$methodName"))
                add(
                    MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        owner,
                        "recordStaticMethodEnd",
                        "(ILjava/lang/String;)V",
                        false
                    )
                )
            } else {
                add(FieldInsnNode(Opcodes.GETSTATIC, owner, "INSTANCE", descripter))
                //栈中传入对应的三个入参
                add(IntInsnNode(Opcodes.SIPUSH, mThresholdTime))
                add(LdcInsnNode("$clzName&$methodName"))
                add(VarInsnNode(Opcodes.ALOAD, 0))
                //将上面的三个参数传入下面的方法中
                add(
                    MethodInsnNode(
                        Opcodes.INVOKEVIRTUAL,
                        owner,
                        "recordMethodEnd",
                        "(ILjava/lang/String;Ljava/lang/Object;)V",
                        false
                    )
                )
            }
        }
    }


}