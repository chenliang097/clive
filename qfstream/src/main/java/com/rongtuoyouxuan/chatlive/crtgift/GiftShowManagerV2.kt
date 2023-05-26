package com.rongtuoyouxuan.chatlive.crtgift

import android.content.Context
import android.view.ViewGroup
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTGiftMsg
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.crtgift.view.layout.GiftShowView
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by mah on 2021/6/1.
 */
class GiftShowManagerV2(
    private val giftRootView: ViewGroup,
    private val giftShowViews: Array<GiftShowView>
) : com.rongtuoyouxuan.chatlive.crtgift.interfaces.IGiftShowManager {
    companion object {
        const val TAG = "GiftShowManagerV2"
    }

    private var hostId = ""
    private var giftViewQueues = giftShowViews.map { it.taskQueue }

    override fun add(giftMsg: RTGiftMsg?, context: Context?) {
        giftMsg ?: return
        context ?: return
        val giftShow = GiftEntity(
            userHead = giftMsg?.avatar,
            userId = giftMsg.userId.toString(),
            userName = giftMsg.userName,
            giftName = giftMsg.giftName,
            giftNum = giftMsg.num,
            thumbnail = giftMsg.url_1x)
        ULog.d(TAG, "处理前：$giftViewQueues")
        val isInsert = findAndInsertCombo(giftShow)
        if (!isInsert) {
            giftViewQueues.minByOrNull { it.size }?.offer(ConcurrentLinkedQueue<GiftEntity>().apply { offer(giftShow) })
        }
        ULog.d(TAG, "处理后：$giftViewQueues")
        giftShowViews.forEach { it.action() }
    }

    /**
     * 查找队列中是否已经存在连击礼物，如果有插入到相应位置
     */
    private fun findAndInsertCombo(
        giftShow: GiftEntity
    ): Boolean {
        giftViewQueues.forEach { giftViewQueue ->
            giftViewQueue.forEach { task ->
                task.forEach { item ->
                    if (
                        item.userId == giftShow.userId
                    ) {
                        task.offer(giftShow)
                        ULog.d(TAG, "找到连击队列")
                        return true
                    }
                }
            }
        }
        return false
    }

    override fun setHostId(hostId: String) {
        this.hostId = hostId
        giftShowViews.forEach {
            it.hostId = hostId
        }
    }

    override fun onDestory() {
    }
}