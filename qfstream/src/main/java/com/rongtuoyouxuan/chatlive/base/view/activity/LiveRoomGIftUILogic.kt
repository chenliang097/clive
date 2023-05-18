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
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTGiftMsg
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
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
    private val isHost: Boolean = false,
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

        giftImg?.let {
            it.setOnClickListener {
                Router.toGiftPanelActivity(
                    activity, roomId, scendId, anchorId, DataBus.instance().USER_ID, DataBus.instance().USER_NAME, DataBus.instance().AVATAR)
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

            roomId?.let { roomId ->
                IMSocketBase.instance().room(roomId).giftMsg.observe(lifecycleOwner) { giftMsg ->
                    //普通礼物
                        val entity = GiftEntity(
                            userHead = giftMsg.avatar,
                            userId = giftMsg.userId.toString(),
                            userName = giftMsg.userName,
                            giftName = giftMsg.giftName,
                            giftNum = giftMsg.num,
                            thumbnail = giftMsg.url_1x)
                        if (!isPlayGift) {
                            return@observe
                        }
                        if (giftMsg.userId.toString() == DataBus.instance().USER_ID) {
                            giftSideManager.addChildGiftFirst(entity)
                        } else {
                            giftSideManager.addChildGift(entity)
                        }
                    }

                clickMarquee(lifecycleOwner, roomId)
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
                starManager.addChild(roomId?.let { it1 -> LikeLiveReq(it1, scendId, DataBus.instance().USER_ID, anchorId) })
            }

            if (isHost) {
//                LiveRoomHelper.starVM2.observe(lifecycleOwner) {
//                    currentStarTotal = it
//                    starText?.text = BigDecimalUtil.setNumber(it)
//                }

//                LiveRoomHelper.starVM.observe(lifecycleOwner) {
//                    //更新点赞数量
//                    starText?.text = BigDecimalUtil.setNumber(it)
//                }
            }
            starManager.init(lifecycle, isHost)

        }

        IMSocketBase.instance().room(roomId).likeMsg.observe(lifecycleOwner){
                if ((it?.userId?.toString() ?: "0") != DataBus.instance().USER_ID) {
                    val total = it.likeCount
                    val num = total - currentStarTotal
                    if (num > 0) {
                        for (i in 0 until num) {
                            activity.lifecycleScope.launch {
                                delay(300 * i)
                                liveBottomStarView?.addChildView()
                            }
                        }
                    }
                }
                currentStarTotal = it.likeCount
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