package org.ninetripods.lib_bytecode.asm.demo;

import org.ninetripods.lib_bytecode.BConstant;
import org.ninetripods.lib_bytecode.asm.coreApi.ATimeCostClassVisitor;
import org.ninetripods.lib_bytecode.asm.treeApi.AOutLibClassNode;
import org.ninetripods.lib_bytecode.asm.treeApi.ATimeCostClassNode;
import org.ninetripods.lib_bytecode.util.FileUtil;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

public class AExecutor {

    public static void main(String[] args) {
        try {
            //使用示例
            ClassReader classReader = new ClassReader(MethodTimeCostTestJava.class.getName());
            //ClassWriter.COMPUTE_MAXS 自动计算帧栈信息（操作数栈 & 局部变量表）
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            //-------------------------方式1：ASM Tree API -------------------------
            //ClassNode classNode = new ATimeCostClassNode(BConstant.ASM9,classWriter);
            ClassNode classNode = new AOutLibClassNode(BConstant.ASM9, classWriter);
            //-------------------------方式2：ASM Core API -------------------------
            //ClassVisitor classVisitor = new ATimeCostClassVisitor(BConstant.ASM9, classWriter);

            //访问者模式：将ClassVisitor传入ClassReader中，从而可以访问ClassReader中的私有信息；类似一个接口回调。
            classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

            FileUtil.INSTANCE.byte2File("lib_bytecode/files/MethodTimeCostTestJava.class",classWriter.toByteArray());

//            Loader loader = new Loader();
//            try {
//                log("classWriter ByteArray: " + Arrays.toString(classWriter.toByteArray()));
//                Class addTimeClass = loader.defineClass(MethodTimeCostTest.class.getName(), classWriter.toByteArray());
//                Object instance = addTimeClass.newInstance();
//                addTimeClass.getDeclaredMethod("addTimeCostMonitor").invoke(instance);
//                Long timeCost = addTimeClass.getDeclaredField(FIELD_NAME_ADD).getLong(instance);
//                log("timeCost:" + timeCost);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
