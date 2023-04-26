package com.rongtuoyouxuan.libgift.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftLargerAnimEntity
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * 
 * date:2022/8/3-09:46
 * des: 大动画管理/座驾，500ms获取一次数据
 */
class GiftLargerAnimManager {
    //是否继续添加播放--礼物
    private var isHanding = false

    //所有礼物数据
    private val largerAnimList = LinkedList<GiftLargerAnimEntity>()

    //控制取大动画的时机
    private var status = false

    //观察者
    val msgObserver: SingleLiveEvent<Event<GiftLargerAnimEntity>> = SingleLiveEvent()

    //每隔200ms去一次数据
    private var msgTimer: ScheduledExecutorService? = null
    private var futures: ScheduledFuture<*>? = null
    private var timeTask: Runnable? = null

    fun init(lifecycle: Lifecycle) {
        largerAnimList.clear()
        startReceive()
        val value = LifecycleEventObserver { _, _ ->
            if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
                stopReceive()
            }
        }
        lifecycle.removeObserver(value)
        lifecycle.addObserver(value)
    }

    //修改队列状态
    fun updateStatus(status: Boolean) {
        this.status = status
    }

    private fun startReceive() {
        if (isHanding) {
            return
        }
        isHanding = true
        status = true
        timeTask = Runnable {
            synchronized(largerAnimList) {
                if (status) {
                    if (largerAnimList.isEmpty()) {
                        return@Runnable
                    }
                    msgObserver.postValue(Event(largerAnimList.poll()))
                }
            }
        }
        msgTimer = Executors.newScheduledThreadPool(1)
        futures = msgTimer?.scheduleAtFixedRate(
            timeTask, 100, 1000, TimeUnit.MILLISECONDS
        )
    }

    private fun stopReceive() {
        isHanding = false
        if (largerAnimList.size > 0) {
            largerAnimList.clear()
        }
        futures?.cancel(true)
        msgTimer = null
        futures = null
        timeTask = null
    }

    //暂停添加并清除礼物数据，保留座驾数据
    fun pauseReceive() {
        isHanding = false
        status = false
        val list = largerAnimList.filter {
            it.type == 2
        }
        largerAnimList.clear()
        largerAnimList.addAll(list)
        status = true
    }

    //恢复添加
    fun resumeReceive() {
        isHanding = true
    }

    //暂停添加并清除座驾数据，保留礼物数据
    fun pauseCarReceive() {
        status = false
        val list = largerAnimList.filter {
            it.type == 1
        }
        largerAnimList.clear()
        largerAnimList.addAll(list)
        status = true
    }

    //恢复添加
    fun resumeCarReceive() {

    }

    //自己优先加入队列
    @Synchronized
    fun addChildGiftFirst(item: GiftLargerAnimEntity?) {
        if (!isHanding) {
            return
        }
        if (null == item) {
            return
        }
        largerAnimList.offerFirst(item)
    }

    @Synchronized
    fun addChildGift(item: GiftLargerAnimEntity?) {
        if (!isHanding) {
            return
        }
        if (null == item) {
            return
        }
        largerAnimList.offer(item)
    }

    //添加座驾
    @Synchronized
    fun addChildCar(item: GiftLargerAnimEntity?) {
        if (null == item) {
            return
        }
        largerAnimList.offer(item)
    }
}