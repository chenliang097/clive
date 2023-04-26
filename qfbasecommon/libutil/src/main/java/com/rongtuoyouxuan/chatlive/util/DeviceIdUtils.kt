package com.rongtuoyouxuan.chatlive.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.Utils
import com.rongtuoyouxuan.chatlive.log.OSUtils
import com.rongtuoyouxuan.chatlive.log.PLog
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

object DeviceIdUtils {
    const val tag = "DeviceIdUtils"
    const val fileName = "device_id.qf"

    private val genDeviceIdCallback: CopyOnWriteArrayList<Handler.Callback> = CopyOnWriteArrayList()
    private val isHaveGenDeviceId = AtomicBoolean(false)
    private var deviceId = ""

    /**
     * 异步获取device id, 如果没有自动生成
     * android.os.Build.VERSION.SDK_INT <= 29 使用 uuid 存储到sd卡， >= 30 使用google ad id,保存到私有目录
     */
    @JvmStatic
    @Synchronized
    fun getOrGenDeviceId(context: Context, callback: Handler.Callback?) {
        PLog.d(tag, "getOrGenDeviceId")
        if (!TextUtils.isEmpty(deviceId)) {
            notifyAll(deviceId)
            return
        }

        val deviceId = getDeviceIDFromDisk(context)
        if (!TextUtils.isEmpty(deviceId)) {
            PLog.d(tag, "has id notify $deviceId")
            notifyAll(deviceId)
            return
        }

        if (callback != null) {
            genDeviceIdCallback.add(callback)
        }

        if (!OSUtils.isRLater() &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PLog.d(tag, "os < 30 , no have Manifest.permission.WRITE_EXTERNAL_STORAGE")
            return
        }

        genDeviceId(context)
    }

    private fun genDeviceId(context: Context) {
        if (isHaveGenDeviceId.get()) return
        isHaveGenDeviceId.set(true)
        object : Thread() {
            override fun run() {
                val id: String
                try {
                    PLog.d(tag, "gen deviceId")
                    id = if (OSUtils.isRLater()) {
                        PLog.d(tag, "get google ad id....")
                        try {
                            GoogleAdIdUtils.getGooglePlayServicesInfo(context, 1000 * 11).gpsAdid
                        } catch (e: Exception) {
                            PLog.d(tag, "use uuid 1")
                            e.printStackTrace()
                            UUIDUtil.getUUID()
                        }
                    } else {
                        PLog.d(tag, "use uuid 2")
                        UUIDUtil.getUUID()
                    }
                    PLog.d(tag, "gen device id success  deviceId: $id")
                    FileUtils.writetoFile(getDeviceIdFilePath(context), fileName, id)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    val idFromDisk = getDeviceIDFromDisk(context)
                    if (!TextUtils.isEmpty(idFromDisk)) {
                        Handler(Looper.getMainLooper()).post {
                            notifyAll(idFromDisk)
                        }
                    }
                }
            }
        }.start()
    }

    @JvmStatic
    fun getDeviceIDFromDisk(context: Context): String {
        PLog.d(tag, "getDeviceIDFromDisk")
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId
        }
        try {
            val deviceIdFile = getDeviceIdFilePath(context) + File.separatorChar + fileName
            return readDeviceIdFromDisk(context, deviceIdFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun notifyAll(deviceId: String) {
        PLog.d(tag, "notifyAll deviceId:$deviceId")
        genDeviceIdCallback.forEach {
            it.handleMessage(Message().apply { obj = deviceId })
        }
        genDeviceIdCallback.clear()
    }

    private fun getDeviceIdFilePath(context: Context): String {
        return if (OSUtils.isRLater()) {
            context.filesDir.absolutePath + File.separatorChar + "ids"
        } else {
            Environment.getExternalStorageDirectory().absolutePath + File.separatorChar + "boboo" + File.separatorChar + "ids"
        }
    }

    private fun readDeviceIdFromDisk(context: Context, filePath: String): String {
        if (!OSUtils.isRLater() &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            PLog.d(tag, "os < 30 , no have Manifest.permission.WRITE_EXTERNAL_STORAGE")
            return ""
        }
        val deviceId = FileUtils.getStringFromSD(filePath)
        if (deviceId != null) {
            return deviceId.trim()
        }
        return ""
    }

    // 是否阿语地区
    fun isSupportRTL(): Boolean {
        return Utils.getApp().resources?.configuration?.layoutDirection == View.LAYOUT_DIRECTION_RTL
    }
}