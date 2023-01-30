package com.performance.optimize

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType
import jdk.internal.org.objectweb.asm.ClassReader
import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.ClassWriter
import org.ninetripods.lib_bytecode.asm.AClassVisitor
import org.objectweb.asm.Opcodes
import shadow.bundletool.com.android.utils.FileUtils

class ATransform extends Transform {

    /**
     * @param invocation
     * invocation.inputs: 表示传进来的输入流，包含两种格式：jar包格式 及 directory目录格式
     * invocation.outputProvider: 获取到输出目录
     */
    @Override
    void transform(TransformInvocation invocation) throws TransformException, InterruptedException, IOException {
        Collection<TransformInput> transformInputs = invocation.inputs

        System.out.println("transformInputs: " + transformInputs.size())
        transformInputs.each { TransformInput input ->
            //directoryInputs表示以源码方式参与项目编译的所有目录结构及其目录下的源码文件
            input.directoryInputs.each { DirectoryInput dInput ->
                File dir = dInput.file
                if (dir) {
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        ClassReader classReader = new ClassReader(file.bytes)
                        ClassVisitor classVisitor = new AClassVisitor(Opcodes.ASM9, new ClassWriter(ClassWriter.COMPUTE_MAXS))
                        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                        System.out.println("find class: " + file.name)
                    }
                }

                //处理完文件把输出传给下一个文件
                def dest = invocation.outputProvider.getContentLocation(dInput.name,
                        dInput.contentTypes, dInput.scopes, Format.DIRECTORY)
                FileUtils.copyDirectory(dInput.file, dest)
            }

            //jarInputs表示以jar包方式参与项目编译的文件
            input.jarInputs.each { JarInput jarInput -> }
        }
    }

    /**
     * 自定义Transform对应的Task名称。
     * Gradle在编译时会将这个名字显示在控制台上，如：app:transformClassesWithXXXForDebug
     */
    @Override
    String getName() {
        System.out.println("getName()")
        return "PerformanceOpt"
    }

    /**
     * QualifiedContent.ContentType的实现是enum DefaultContentType，其内部常量有两个：
     * CLASSES(0x01) ： 表示只检索.class文件
     * RESOURCES(0x02)：表示只检索Java标准资源文件
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 定义了Transform()检索的范围。
     * PROJECT:            只有项目内容
     * SUB_PROJECTS：      只有子项目(other modules)
     * EXTERNAL_LIBRARIES: 只有外部Lib
     * TESTED_CODE：       由当前变量(包括依赖项)测试的代码
     * PROVIDED_ONLY：     只提供本地或远程依赖项
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否支持增量编译
     */
    @Override
    boolean isIncremental() {
        return false
    }

}

