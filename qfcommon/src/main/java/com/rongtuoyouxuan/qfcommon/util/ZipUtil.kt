package com.rongtuoyouxuan.qfcommon.util

import com.blankj.utilcode.util.FileUtils
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * 
 * date:2022/8/3-16:35
 * des: 解压缩
 */
object ZipUtil {
    fun unZip(
        zipFile: String?,
        targetDir: String,
        end: (fileName: String, filePath: String) -> Unit
    ) {
        val BUFFER = 4096 // 这里缓冲区我们使用4KB，
        var strEntry: String // 保存每个zip的条目名称
        try {
            var dest: BufferedOutputStream? = null // 缓冲输出流
            val fis = FileInputStream(zipFile)
            val zis = ZipInputStream(
                BufferedInputStream(fis)
            )
            var entry: ZipEntry? = null // 每个zip条目的实例
            while (zis.nextEntry?.also { entry = it } != null) {
                try {
                    var count: Int
                    val data = ByteArray(BUFFER)
                    strEntry = entry!!.name
                    ULog.d(">>GiftManager>>", ">>>unZip>>$strEntry")
                    if (entry!!.isDirectory) {
                        val entryFile = File(
                            targetDir + File.separator
                                    + strEntry
                        )
                        if (!entryFile.exists()) {
                            entryFile.mkdirs()
                        }
                    } else {
                        val entryFile = File(
                            targetDir + File.separator
                                    + strEntry
                        )
                        val fos = FileOutputStream(entryFile)
                        dest = BufferedOutputStream(fos, BUFFER)
                        while (zis.read(data).also { count = it } != -1) {
                            dest.write(data, 0, count)
                        }
                        dest.flush()
                        dest.close()
                        end(strEntry, entryFile.path)
                    }
                } catch (ex: Exception) {
                    ULog.d(">>GiftManager>>", ">>>${zipFile}>>>>------=解析失败")
                }
            }
            zis.close()
        } catch (cwj: Exception) {
            cwj.printStackTrace()
        }
        FileUtils.delete(zipFile)
    }

    fun unZipGame(
        zipFile: String?,
        targetDir: String,
        fileIndex: String,
        end: (filePath: String) -> Unit
    ) {
        val BUFFER = 4096 // 这里缓冲区我们使用4KB，
        var strEntry: String // 保存每个zip的条目名称
        try {
            var dest: BufferedOutputStream? = null // 缓冲输出流
            val fis = FileInputStream(zipFile)
            val zis = ZipInputStream(
                BufferedInputStream(fis)
            )
            var entry: ZipEntry? = null // 每个zip条目的实例
            var indexPath = "" //包含index.html的路径
            while (zis.nextEntry?.also { entry = it } != null) {
                try {
                    var count: Int
                    val data = ByteArray(BUFFER)
                    strEntry = entry!!.name
                    if (entry!!.isDirectory) {
                        val entryFile = File(
                            targetDir + File.separator
                                    + strEntry
                        )
                        if (!entryFile.exists()) {
                            entryFile.mkdirs()
                        }
                    } else {
                        val entryFile = File(
                            targetDir + File.separator
                                    + strEntry
                        )
                        val fos = FileOutputStream(entryFile)
                        dest = BufferedOutputStream(fos, BUFFER)
                        while (zis.read(data).also { count = it } != -1) {
                            dest.write(data, 0, count)
                        }
                        dest.flush()
                        dest.close()
                        if (entryFile.path.contains(fileIndex)) {
                            indexPath = entryFile.path
                        }
                    }
                } catch (ex: Exception) {
                }
            }
            zis.close()
            end(indexPath)
        } catch (cwj: Exception) {
            cwj.printStackTrace()
            end("")
        }
        FileUtils.delete(zipFile)
    }
}