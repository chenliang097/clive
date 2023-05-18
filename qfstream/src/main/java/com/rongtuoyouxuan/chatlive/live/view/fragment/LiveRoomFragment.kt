package com.rongtuoyouxuan.chatlive.live.view.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.DebouncingUtils
import com.blankj.utilcode.util.StringUtils
import com.lxj.xpopup.XPopup
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.base.DialogUtils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.view.activity.LiveRoomGIftUILogic
import com.rongtuoyouxuan.chatlive.base.view.activity.LiveRoomOnlineLogic
import com.rongtuoyouxuan.chatlive.base.view.dialog.AnchorBlockedTipDialog
import com.rongtuoyouxuan.chatlive.base.view.dialog.RecommendDialog
import com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout.BroadcastGiftLayout
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveAudienceNotificationMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTLiveEndMsg
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorInfo
import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.live.LiveManager
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay
import com.rongtuoyouxuan.chatlive.live.view.activity.LiveRoomActivity
import com.rongtuoyouxuan.chatlive.live.view.dialog.AudienceExitDialog
import com.rongtuoyouxuan.chatlive.live.view.dialog.LiveLightBoardDialog
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.FloatWindowsService
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.FloatingWindowHelper
import com.rongtuoyouxuan.chatlive.live.view.layout.BasePlayerView
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveRoomViewModel
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.layout.LivePublicChatAreaListLayout
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
import com.rongtuoyouxuan.libuikit.SimpleFragment
import com.rongtuoyouxuan.qfcommon.dialog.CommonBottomDialog
import com.rongtuoyouxuan.qfcommon.dialog.UserCardDialog
import com.rongtuoyouxuan.qfcommon.util.UserCardHelper
import com.zhihu.matisse.dialog.DiySystemDialog
import kotlinx.android.synthetic.main.qf_stream_live_fragment_live_room.*
import kotlinx.android.synthetic.main.qf_stream_live_layout_intercation_fix.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LiveRoomFragment : SimpleFragment() {

    private var viewModel: LiveRoomViewModel? = null
    private var imViewModel: IMLiveViewModel? = null
    private var mLiveControllerViewModel: LiveControllerViewModel? = null

    private var roomInfoBean: EnterRoomBean? = null
    private var isFollow: Boolean? = false
    private var anchorId: String? = ""
    private var streamId: String? = ""
    private var sceneId: String? = ""
    private var roomId: String? = ""
    private var anchorAvatar: String? = ""
    private var anchorCoverImg: String? = ""
    private var recoverIs: Boolean? = false
    private var isPageScrolled: Boolean = false
    private var streamType: String = ""
    private var liveStreamcontainer: FrameLayout? = null
    private var isHide = false

    private var groupId = 0L

    var observer: Observer<String> = Observer { msg -> LaToastUtil.showShort(msg) }

    private var liveEndObserver: Observer<RTLiveEndMsg> = Observer {
        Router.toLiveEndActivity(
            (mContext as LiveRoomActivity),
            roomInfoBean?.data?.room_id_str,
            roomInfoBean?.data?.user_avatar,
            roomInfoBean?.data?.anchor_name
        )
        LiveDataBus.getInstance()
            .with(LiveDataBusConstants.EVENT_KEY_TO_FINISH_ROOM_ACTIVITY).value = true
        (mContext as LiveRoomActivity)?.finish()
    }

    private var liveNotifyCallback: Observer<LiveAudienceNotificationMsg> = Observer {
        showNotifyMsgDialog(it)
    }

    private var gIftUILogic: LiveRoomGIftUILogic? = null
    private var onlineLogic: LiveRoomOnlineLogic? = null

    companion object {
        fun newInstance(
            sceneId: String?,
            roomId: String?,
            anchorId: String?,
            streamId: String?,
            recoverIs: Boolean?,
            isPageScrolled: Boolean?,
            streamType: Int?,
        ): LiveRoomFragment {
            val bundle = Bundle()
            bundle.putString("sceneId", sceneId)
            bundle.putString("roomId", roomId)
            bundle.putString("anchorId", anchorId)
            bundle.putString("streamId", streamId)
            if (streamType != null) {
                bundle.putInt("streamType", streamType)
            }
            if (recoverIs != null) {
                bundle.putBoolean("recoverIs", recoverIs)
            } else {
                bundle.putBoolean("recoverIs", false)
            }
            if (isPageScrolled != null) {
                bundle.putBoolean("position", isPageScrolled)
            }
            val fragment = LiveRoomFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        ViewModelUtils.setLiveFragment(this)
        super.onAttach(context)
    }

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_live_fragment_live_room
    }

    override fun initData() {
        sceneId = arguments?.get("sceneId").toString()
        roomId = arguments?.get("roomId").toString()
        anchorId = arguments?.get("anchorId").toString()
        streamId = arguments?.get("streamId").toString()
        recoverIs = arguments?.get("recoverIs") as Boolean
        isPageScrolled = arguments?.get("position") as Boolean
        var streamTypeInt = arguments?.get("streamType") as Int
        when (streamTypeInt) {
            1 -> {
                (mContext as LiveRoomActivity).updateStreamType(StreamPreviewLayout.TYPE_LIVE)
                streamType = StreamPreviewLayout.TYPE_LIVE
            }
            else -> {
                (mContext as LiveRoomActivity).updateStreamType(StreamPreviewLayout.TYPE_LIVE)
                streamType = StreamPreviewLayout.TYPE_LIVE
            }
        }

        initViewModel()
        initView()
        initObserver()
        reloadData()
    }

    override fun initListener() {
        requireActivity().onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelUtils.getLive(LiveRoomViewModel::class.java)
        imViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
        mLiveControllerViewModel = ViewModelUtils.getLive(LiveControllerViewModel::class.java)
    }

    private fun initView() {
        liveStreamcontainer = mRootView.findViewById(R.id.liveStreamcontainer)
        live_clear_layout?.setScrollView(ll_controllerview)
        live_clear_layout.setHideListener {
            isHide = it
        }
        dl_drawerlayout.setRightToLeftCallBack {
            if (!isHide) {
                RecommendDialog.showDialog(mContext)
            }
        }
        initListener1()

    }

    private fun initListener1() {
        view_measured?.viewTreeObserver?.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    view_measured?.viewTreeObserver?.removeOnPreDrawListener(this)
                    view_measured?.width?.let {
                        lc_livefixintercation?.setParams(it, view_measured?.height!!)
                    }
                    return true
                }
            })
        iv_close_master_play?.setOnClickListener(View.OnClickListener {
            if (!DebouncingUtils.isValid(it, 1000)) return@OnClickListener
            onBackPressed()
        })

        lc_livefixintercation?.findViewById<RoundedImageView>(R.id.iv_room_master_headimg)
            ?.setOnClickListener {
                LiveRoomHelper.openUserCardVM.post(anchorId)
            }
    }

    private fun initObserver() {
        mLiveControllerViewModel!!.showToast.observeOnce(this, observer)
        imViewModel!!.showTransteDialog.observeOnce(this) { msg ->
            val dialog = DiySystemDialog.Builder(mContext)
            dialog.setMessage(R.string.translate)
            dialog.setIsCancle(true)
            dialog.setOnKeyCancle(true)
            dialog.setPositiveButton(R.string.alert_dialog_ok) { dialog, which ->
                mLiveControllerViewModel!!.translateContent(msg)
                dialog.dismiss()
            }
            val diySystemDialog = dialog.create()
            diySystemDialog.setCancelable(true)
            diySystemDialog.show()
        }
        mLiveControllerViewModel!!.roomInfoLiveData.observe(this) { roomInfoModel ->
            dismissDialogLoading()
            if (roomInfoModel?.errCode == 0) {
                roomInfoBean = roomInfoModel
                imViewModel!!.roomManagerLiveData.value = roomInfoModel.data?.is_room_admin
                viewModel?.setStatus("living")
                roomInfoModel.data?.scene_id_str?.let {
                    roomInfoModel.data?.room_id_str?.let { it1 ->
                        imViewModel?.initIM(mContext, "enter_room", it1,
                            it, DataBus.instance().USER_ID, DataBus.instance().USER_NAME, true)
                    }
                }
                anchorAvatar = roomInfoModel?.data?.anchor_pic
                anchorCoverImg = roomInfoModel?.data?.anchor_pic
                sceneId = roomInfoModel?.data?.scene_id_str
                roomId = roomInfoModel?.data?.room_id_str
                streamId = roomInfoModel?.data?.stream_id
                anchorId = roomInfoModel?.data?.anchor_id
                isFollow = roomInfoModel?.data?.is_follow
                roomInfoModel?.data?.anchor_id?.let {
                    (mContext as LiveRoomActivity).updateAnchorId(it)
                }
                roomInfoModel?.data?.stream_id?.let {
                    (mContext as LiveRoomActivity).updateLiveStreamID(it)
                }
                roomInfoModel?.data?.room_id_str?.let {
                    (mContext as LiveRoomActivity).updateLiveRoomID(it)
                }
                roomInfoModel?.data?.stream_id?.let {
                    roomInfoModel?.data?.room_id_str?.let { it1 -> reloadPlayer("", "", it, it1) }
                }
                imViewModel?.roomInfoLiveEvent?.value = roomInfoModel?.data
                imViewModel?.followStateLiveData?.value = roomInfoModel.data?.is_follow
                imViewModel?.streamIdLiveEvent?.value = roomInfoModel.data?.room_id_str
                roomInfoModel.data?.room_id_str?.let {
                    roomInfoModel.data?.scene_id_str?.let { it1 ->
                        mLiveControllerViewModel?.getRoomInfoExtra(
                            it, it1, DataBus.instance().USER_ID, true)
                    }
                }
            } else {
                LaToastUtil.showShort(roomInfoModel?.errMsg)
                notifyNextPager()
            }
        }

        mLiveControllerViewModel!!.roomInfoExtraLiveData.observe(this) { roomInfoModel ->
            if (roomInfoModel != null) {
                dismissDialogLoading()
//                imViewModel?.shareUrlLiveEvent?.value = roomInfoModel.data.share_url
                var anchorInfo = AnchorInfo()
                anchorInfo.avatar = roomInfoBean?.data?.user_avatar.toString()
                anchorInfo.nickname = roomInfoBean?.data?.anchor_name.toString()
                anchorInfo.likeNum = roomInfoModel?.data?.liking_count!!
                mLiveControllerViewModel?.mHostInfo?.value = anchorInfo

//                LiveRoomHelper.starVM2.post(roomInfoModel.data?.liking_count?.toLong())
            }
        }
        viewModel!!.playerError.observe(this) { }
        viewModel!!.playerComplete.observe(this) {}

        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_SHOW_LOGIN_DIALOG)
            .observe(this) {
                showLoginDialog()
            }

        viewModel?.liveEndLiveData?.observe(this) {
            (mContext as LiveRoomActivity)?.finish()
        }

        LiveRoomHelper.openUserCardVM.observe(this) {
            if (anchorId?.isNotEmpty() == true) {
                XPopup.Builder(mContext as FragmentActivity)
                    .enableDrag(false)
                    .asCustom(
                        roomId?.let { it1 ->
                            sceneId?.let { it2 ->
                                UserCardDialog(
                                    mContext as FragmentActivity,
                                    DataBus.instance().USER_ID,
                                    it.toString(),
                                    it1,
                                    it2,
                                    anchorId!!,
                                    roomInfoBean?.data?.is_super_admin!!,
                                    imViewModel?.roomManagerLiveData?.value!!
                                )
                            }
                        }
                    )
                    .show()
            }
        }

        UserCardHelper.managerVM.observe(this) {
            CommonBottomDialog(
                mContext,
                roomId,
                sceneId, anchorId, DataBus.instance().USER_ID,
                DataBus.instance().USER_NAME, it.follow_id, it.nick_name, it.is_forbid_speak, it.is_room_admin, it.is_super_admin,
                imViewModel?.roomManagerLiveData?.value!!
            )
                .showManagerDialog("", true)
        }

        UserCardHelper.reportVM.observe(this) {
            CommonBottomDialog(
                mContext,
                streamId,
                anchorId,
                arrayListOf("${it.userId}")
            )
                .showChatRoomReportDialog()
        }

        UserCardHelper.atUserVM.observe(this) {
            mLiveControllerViewModel!!.mMessageButton.setValue(
                SendEvent(SendEvent.TYPE_AITE, "@${it.userName}".plus(" "))
            )
        }

        UserCardHelper.lightBoardVM.observe(this) {
            if (streamId?.isNotEmpty() == true) {
                XPopup.Builder(mContext)
                    .enableDrag(false)
                    .asCustom(
                        (mContext as LiveRoomActivity)?.let { it1 ->
                            LiveLightBoardDialog(
                                it1,
                                streamId?.toLongOrNull() ?: 0L,
                                it.userId,
                                it.avatar, it.userName, groupId
                            ) { groupName, groupId ->
                                DataBus.instance().curLiveRoomFansClub = groupName
                                imViewModel?.fansLightBoardLiveEvent?.value = groupName
                                isFollow = true
                                this.groupId = groupId
                            }
                        }
                    )
                    .show()
            }
        }

        UserCardHelper.followAnchorVM.observe(this) {
            imViewModel!!.followStateLiveData.value = it
        }

        LiveRoomHelper.liveFloatingWindow.observe(this) {
            viewModel?.setStatus("floatWindow")
            if (!FloatingWindowHelper.canDrawOverlays(mContext, true)) {
                return@observe
            }
            openFloatingWindow()

        }
        imViewModel?.showPanel?.observe(this) {
            if (it) {
                ll_room_bottom_tools?.visibility = View.GONE
            } else {
                var handler1: Handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        super.handleMessage(msg)
                        ll_room_bottom_tools?.visibility = View.VISIBLE
                    }
                }
                handler1.sendEmptyMessageDelayed(0, 100)

            }
        }

        imViewModel?.followStateLiveData?.observe((this), Observer { aBoolean ->
            if (aBoolean == null) return@Observer
        })

        imViewModel?.followAndExitLiveData?.observe(this) {
            exitRequest()
        }

        IMSocketBase.instance().room(roomId).liveEndMsg.observe(liveEndObserver)
        IMSocketBase.instance().room(DataBus.instance().USER_ID).roomBlackMsg.observe{
            LaToastUtil.showShort(mContext.getString(R.string.stream_black_tip))
            (mContext as LiveRoomActivity)?.finish()
        }
