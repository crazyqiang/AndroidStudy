package org.ninetripods.lib_bytecode.asm.treeApi

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

/**
 * ASM -> Tree API
 * Created by mq on 2023/2/24
 */
class ATimeCostClassNode(var api: Int, var classVisitor: ClassVisitor) : ClassNode(api) {

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor {
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        //super.visitEnd()
        processMethod()
        accept(classVisitor)
    }

    /**
     * 统计方法耗时
     */
    private fun processMethod() {
        for (methodNode: MethodNode in methods) {
            if (methodNode.name.equals("<init>") || methodNode.name.equals("<clinit>")) continue
            val instructions = methodNode.instructions

            //方法开头 插入 timer -= Snsem.currentTimeMillis();
            InsnList().apply {
                add(FieldInsnNode(Opcodes.GETSTATIC, name, "timer", "J"))
                add(
                    MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "java/lang/System",
                        "currentTimeMillis",
                        "()J"
                    )
                )
                add(InsnNode(Opcodes.LSUB))
                add(FieldInsnNode(Opcodes.PUTSTATIC, name, "timer", "J"))
                instructions.insert(this)
            }

            //退出方法之前 插入timer += System.currentTimeMillis();
            methodNode.instructions.forEach { insnNode ->
                val opcode = insnNode.opcode
                if ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                    InsnList().apply {
                        add(FieldInsnNode(Opcodes.GETSTATIC, name, "timer", "J"))
                        add(
                            MethodInsnNode(
                                Opcodes.INVOKESTATIC,
                                "java/lang/System",
                                "currentTimeMillis",
                                "()J"
                            )
                        )
                        add(InsnNode(Opcodes.LADD))
                        add(FieldInsnNode(Opcodes.PUTSTATIC, name, "timer", "J"))
                        methodNode.instructions.insertBefore(insnNode, this)
                    }
                }
            }
            methodNode.maxStack += 4
        }

        val acc = Opcodes.ACC_PUBLIC or Opcodes.ACC_STATIC
        fields.add(FieldNode(acc, "timer", "J", null, null))
//        methods.forEach { methodNode ->
//            if (methodNode.name.equals("<init>") || methodNode.name.equals("<clinit>")) continue
//        }
    }
}
