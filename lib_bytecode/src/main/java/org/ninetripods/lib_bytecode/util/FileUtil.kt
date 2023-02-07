package org.ninetripods.lib_bytecode.util

import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by mq on 2023/2/7
 */
object FileUtil {

    fun byte2File(outputPath: String, sourceByte: ByteArray) {
        try {
            val file = File(outputPath)
            if (file.exists()) {
                file.delete()
            } else {
                file.createNewFile()
            }

            val inputStream = ByteArrayInputStream(sourceByte)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var len = 0
            while (inputStream.read(buffer).apply { len = this } != -1) {
                outputStream.write(buffer, 0, len)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}