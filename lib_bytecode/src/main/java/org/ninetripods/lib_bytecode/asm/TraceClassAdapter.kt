package org.ninetripods.lib_bytecode.asm

import org.objectweb.asm.*

class TraceClassAdapter(val api: Int, val classVisitor: ClassVisitor? = null) :
    ClassVisitor(api) {

    /**
     * @param version JDK版本  例如返回52，代表是是JDK1.8
     * @param access
     * @param name 类名
     * @param signature
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
        println("visit($version,$access,$name:$superName,$signature,$interfaces)")
        super.visit(version, access, name, signature, superName, interfaces)
    }

    /**
     * @param source
     * @param debug
     */
    override fun visitSource(source: String?, debug: String?) {
        println("visitSource($source, $debug)")
        super.visitSource(source, debug)
    }

    /**
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
        println("visitField($access,$name, $descriptor, $signature, $value)")
        return super.visitField(access, name, descriptor, signature, value)
    }

    /**
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
        return super.visitMethod(access, name, descriptor, signature, exceptions)
    }

    override fun visitEnd() {
        println("visitEnd()")
        super.visitEnd()
    }

}