//        IMSocketImpl.getInstance().chatRoom(streamID).liveAudiecneNotifyMsgLiveCallback.observe(
//            liveNotifyCallback
//        )

        imViewModel?.minLiveEvent?.observe(this) {
            LiveRoomHelper.liveFloatingWindow.post(0L)
        }

        mLiveControllerViewModel!!.anchorSettingLiveEvent.observeOnce(this) {
            Router.toAnchorManagerDialog(roomId, roomInfoBean?.data?.scene_id_str, 2)
        }
        imViewModel?.zegoUserRepeatLiveData?.observe(this){
            (mContext as LiveRoomActivity).finish()
        }
    }

    private fun reloadPlayer(
        baseUrl: String,
        authParams: String,
        streamId: String,
        roomId: String
    ) {
        ULog.e("clll", "reloadPlayer1------")
        if (TextUtils.isEmpty(anchorId)) {
            return
        }
        ULog.e("clll", "reloadPlayer2------")
        viewModel?.preparePlayer(baseUrl, authParams, streamId, roomId)
        if (recoverIs == true) {
            val playerView: BasePlayerView? = ZegoLiveplay.instance.getPlayerView()
            if (playerView != null) {
                setPlayerView(playerView, liveStreamcontainer)
            }
        } else {
            var basePlayerView = BasePlayerView(mContext)
            viewModel!!.onCreate(basePlayerView, mContext, isPageScrolled)
//            ZegoLiveplay.instance.setupVideo?.setupRemoteVideo(basePlayerView)
            ZegoLiveplay.instance.setISetupVideo(object : ZegoLiveplay.ISetupVideo {
                override fun setupRemoteVideo(view: BasePlayerView?) {
                    setPlayerView(view, liveStreamcontainer)
                }

            })
        }

        initGiftOnline()
    }

    private fun setPlayerView(view: BasePlayerView?, mFlContainer: FrameLayout?) {
        if (null == view) {
            return
        }
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
        mFlContainer?.removeAllViews()
        mFlContainer?.addView(view)
    }

    private fun reloadData() {
//        imViewModel?.joinGroup(true)
//        showDialogLoading()
        roomId?.let { sceneId?.let { it1 ->
            mLiveControllerViewModel!!.getRoomInfo(it,
                it1, DataBus.instance().USER_ID, true)
        } }
//        mLiveControllerViewModel!!.setFromSource(fromSource)
        completeImCallBack()
    }

    private fun initGiftOnline() {
        val giftImg = mRootView.findViewById<ImageView>(R.id.liveRoomBottomGiftImg)
        val starImg = mRootView.findViewById<ImageView>(R.id.liveRoomBottomZanImg)
        val starText = mRootView.findViewById<TextView>(R.id.liveRoomBottomZanTxt)
        starText?.visibility = View.GONE
        val bcLayout = mRootView.findViewById<BroadcastGiftLayout>(R.id.bcLayout)
        gIftUILogic = (mContext as LiveRoomActivity)?.let {
            LiveRoomGIftUILogic(
                it, roomId,sceneId,  anchorId, giftImg, starImg, null, giftSide, giftPlayView,
                liveBottomStarView, null, false)
        }
        gIftUILogic?.init(this)

        val onlineLayout = mRootView.findViewById<LinearLayout>(R.id.llOnlineLayout)
//        onlineLogic = (mContext as LiveRoomActivity)?.let {
//            LiveRoomOnlineLogic(
//                it,
//                streamId,
//                streamId,
//                anchorId,
//                null,
//                onlineLayout
//            )
//        }
//        onlineLogic?.init(this)
    }

    override fun onDestroyView() {
        LiveManager.instance().setLiveStatusNull()
        exitRequest()
        viewModel?.onDestroy()
        if (viewModel?.liveStatus != "floatWindow") {
            imViewModel!!.onDestroy()
        }
        liveStreamcontainer?.removeAllViews()
        IMSocketBase.instance().room(roomId).liveEndMsg.removeObserver(liveEndObserver)

//        IMSocketImpl.getInstance()
//            .chatRoom(streamID).liveAudiecneNotifyMsgLiveCallback.removeObserver(liveNotifyCallback)

        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        lc_livefixintercation?.onActivityResult(requestCode, resultCode, data)
        ll_room_bottom_tools.onActivityResult(requestCode, resultCode, data)
        mLiveControllerViewModel!!.onActivityResultEvent(requestCode, resultCode, data)
    }

    fun onBackPressed() {
        if (room_message_layout!!.onBackPress()) {
            return
        }
        showOutDialog()
    }

    private fun showOutDialog() {
        exitRequest()
        (mContext as LiveRoomActivity)?.finish()
//            DialogUtils.createAudienceExitDialog(
//                mContext, anchorAvatar, isFollow, StreamPreviewLayout.TYPE_LIVE,
//                object : AudienceExitDialog.AudienceExitDialogListener {
//                    override fun onAudienceExitAndFollowListener() {//继续观看
//                    }
//
//                    override fun onAudienceExitListener() {//
//                        exitRequest()
//                        (mContext as LiveRoomActivity)?.finish()
//                    }
//
//                    override fun onFollowListener() {
//                    }
//
//                }).show()
    }

    private fun showLoginDialog() {
        val builder = DiySystemDialog.Builder(mContext)
        builder.setMessage(R.string.stream_live_login_title)
        builder.setNegativeButton(
            R.string.stream_live_contine_watch
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.setPositiveButton(
            StringUtils.getString(R.string.stream_live_login)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun completeImCallBack() {
        val loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L

        val chatLayout = mRootView.findViewById<LivePublicChatAreaListLayout>(R.id.danmaku_list)
//        IMSocketImpl.getInstance()
//            .chatRoom(streamID).joinLiveRoomCallback?.observe(this) { entity ->
//                //插入一条加入直播间消息并播放座驾
////                entity?.carResources = arrayListOf<String>().apply {
////                    add("https://baidu.com/zoujia_123.PAG.zip")
////                }
//                if (DataBus.instance().floaT_IS_RUN && entity?.from?.userId == DataBus.instance().uid) {
//
//                } else {
//                    gIftUILogic?.playCar(entity)
//                }
//
//                chatLayout?.addLiveJoinConvention(entity)
//                entity?.from?.let {
//                    onlineLogic?.refresh(
//                        true,
//                        it.userId.toLong(),
//                        it.nickname,
//                        it.avatar,
//                        entity.score,
//                        entity.total
//                    )
//                }
//            }
//
//        IMSocketImpl.getInstance()
//            .chatRoom(streamID).liveLeaveRoomCallback?.observe(this) { entity ->
//                entity?.from?.let {
//                    onlineLogic?.refresh(
//                        false,
//                        it.userId.toLong(),
//                        it.nickname,
//                        it.avatar,
//                        0L,
//                        entity.total
//                    )
//                }
//            }
//
//        IMSocketImpl.getInstance()
//            .chatRoom(streamID).liveKickPeopleMsgLiveCallback?.observe(this) { entity ->
//                if (entity.operateUser?.userId == loginUID) {
//                    if (entity.operateType == 2) {
//                        LaToastUtil.showShort(R.string.chat_balck_live_tip)
//                    } else if (entity.operateType == 1) {
//                        LaToastUtil.showShort(R.string.chat_remove_live_tip)
//                    }
//                    viewModel?.setStatus("finish")
//                    (mContext as LiveRoomActivity)?.finish()
//                }
//            }
    }

    private fun openFloatingWindow() {
        val intent = Intent()
        intent.setClassName(
            mContext as LiveRoomActivity,
            FloatWindowsService::class.java.name
        )
        intent.putExtra(ZegoLiveplay.STREAM_ID, streamId)
        intent.putExtra(ZegoLiveplay.ANCHORID, anchorId)
//        intent.putExtra(ZegoLiveplay.BASE_URL, pullUrl)
//        intent.putExtra(ZegoLiveplay.AUTH_PARAMS, authParams)
//        intent.putExtra(ZegoLiveplay.FROAM_SOURCE, fromSource)
        (mContext as LiveRoomActivity)?.startService(intent)
        (mContext as LiveRoomActivity)?.finish()
    }

    fun getLiveStreamInfo(): LiveStreamInfo? {
        return (mContext as LiveRoomActivity).liveStreamInfo
    }

    private fun showNotifyMsgDialog(it: LiveAudienceNotificationMsg) {
        when (it.type) {
            1 -> {
                if (this != null && !(mContext as LiveRoomActivity).isDestroyed && !(mContext as LiveRoomActivity).isFinishing) {
                    DialogUtils.createAnchorBlockedTipDialog(
                        mContext,
                        2,
                        object : AnchorBlockedTipDialog.AnchorBlockedTipDialogListener {
                            override fun onConfirm() {
                                Router.toLiveEndActivity(
                                    (mContext as LiveRoomActivity),
                                    roomInfoBean?.data?.room_id_str,
                                    roomInfoBean?.data?.user_avatar,
                                    roomInfoBean?.data?.anchor_name
                                )
                            }

                        }).show()
                }
            }
        }
    }

    private fun exitRequest() {
        roomInfoBean?.data?.room_id_str?.let {
            roomInfoBean?.data?.scene_id_str?.let { it1 ->
                imViewModel?.imOutRoom("leave_room", it, it1, DataBus.instance().USER_ID,
                    DataBus.instance().USER_NAME, true)
            }
        }
    }

    private fun notifyNextPager() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(300)
        }
    }
}