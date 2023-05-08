package com.rongtuoyouxuan.chatlive.base.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.blankj.utilcode.util.ThreadUtils
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LikeLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LikeLiveReq
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * 
 * date:2022/8/3-09:46
 * des: 点赞数据上传，每2000ms触发一次
 */
class StarManager {
    //是否继续播放
    private var isHanding = false

    //所有点赞数据
    private val starList = LinkedList<LikeLiveReq>()

    private var msgTimer: ScheduledExecutorService? = null
    private var futures: ScheduledFuture<*>? = null
    private var timeTask: Runnable? = null
    private var isHost = false

    fun init(lifecycle: Lifecycle, isHost: Boolean = false) {
        starList.clear()
        startReceive()
        val value = LifecycleEventObserver { _, _ ->
            if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
                stopReceive()
            }
        }
        lifecycle.removeObserver(value)
        lifecycle.addObserver(value)
        this.isHost = isHost
    }

    private fun startReceive() {
//        if (isHanding) {
//            return
//        }
//        isHanding = true
//        timeTask = Runnable {
//            synchronized(starList) {
//                if (starList.isEmpty()) {
//                    return@Runnable
//                }
//                updateStarCount()
//            }
//        }
//        msgTimer = Executors.newScheduledThreadPool(1)
//        futures = msgTimer?.scheduleAtFixedRate(
//            timeTask, 2000, 0, TimeUnit.MILLISECONDS
//        )
    }

    private fun stopReceive() {
//        isHanding = false
//        if (starList.size > 0) {
//            starList.clear()
//        }
//        futures?.cancel(true)
//        msgTimer = null
//        futures = null
//        timeTask = null
//
//        if (starList.isNotEmpty()) {
//            updateStarCount()
//        }
    }

    @Synchronized
    fun addChild(item: LikeLiveReq?) {
//        if (!isHanding) {
//            return
//        }
        if (null == item) {
            return
        }
        starList.offer(item)
        updateStarCount()
    }

    private fun updateStarCount() {
        ThreadUtils.runOnUiThread {
            StreamBiz.liveLike(starList[0].roomId, starList[0].sceneIdStr, starList[0].userIdStr, starList[0].anchorIdStr, 1, null, object :
                RequestListener<LikeLiveData> {
                override fun onSuccess(reqId: String?, result: LikeLiveData?) {
                    starList.clear()
                    if (null != result?.data) {
                        LiveRoomHelper.starVM.post(result.data.likeTotal)
                    }
                }

                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                }
            })
        }
    }
}