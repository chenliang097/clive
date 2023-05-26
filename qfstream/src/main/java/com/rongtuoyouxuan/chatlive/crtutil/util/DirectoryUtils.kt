package com.rongtuoyouxuan.chatlive.crtutil.util

import android.annotation.SuppressLint
import android.content.Context
import java.io.File

@SuppressLint("StaticFieldLeak")
object DirectoryUtils {

    const val DIRECTORY_VIDEOS = "videos"
    const val DIRECTORY_IMAGES = "images"
    private lateinit var context: Context

    @JvmStatic
    fun init(context: Context) {
        DirectoryUtils.context = context.applicationContext
    }

    @JvmStatic
    fun getFilesDirFile(type: String?): File = context.getExternalFilesDir(type) ?: context.filesDir

    @JvmStatic
    fun getFilesDirStr(type: String?): String = getFilesDirFile(type).absolutePath

    @JvmStatic
    @JvmOverloads
    fun getCacheFilesDirFile(path: String? = null): File {
        val cachePath = context.externalCacheDir ?: context.cacheDir
        return path?.let { File(cachePath, path).apply { if (!exists()) mkdirs() } } ?: cachePath
    }

    @JvmStatic
    fun getCacheFilesDirStr(path: String? = null): String = getCacheFilesDirFile(path).absolutePath
}