//package com.performance.optimize
//
//import com.android.build.api.transform.*
//import com.android.build.gradle.internal.pipeline.TransformManager
//import groovy.io.FileType
//
//class ATransform extends Transform {
//
//    @Override
//    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
////        super.transform(transformInvocation)
//        Collection<TransformInput> transformInputs = transformInvocation.inputs
//        System.out.println("transformInputs: " + transformInputs.size())
//        transformInputs.each { TransformInput input ->
//            input.directoryInputs.each { DirectoryInput dInput ->
//                File dir = dInput.file
//                if (dir) {
//                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
//                        System.out.println("find class: " + file.name)
//                    }
//                }
//            }
//        }
//    }
//
//    @Override
//    String getName() {
//        return "PerformanceOpt"
//    }
//
//    @Override
//    Set<QualifiedContent.ContentType> getInputTypes() {
//        return TransformManager.CONTENT_CLASS
//    }
//
//    @Override
//    Set<? super QualifiedContent.Scope> getScopes() {
//        return TransformManager.PROJECT_ONLY
//    }
//
//    @Override
//    boolean isIncremental() {
//        return false
//    }
//
//}
//
