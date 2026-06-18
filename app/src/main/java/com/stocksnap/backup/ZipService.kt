package com.stocksnap.backup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

object ZipService {

    suspend fun createBackupZip(zipFile: File, filesToZip: List<File>, baseDir: File) {
        withContext(Dispatchers.IO) {
            ZipOutputStream(BufferedOutputStream(FileOutputStream(zipFile))).use { out ->
                for (file in filesToZip) {
                    zipFileOrDirectory(out, file, baseDir)
                }
            }
        }
    }

    private fun zipFileOrDirectory(out: ZipOutputStream, file: File, baseDir: File) {
        if (file.isDirectory) {
            file.listFiles()?.forEach { child ->
                zipFileOrDirectory(out, child, baseDir)
            }
        } else {
            val entryName = file.absolutePath.removePrefix(baseDir.absolutePath).removePrefix(File.separator)
            val entry = ZipEntry(entryName)
            out.putNextEntry(entry)
            FileInputStream(file).use { input ->
                input.copyTo(out)
            }
            out.closeEntry()
        }
    }

    suspend fun unzipBackup(zipFile: File, targetDir: File) {
        withContext(Dispatchers.IO) {
            ZipInputStream(BufferedInputStream(FileInputStream(zipFile))).use { input ->
                var entry: ZipEntry? = input.nextEntry
                while (entry != null) {
                    val file = File(targetDir, entry.name)
                    if (entry.isDirectory) {
                        file.mkdirs()
                    } else {
                        file.parentFile?.mkdirs()
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }
                    input.closeEntry()
                    entry = input.nextEntry
                }
            }
        }
    }
}
