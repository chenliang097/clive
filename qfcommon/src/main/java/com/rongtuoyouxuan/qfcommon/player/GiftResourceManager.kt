package com.rongtuoyouxuan.qfcommon.player

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.coroutineScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.biz2.gift.GiftNewBiz
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.util.FileUtils
import com.rongtuoyouxuan.chatlive.util.ICallFileResult
import com.rongtuoyouxuan.qfcommon.util.ZipUtil
import kotlinx.coroutines.*
import java.io.File

/**
 * 
 * date:2022/8/3-15:11
 * des: 礼物资源下载等
 */
object GiftResourceManager {

    //礼物资源本地地址
    private var giftFilePath = ""

    //本地下载的文件数据-key：文件名称，value：本地地址
    private val localGiftList = hashMapOf<String, String>()

    //初始化文件路径及并下载全量/增量接口开启workManager
    fun init(context: Context, lifecycleCoroutineScope: LifecycleCoroutineScope?) {
        val path = getGiftFilePath(context.applicationContext)
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        giftFilePath = file.path
        getLocalFileList(lifecycleCoroutineScope)
        startWorkManager(context.applicationContext)
    }

    private fun getGiftFilePath(context: Context): String {
        return context.filesDir.absolutePath + File.separatorChar + "gifts"
    }

    //开启下载模式
    private fun startWorkManager(context: Context) {
        val workRequest = OneTimeWorkRequest.Builder(PreResourceDownWorker::class.java)
            .addTag("GiftManager")
            .build()
        WorkManager.getInstance(context).beginWith(workRequest).enqueue()
    }

    private fun getLocalFileList(lifecycleCoroutineScope: LifecycleCoroutineScope?) {
        localGiftList.clear()
        lifecycleCoroutineScope?.launch(Dispatchers.IO) {
            val file = File(giftFilePath)
            if (file.exists()) {
                val listFiles = file.listFiles()
                if (listFiles?.isNotEmpty() == true) {
                    listFiles.forEach {
                        if (it.isFile) {
                            localGiftList[it.name] = it.path
                        }
                    }
                }
            }
        }
    }

    //获取本地MP4文件，没有则下载--1.1.0版本fileKey弃用，使用url获取key
    fun getMp4File(
        url: String,
        lifecycleCoroutineScope: LifecycleCoroutineScope?,
        downPlay: (path: String) -> Unit = { }
    ): String? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        val lastIndex = url.lastIndexOf("/")
        if (lastIndex == -1) {
            return null
        }
        val fileName = url.substring(lastIndex + 1, url.length).replace(".zip", "")
        if (fileName.isEmpty()) {
            return null
        }
        val path = localGiftList[fileName]
        if (path?.isNotEmpty() == true && com.blankj.utilcode.util.FileUtils.isFileExists(path)) {
            return path
        }
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            ULog.d("GiftResourceManager", ">>>礼物下载失败--$url")
        }
        //单独下载
        lifecycleCoroutineScope?.launch(Dispatchers.IO + exceptionHandler) {
            downFile(url, downPlay)
        }
        return null
    }

    //获取本地MP4文件，没有则下载
    fun getMp4File(
        url: String,
        lifecycle: Lifecycle?
    ): String? {
        if (TextUtils.isEmpty(url)) {
            return null
        }
        val lastIndex = url.lastIndexOf("/")
        if (lastIndex == -1) {
            return null
        }
        val fileName = url.substring(lastIndex + 1, url.length).replace(".zip", "")
        if (fileName.isEmpty()) {
            return null
        }
        val path = localGiftList[fileName]
        if (path?.isNotEmpty() == true && com.blankj.utilcode.util.FileUtils.isFileExists(path)) {
            return path
        }
        //单独下载
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            ULog.d("GiftResourceManager", ">>>礼物下载失败--$url")
        }
        lifecycle?.coroutineScope?.launch(Dispatchers.IO + exceptionHandler) {
            downFile(url)
        }
        return null
    }

    suspend fun downFile(
        url: String,
        downPlay: (path: String) -> Unit = { }
    ) {
        coroutineScope {
            if (TextUtils.isEmpty(url)) {
                return@coroutineScope
            }
            if (url.startsWith("http") || url.startsWith("https")) {
                if (!url.contains(".zip")) {
                    return@coroutineScope
                }
                val lastIndex = url.lastIndexOf("/")
                if (lastIndex == -1) {
                    return@coroutineScope
                }
                val zipFileName = url.substring(lastIndex + 1, url.length)
                if (zipFileName.isEmpty()) {
                    return@coroutineScope
                }
                val localFileName = zipFileName.replace(".zip", "")
                if (null != localGiftList[localFileName]) {
                    //本地已有数据，不在下载
                    return@coroutineScope
                }

                val result = async {
                    GiftNewBiz.getService().downloadFileUrl(url)
                }
                val stream = result.await().byteStream()
                stream.let {
                    val file = File(giftFilePath.plus("/").plus(zipFileName))
                    FileUtils.writeToFile(it, file, object : ICallFileResult {
                        override fun onSuccess() {
                            ZipUtil.unZip(file.path, giftFilePath) { fileName, filePath ->
                                localGiftList[fileName] = filePath
                                downPlay(filePath)
                            }
                        }
                    })
                }
            }
        }
    }

    fun setGiftResourceTime(timestamp: Long) {
        SPUtils.getInstance().put("gift_resource_time", timestamp)
    }

    fun setCarResourceTime(timestamp: Long) {
        SPUtils.getInstance().put("car_resource_time", timestamp)
    }

    fun clearTime() {
        SPUtils.getInstance().put("gift_resource_time", 0L)
        SPUtils.getInstance().put("car_resource_time", 0L)
    }
}