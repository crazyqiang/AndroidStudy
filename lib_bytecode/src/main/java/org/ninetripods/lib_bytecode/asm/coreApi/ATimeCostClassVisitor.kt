package org.ninetripods.lib_bytecode.asm.coreApi

import org.ninetripods.lib_bytecode.log
import org.ninetripods.lib_bytecode.util.decodeAcc
import org.ninetripods.lib_bytecode.util.decodeOpcode
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.commons.AdviceAdapter

const val FIELD_NAME_ADD: String = "timeCost" //新增属性名称

/**
 * 方法耗时统计
 * @param api
 * @param classVisitor
visit(): owner-org/ninetripods/lib_bytecode/asm/demo/MethodTimeCostTest
visitMethod(): access-ACC_PUBLIC , name-<init>, descriptor-()V, signature-null, exceptions-null
visitMethod(): access-ACC_PUBLIC ACC_FINAL , name-getTimeCost, descriptor-()J, signature-null, exceptions-null
onMethodEnter():
visitFieldInsn(): opcode-180, owner-org/ninetripods/lib_bytecode/asm/demo/MethodTimeCostTest, name-timeCost, descriptor-J
onMethodExit(): opcode-LRETURN
visitMethod(): access-ACC_PUBLIC ACC_FINAL , name-setTimeCost, descriptor-(J)V, signature-null, exceptions-null
onMethodEnter():
visitFieldInsn(): opcode-181, owner-org/ninetripods/lib_bytecode/asm/demo/MethodTimeCostTest, name-timeCost, descriptor-J
onMethodExit(): opcode-RETURN
visitMethod(): access-ACC_PUBLIC ACC_FINAL , name-addTimeCostMonitor, descriptor-()V, signature-null, exceptions-null
onMethodEnter():
visitFieldInsn(): opcode-181, owner-org/ninetripods/lib_bytecode/asm/demo/MethodTimeCostTest, name-timeCost, descriptor-J
visitFieldInsn(): opcode-180, owner-org/ninetripods/lib_bytecode/asm/demo/MethodTimeCostTest, name-timeCost, descriptor-J
visitFieldInsn(): opcode-178, owner-java/lang/System, name-out, descriptor-Ljava/io/PrintStream;
onMethodExit(): opcode-RETURN
visitEnd():
 */
class ATimeCostClassVisitor(api: Int, classVisitor: ClassVisitor) :
    ClassVisitor(api, classVisitor) {

    private var owner = ""

    /**
     * 如：visit(52,4128,org/ninetripods/mq/study/customView/alipayView/AlipayView$1:java/lang/Object,null,[Ljava.lang.String;@4ab48b2b)
     * @param version JDK版本  例如返回52，代表是是JDK1.8
     * @param access 修饰字段 如:Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE
     * @param name 类名
     * @param signature 泛型
     * @param superName 父类名
     * @param interfaces
     */
    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?,
    ) {
        this.owner = name ?: ""
        //log("visit(): owner-$owner")
        super.visit(version, access, name, signature, superName, interfaces)
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor {
        log("visitMethod(): access-${access.decodeAcc()}, name-$name, descriptor-$descriptor, signature-$signature, exceptions-$exceptions")
        //排除<init>构造函数
        if (cv != null && name != "<init>") {
            val methodVisitor =
                cv.visitMethod(access, name, descriptor, signature, exceptions)
            return CustomAdviceAdapter(owner, api,
                methodVisitor, access, name ?: "", descriptor ?: "")
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        if (cv != null) {
            val fieldVisitor = cv.visitField(
                Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, FIELD_NAME_ADD, "J", null, null)
            fieldVisitor.visitEnd()
        }
        log("visitEnd()")
        super.visitEnd()
    }
}

/*
AdviceAdapter 是继承自 MethodVisitor 的一个抽象类，可用于在一个方法的开头以及任意 RETURN 或 ATHROW 指令之前插入代码。
它的优点是可以对构造器也是有效的，在构造器中插入到构造器调用之后。事实上，这个适配器的大多数代码都是用于检测对这个构造器的调用。
注意，AdviceAdapter 继承自 LocalVariablesSorter，所以也可以轻松完成对一个局部变量的操作。
 */
class CustomAdviceAdapter(
    private val owner: String,
    api: Int,
    methodVisitor: MethodVisitor,
    inputAccess: Int,
    inputName: String,
    descriptor: String,
) : AdviceAdapter(api, methodVisitor, inputAccess, inputName, descriptor) {

    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        log("visitFieldInsn(): opcode-$opcode, owner-$owner, name-$name, descriptor-$descriptor")
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    override fun visitVarInsn(opcode: Int, `var`: Int) {
        super.visitVarInsn(opcode, `var`)
    }

    override fun onMethodEnter() {
        log("onMethodEnter():")
        /**
         * @see FIELD_NAME_ADD
         * @param descriptor J表示Long类型
         * 1、访问FIELD_NAME_ADD变量
         */
        mv.visitFieldInsn(GETSTATIC, owner, FIELD_NAME_ADD, "J")
        /**
         * 2、System.currentTimeMillis()
         * Type.getInternalName(System::class.java) == java/lang/System
         */
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        /**
         * 上述1、2步的值进行相减
         */
        mv.visitInsn(LSUB)
        /**
         * @see FIELD_NAME_ADD 将相减的结果重新赋值给变量FIELD_NAME_ADD
         */
        mv.visitFieldInsn(PUTSTATIC, owner, FIELD_NAME_ADD, "J")
    }

    override fun onMethodExit(opcode: Int) {
        log("onMethodExit(): opcode-${opcode.decodeOpcode()}")
        mv.visitFieldInsn(GETSTATIC, owner, FIELD_NAME_ADD, "J")
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
        mv.visitInsn(LADD)
        mv.visitFieldInsn(PUTSTATIC, owner, FIELD_NAME_ADD, "J")

        //Log.e("TTT", "===timeCost:===" + timeCost);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        mv.visitLdcInsn("===timeCost:===");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
        mv.visitFieldInsn(GETSTATIC, owner, "timeCost", "J");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        super.visitMaxs(maxStack, maxLocals)
    }
}