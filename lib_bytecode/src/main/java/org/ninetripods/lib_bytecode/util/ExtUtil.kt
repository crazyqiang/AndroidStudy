package org.ninetripods.lib_bytecode.util

import com.android.build.gradle.api.BaseVariant
import com.android.dex.DexFormat
import com.android.dx.command.dexer.Main
import org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream
import org.apache.commons.compress.parallel.InputStreamSupplier
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * Created by mq on 2023/2/21
 */
fun MethodNode.isGetSetMethod(): Boolean {
    var ignoreCount = 0
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        }
        if (opcode != Opcodes.GETFIELD && opcode != Opcodes.GETSTATIC && opcode != Opcodes.H_GETFIELD && opcode != Opcodes.H_GETSTATIC && opcode != Opcodes.RETURN && opcode != Opcodes.ARETURN && opcode != Opcodes.DRETURN && opcode != Opcodes.FRETURN && opcode != Opcodes.LRETURN && opcode != Opcodes.IRETURN && opcode != Opcodes.PUTFIELD && opcode != Opcodes.PUTSTATIC && opcode != Opcodes.H_PUTFIELD && opcode != Opcodes.H_PUTSTATIC && opcode > Opcodes.SALOAD) {
            if (name.equals("<init>") && opcode == Opcodes.INVOKESPECIAL) {
                ignoreCount++
                if (ignoreCount > 1) {
                    return false
                }
                continue
            }
            return false
        }
    }
    return true
}

fun MethodNode.isSingleMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        } else if (opcode in Opcodes.INVOKEVIRTUAL..Opcodes.INVOKEDYNAMIC) {
            return false
        }
    }
    return true
}

fun MethodNode.isEmptyMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        return if (-1 == opcode) {
            continue
        } else {
            false
        }
    }
    return true
}

fun MethodNode.isMainMethod(className: String): Boolean {
    if (this.name == "main" && this.desc == "([Ljava/lang/String;)V") {
//        "====isMainMethod====$className  ${this.name}   ${this.desc}   ${this.access}".println()
        return true
    }

    return false
}


fun InsnList.getMethodExitInsnNodes(): Sequence<InsnNode>? {
    return this.iterator().asSequence().filterIsInstance(InsnNode::class.java).filter {
        it.opcode == Opcodes.RETURN ||
                it.opcode == Opcodes.IRETURN ||
                it.opcode == Opcodes.FRETURN ||
                it.opcode == Opcodes.ARETURN ||
                it.opcode == Opcodes.LRETURN ||
                it.opcode == Opcodes.DRETURN ||
                it.opcode == Opcodes.ATHROW
    }
}

fun BaseVariant.isRelease(): Boolean {
    if (this.name.contains("release") || this.name.contains("Release")) {
        return true
    }
    return false
}


//fun TransformContext.isRelease(): Boolean {
//    if (this.name.contains("release") || this.name.contains("Release")) {
//        return true
//    }
//    return false
//}


//fun String.println() {
//    if (DoKitExtUtil.dokitLogSwitchOpen()) {
//        println("[dokit plugin]===>$this")
//    }
//}

fun File.lastPath(): String {
    return this.path.split("/").last()
}

val MethodInsnNode.ownerClassName: String
    get() = owner.replace('/', '.')


val ClassNode.formatSuperName: String
    get() = superName.replace('/', '.')

internal fun File.dex(output: File, api: Int = DexFormat.API_NO_EXTENDED_OPCODES): Int {
    val args = Main.Arguments().apply {
        numThreads = Runtime.getRuntime().availableProcessors()
        debug = true
        warnings = true
        emptyOk = true
        multiDex = true
        jarOutput = true
        optimize = false
        minSdkVersion = api
        fileNames = arrayOf(output.canonicalPath)
        outName = canonicalPath
    }
    return try {
        Main.run(args)
    } catch (t: Throwable) {
        t.printStackTrace()
        -1
    }
}

/**
 * Transform this file or directory to the output by the specified transformer
 *
 * @param output The output location
 * @param transformer The byte data transformer
 */
//fun File.dokitTransform(output: File, transformer: (ByteArray) -> ByteArray = { it -> it }) {
//    when {
//        isDirectory -> this.toURI().let { base ->
//            this.search().parallelStream().forEach {
//                it.transform(File(output, base.relativize(it.toURI()).path), transformer)
//            }
//        }
//        isFile -> when (extension.toLowerCase()) {
//            "jar" -> JarFile(this).use {
//                it.dokitTransform(output,
//                    org.apache.commons.compress.archivers.jar::JarArchiveEntry, transformer)
//            }
//            "class" -> this.inputStream().use {
//                it.transform(transformer).redirect(output)
//            }
//            else -> this.copyTo(output, true)
//        }
//        else -> throw IOException("Unexpected file: ${this.canonicalPath}")
//    }
//}

//fun ZipFile.dokitTransform(
//    output: File,
//    entryFactory: (ZipEntry) -> ZipArchiveEntry = ::ZipArchiveEntry,
//    transformer: (ByteArray) -> ByteArray = { it -> it }
//) = output.touch().outputStream().buffered().use {
//    this.dokitTransform(it, entryFactory, transformer)
//}


fun ZipFile.dokitTransform(
    output: OutputStream,
    entryFactory: (ZipEntry) -> ZipArchiveEntry = ::ZipArchiveEntry,
    transformer: (ByteArray) -> ByteArray = { it -> it }
) {
    val entries = mutableSetOf<String>()
    val creator = ParallelScatterZipCreator(
        ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors(),
            0L,
            TimeUnit.MILLISECONDS,
            LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(),
            RejectedExecutionHandler { runnable, _ ->
                runnable.run()
            })
    )
    //将jar包里的文件序列化输出
    entries().asSequence().forEach { entry ->
        if (!entries.contains(entry.name)) {
            val zae = entryFactory(entry)

            val stream = InputStreamSupplier {
                when (entry.name.substringAfterLast('.', "")) {
                    "class" -> getInputStream(entry).use { src ->
                        try {
                            src.transform(transformer).inputStream()
                        } catch (e: Throwable) {
                            System.err.println("Broken class: ${this.name}!/${entry.name}")
                            getInputStream(entry)
                        }
                    }
                    else -> getInputStream(entry)
                }
            }

            creator.addArchiveEntry(zae, stream)
            entries.add(entry.name)
        } else {
            System.err.println("Duplicated jar entry: ${this.name}!/${entry.name}")
        }
    }
    val zip = ZipArchiveOutputStream(output)
    zip.use { zipStream ->
        try {
            creator.writeTo(zipStream)
            zipStream.close()
        } catch (e: Exception) {
            zipStream.close()
//            e.printStackTrace()
//            "e===>${e.message}".println()
            System.err.println("Duplicated jar entry: ${this.name}!")
        }
    }
}

fun InputStream.transform(transformer: (ByteArray) -> ByteArray): ByteArray {
    return transformer(readBytes())
}