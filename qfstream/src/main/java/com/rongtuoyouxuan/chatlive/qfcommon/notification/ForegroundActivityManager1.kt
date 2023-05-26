package com.rongtuoyouxuan.chatlive.qfcommon.notification

import android.app.Activity
import java.lang.ref.WeakReference

/**
 * 前台Activity管理类
 */
class ForegroundActivityManager1 {

    private var currentActivityWeakRef: WeakReference<Activity>? = null

    companion object {
        val TAG = "ForegroundActivityManager"
        private val instance = ForegroundActivityManager1()

        @JvmStatic
        fun getInstance(): ForegroundActivityManager1 {
            return instance
        }
    }


    fun getCurrentActivity(): Activity? {
        var currentActivity: Activity? = null
        if (currentActivityWeakRef != null) {
            currentActivity = currentActivityWeakRef?.get()
        }
        return currentActivity
    }


    fun setCurrentActivity(activity: Activity) {
        currentActivityWeakRef = WeakReference(activity)
    }

}