package com.rongtuoyouxuan.chatlive.live.view.floatwindow

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.log.upload.ULog

class FloatWindowsService : Service() {
    companion object {
        private const val TAG = "FloatWindowsService"

        //is run
        var IS_RUN = false
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        IS_RUN = true
        DataBus.instance().floaT_IS_RUN = IS_RUN
        ULog.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ULog.d(TAG, "onStartCommand")
        intent ?: return START_NOT_STICKY
        var type = intent.type
        if(type == "remove"){
            FloatWindowManager.hideVideoFloatWindow(this){stopSelf()}
        }else{
            FloatWindowManager.showVideoFloatWindow(this, intent) { stopSelf() }
        }


        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        ULog.d(TAG, "onDestroy")
        FloatWindowManager.removeFloatWindowView()
        IS_RUN = false
        DataBus.instance().floaT_IS_RUN = IS_RUN
    }
}