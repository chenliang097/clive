package com.rongtuoyouxuan.chatlive.base.view.activity

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.base.utils.StarManager
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.view.dialog.LiveAnimConfigDialog
import com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout.BroadcastGiftLayout
import com.rongtuoyouxuan.chatlive.base.view.widget.LiveBottomStarView
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftLargerAnimEntity
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.FullSiteGiftMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveJoinRoomMsg
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LikeLiveReq
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomBannerGift
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveRoomViewModel
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.bean.ISource
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.BigDecimalUtil
import com.rongtuoyouxuan.libgift.utils.GiftLargerAnimManager
import com.rongtuoyouxuan.libgift.utils.GiftSideManager
import com.rongtuoyouxuan.libgift.viewmodel.GiftHelper
import com.rongtuoyouxuan.libgift.widget.GiftSidebarLinearLayout
import com.rongtuoyouxuan.qfcommon.player.GiftLargerAnimPlayView
import com.rongtuoyouxuan.qfcommon.player.GiftResourceManager
import com.rongtuoyouxuan.qfcommon.util.DiySystemDialogUtil
import com.lxj.xpopup.XPopup
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * des: 直播间处理gift，star，通道，跑马灯等
 */
class LiveRoomGIftUILogic(
    private val activity: FragmentActivity,
    private val roomId: String? = "",
    private val scendId: String? = "",
    private val anchorId: String? = "",
    private val giftImg: View? = null,
    private val starImg: View? = null,
    private val starText: TextView? = null,
    private val giftSide: GiftSidebarLinearLayout? = null,
    private val giftPlayView: GiftLargerAnimPlayView? = null,
    private val liveBottomStarView: LiveBottomStarView? = null,
    private val broadcastGiftLayout: BroadcastGiftLayout? = null,
    private val isHost: Boolean = true,
) {
    var currentStarTotal = 0L

    private var isPlayGift = true
    private var isPlayCar = true

    private val giftSideManager: GiftSideManager by lazy {
        GiftSideManager()
    }

    private val giftLargerAnimManager: GiftLargerAnimManager by lazy {
        GiftLargerAnimManager()
    }

    private val starManager: StarManager by lazy {
        StarManager()
    }

    fun init(fragment: Fragment? = null) {
        val lifecycle = fragment?.lifecycle ?: activity.lifecycle
        val lifecycleOwner = fragment ?: activity

        val userInfo = DataBus.instance().userInfo.value?.user_info

        giftImg?.let {
            it.setOnClickListener {
                Router.toGiftPanelActivity(
                    activity, roomId, scendId, anchorId, StreamPreviewLayout.USER_ID, StreamPreviewLayout.USER_NAME, "")
            }
        }

        giftSide?.let {
            giftSideManager.init(activity, lifecycle, it, it.getSideNum())
        }

        giftPlayView?.let {
            giftLargerAnimManager.msgObserver.observe(lifecycleOwner) {
                if (it.peekContent() == null) return@observe
                when (it.peekContent().type) {
                    1 -> {
                        giftPlayView.playGift(
                            activity,
                            it.peekContent(),
                            object :
                                GiftLargerAnimPlayView.AnimationStatusCallBack {
                                override fun animationStart() {
                                    giftLargerAnimManager.updateStatus(false)
                                }

                                override fun animationEnd() {
                                    giftLargerAnimManager.updateStatus(true)
                                }
                            }
                        )
                    }
                    2 -> {
                        giftPlayView.playCsr(
                            activity,
                            it.peekContent(),
                            object :
                                GiftLargerAnimPlayView.AnimationStatusCallBack {
                                override fun animationStart() {
                                    giftLargerAnimManager.updateStatus(false)
                                }

                                override fun animationEnd() {
                                    giftLargerAnimManager.updateStatus(true)
                                }
                            }
                        )
                    }
                }
            }
            giftLargerAnimManager.init(lifecycle)

            scendId?.let { liveId ->
//                IMSocketImpl.getInstance()
//                    .chatRoom(liveId).giftCallback?.observe(lifecycleOwner) { giftMsg ->
//                        ULog.d(
//                            "giftCallBack",
//                            ">>giftCallback:${giftMsg.giftLevel}>>>${giftMsg.giftMark}"
//                        )
//                        if (giftMsg.giftLevel == "1") {
//                            //普通礼物
//                            giftMsg.from?.let { from ->
//                                val entity = GiftEntity(
//                                    userHead = from.avatar,
//                                    userName = from.nickname,
//                                    giftName = giftMsg.giftName,
//                                    giftNum = giftMsg.num,
//                                    thumbnail = giftMsg.giftImgUrl,
//                                    giftExtra = giftMsg.extra
//                                )
//                                createMarquee(entity)
//                                if (!isPlayGift) {
//                                    return@observe
//                                }
//                                if (from.userId.toLong() == userInfo?.userId) {
//                                    giftSideManager.addChildGiftFirst(entity)
//                                } else {
//                                    giftSideManager.addChildGift(entity)
//                                }
//                            }
//                        } else if (giftMsg.giftLevel == "2") {
//                            giftMsg.extra?.let {
//                                giftMsg.from?.let { from ->
//                                    val entity = GiftEntity(
//                                        userHead = from.avatar,
//                                        userName = from.nickname,
//                                        giftName = giftMsg.giftName,
//                                        giftNum = giftMsg.num,
//                                        thumbnail = giftMsg.giftImgUrl,
//                                        giftExtra = it
//                                    )
//                                    createMarquee(entity)
//                                    if (!isPlayGift) {
//                                        return@observe
//                                    }
//                                    if (giftMsg.giftMark == 1) {
//                                        if (from.userId.toLong() == userInfo?.userId) {
//                                            giftSideManager.addChildGiftFirst(entity)
//                                        } else {
//                                            giftSideManager.addChildGift(entity)
//                                        }
//                                    }
//                                }
//                            }
//                            if (!isPlayGift) {
//                                return@observe
//                            }
//                            //高级礼物
//                            if (giftMsg.giftResources?.isNotEmpty() == true) {
//                                val giftFileZip = giftMsg.giftResources[0]
//                                val filePath =
//                                    GiftResourceManager.getMp4File(
//                                        giftFileZip,
//                                        activity.lifecycleScope
//                                    ) {
//                                        giftMsg.from?.let { from ->
//                                            val entity = GiftLargerAnimEntity(
//                                                1,
//                                                from.avatar ?: "",
//                                                from.nickname ?: "",
//                                                it
//                                            )
//                                            if (from.userId.toLong() == userInfo?.userId) {
//                                                if (giftMsg.num > 1) {
//                                                    for (i in 0 until giftMsg.num) {
//                                                        giftLargerAnimManager.addChildGiftFirst(
//                                                            entity
//                                                        )
//                                                    }
//                                                } else {
//                                                    giftLargerAnimManager.addChildGiftFirst(entity)
//                                                }
//                                            } else {
//                                                if (giftMsg.num > 1) {
//                                                    for (i in 0 until giftMsg.num) {
//                                                        giftLargerAnimManager.addChildGift(entity)
//                                                    }
//                                                } else {
//                                                    giftLargerAnimManager.addChildGift(entity)
//                                                }
//                                            }
//                                        }
//                                    }
//                                if (filePath?.isNotEmpty() == true) {
//                                    giftMsg.from?.let { from ->
//                                        val entity = GiftLargerAnimEntity(
//                                            1,
//                                            from.avatar ?: "",
//                                            from.nickname ?: "",
//                                            filePath
//                                        )
//                                        if (from.userId.toLong() == userInfo?.userId) {
//                                            if (giftMsg.num > 1) {
//                                                for (i in 0 until giftMsg.num) {
//                                                    giftLargerAnimManager.addChildGiftFirst(entity)
//                                                }
//                                            } else {
//                                                giftLargerAnimManager.addChildGiftFirst(entity)
//                                            }
//                                        } else {
//                                            if (giftMsg.num > 1) {
//                                                for (i in 0 until giftMsg.num) {
//                                                    giftLargerAnimManager.addChildGift(entity)
//                                                }
//                                            } else {
//                                                giftLargerAnimManager.addChildGift(entity)
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }

                clickMarquee(lifecycleOwner, liveId)
            }
        }

        starImg?.let {
            it.setOnClickListener {
                val isVisitor = DataBus.instance().isVisitor
                if (isVisitor) {
                    LiveDataBus.getInstance()
                        .with(LiveDataBusConstants.EVENT_KEY_TO_SHOW_LOGIN_DIALOG).value = true
                    return@setOnClickListener
                }
                liveBottomStarView?.addChildView()
                scendId?.toLongOrNull()?.let {
                    starManager.addChild(LikeLiveReq(it, 1))
                }
            }

            if (isHost) {
                LiveRoomHelper.starVM2.observe(lifecycleOwner) {
                    currentStarTotal = it
                    starText?.text = BigDecimalUtil.setNumber(it)
                }

                LiveRoomHelper.starVM.observe(lifecycleOwner) {
                    //更新点赞数量
                    starText?.text = BigDecimalUtil.setNumber(it)
                }
            }
            starManager.init(lifecycle, isHost)

//            IMSocketImpl.getInstance()
//                .chatRoom(streamID).liveLikeNumCallBack?.observe(lifecycleOwner) { entity ->
//                    if ((entity?.from?.userId?.toLongOrNull() ?: 0L) != userInfo?.userId) {
//                        val total = entity.likesNum
//                        val num = total - currentStarTotal
//                        if (num > 0) {
//                            for (i in 0 until num) {
//                                activity.lifecycleScope.launch {
//                                    delay(300 * i)
//                                    liveBottomStarView?.addChildView()
//                                }
//                            }
//                        }
//                    }
//                    currentStarTotal = entity.likesNum
//                    if (isHost) {
//                        starText?.text = BigDecimalUtil.setNumber(entity.likesNum)
//                    }
//                }
        }

        broadcastGiftLayout?.let {
            scendId?.let {
//                IMSocketImpl.getInstance()
//                    .chatRoom(it).fullSiteGiftCallBack?.observe(lifecycleOwner) { entity ->
//                        ULog.d(
//                            "giftCallBack",
//                            ">>fullSiteGiftCallBack:${entity.giftLevel}>>"
//                        )
//                        if (!isPlayGift) {
//                            return@observe
//                        }
//                        val from = RoomBannerGift.FromBean()
//                        if (entity?.from?.nickname?.isNotEmpty() == true) {
//                            from.nick = entity.from?.nickname
//                        } else {
//                            from.nick = ""
//                        }
//                        val to = RoomBannerGift.ToBean()
//                        if (entity?.to?.nickname?.isNotEmpty() == true) {
//                            to.nick = entity.to?.nickname
//                        } else {
//                            to.nick = ""
//                        }
//                        val roomBannerGift = RoomBannerGift()
//                        roomBannerGift.from = from
//                        roomBannerGift.to = to
//                        roomBannerGift.giftUrl = entity?.giftThumbnail ?: ""
//                        roomBannerGift.count = entity?.num ?: 1
//                        roomBannerGift.giftExtra = entity.giftExtra
//                        roomBannerGift.roomId = entity.roomId
//                        if (null != entity.giftExtra) {
//                            roomBannerGift.type = 1
//                        } else {
//                            roomBannerGift.type = 0
//                        }
//                        broadcastGiftLayout.addWinningMsg(roomBannerGift)
//
//                        if (roomBannerGift.type == 0 && entity.giftLevel == 2) {
//                            entity.from?.let { xFrom ->
//                                val xEntity = GiftEntity(
//                                    userHead = xFrom.avatar,
//                                    userName = xFrom.nickname,
//                                    giftName = entity.giftName,
//                                    giftNum = entity.num,
//                                    thumbnail = entity.giftImgUrl,
//                                )
//                                if (xFrom.userId.toLong() == userInfo?.userId) {
//                                    giftSideManager.addChildGiftFirst(xEntity)
//                                } else {
//                                    giftSideManager.addChildGift(xEntity)
//                                }
//                            }
//                        }
//                    }

            }
        }

        GiftHelper.giftConfigAnim.observe(lifecycleOwner) {
            isPlayGift = it == 1
            if (isPlayGift) {
                giftSideManager.resumeReceive()
                giftLargerAnimManager.resumeReceive()
            } else {
                giftSideManager.pauseReceive()
                giftLargerAnimManager.pauseReceive()
            }
        }

        GiftHelper.carConfigAnim.observe(lifecycleOwner) {
            isPlayCar = it == 1
            if (isPlayCar) {
                giftLargerAnimManager.resumeCarReceive()
            } else {
                giftLargerAnimManager.pauseCarReceive()
            }
        }

    }

    fun playCar(giftEntity: LiveJoinRoomMsg) {
        if (!isPlayCar) {
            return
        }

    }

    //打开动效设置
    fun showAnimDialog() {
        XPopup.Builder(activity)
            .enableDrag(false)
            .asCustom(
                LiveAnimConfigDialog(
                    activity,
                    isPlayGift,
                    isPlayCar
                )
            )
            .show()
    }

    //监听点击跑马灯
    private fun clickMarquee(lifecycleOwner: LifecycleOwner, liveId: String) {

    }

}