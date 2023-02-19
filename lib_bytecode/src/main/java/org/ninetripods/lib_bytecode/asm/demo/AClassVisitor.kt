package org.ninetripods.lib_bytecode.asm.demo

import org.objectweb.asm.*

/**
 * use[#org.ninetripods.mq.study.customView.alipayView.AlipayView]
find class: AlipayView.class
visit(52,4128,org/ninetripods/mq/study/customView/alipayView/AlipayView$1:java/lang/Object,null,[Ljava.lang.String;@4ab48b2b)
visitSource(AlipayView.java, null)
visitOuterClass(org/ninetripods/mq/study/customView/alipayView/AlipayView, null, null)
visitInnerClass(org/ninetripods/mq/study/customView/alipayView/AlipayView$1, null, null, 4104)
visitInnerClass(org/ninetripods/mq/study/customView/alipayView/AlipayView$State, org/ninetripods/mq/study/customView/alipayView/AlipayView, State, 16408)
visitField(4120,$SwitchMap$org$ninetripods$mq$study$customView$alipayView$AlipayView$State, [I, null, null)
visitMethod(8, <clinit>, ()V, null, null)
CustomMethodVisitor: visitInsn opcode 190
CustomMethodVisitor: visitInsn opcode 4
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 5
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 6
CustomMethodVisitor: visitInsn opcode 79
CustomMethodVisitor: visitInsn opcode 177
visitEnd()
 */
class AClassVisitor(api: Int, classVisitor: ClassVisitor? = null) :
    ClassVisitor(api, classVisitor) {

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
        println("visit($version,$access,$name,$superName,$signature,$interfaces)")
        super.visit(version, access, name, signature, superName, interfaces)
    }

    /**
     * 如：visitSource(AlipayView.java, null)
     * @param source
     * @param debug
     */
    override fun visitSource(source: String?, debug: String?) {
        println("visitSource($source, $debug)")
        super.visitSource(source, debug)
    }

    /**
     * 如：visitOuterClass(org/ninetripods/mq/study/customView/alipayView/AlipayView, null, null)
     * @param owner
     * @param name
     * @param descriptor
     */
    override fun visitOuterClass(owner: String?, name: String?, descriptor: String?) {
        println("visitOuterClass($owner, $name, $descriptor)")
        super.visitOuterClass(owner, name, descriptor)
    }

    /**
     * @param descriptor
     * @param visible
     * @return
     */
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor? {
        println("visitAnnotation($descriptor, $visible)")
        return super.visitAnnotation(descriptor, visible)
    }

    /**
     * @param attribute
     */
    override fun visitAttribute(attribute: Attribute?) {
        println("visitAttribute($attribute)")
        super.visitAttribute(attribute)
    }

    /**
     * 如：visitInnerClass(org/ninetripods/mq/study/customView/alipayView/AlipayView$1, null, null, 4104)
     *    visitInnerClass(org/ninetripods/mq/study/customView/alipayView/AlipayView$State, org/ninetripods/mq/study/customView/alipayView/AlipayView, State, 16408)
     * @param name
     * @param outerName
     * @param innerName
     * @param access
     */
    override fun visitInnerClass(
        name: String?,
        outerName: String?,
        innerName: String?,
        access: Int,
    ) {
        println("visitInnerClass($name, $outerName, $innerName, $access)")
        super.visitInnerClass(name, outerName, innerName, access)
    }

    /**
     * 如：visitField(4120,$SwitchMap$org$ninetripods$mq$study$customView$alipayView$AlipayView$State, [I, null, null)
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param value
     * @return
     */
    override fun visitField(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        value: Any?,
    ): FieldVisitor {
        println("visitField($access,$name,$descriptor,$signature,$value)")
        return super.visitField(access, name, descriptor, signature, value)
    }

    /**
     * 如：visitMethod(8, <clinit>, ()V, null, null)
     * @param access
     * @param name
     * @param descriptor
     * @param signature
     * @param exceptions
     * @return
     */
    override fun visitMethod(
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
    ): MethodVisitor? {
        println("visitMethod($access, $name, $descriptor, $signature, $exceptions)")
        //MethodVisitor
        var mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (mv != null) {
            mv = AMethodVisitor(api, mv)
        }
        return mv
    }

    override fun visitEnd() {
        println("visitEnd()")
        super.visitEnd()
    }

}