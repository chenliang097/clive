package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.StringUtils
import com.lxj.xpopup.XPopup
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.base.utils.CameraAndMicPermissonUtlils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.base.view.activity.BaseLiveStreamActivity
import com.rongtuoyouxuan.chatlive.base.view.activity.LiveRoomGIftUILogic
import com.rongtuoyouxuan.chatlive.base.view.activity.LiveRoomOnlineLogic
import com.rongtuoyouxuan.chatlive.base.view.dialog.AnchorBlockedTipDialog
import com.rongtuoyouxuan.chatlive.base.view.layout.bradcastlayout.BroadcastGiftLayout
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.AnchorInfo
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.EnterRoomBean
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.FloatWindowsService
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.crtrouter.Router
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.beauty.BeautifyBottomFragment
import com.rongtuoyouxuan.chatlive.stream.view.beauty.BeautifyBottomFragmentViewModel
import com.rongtuoyouxuan.chatlive.stream.view.beauty.BottomFragmentViewModel
import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType
import com.rongtuoyouxuan.chatlive.stream.view.dialog.HostExitDialog
import com.rongtuoyouxuan.chatlive.stream.view.layout.*
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout.StartLiveListener
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel
import com.rongtuoyouxuan.chatlive.rtstream.streaming.BaseStreamView
import com.rongtuoyouxuan.chatlive.crtutil.util.ScreenCapture
import com.rongtuoyouxuan.chatlive.libsocket.base.IMSocketBase
import com.rongtuoyouxuan.chatlive.qfcommon.dialog.CommonBottomDialog
import com.rongtuoyouxuan.chatlive.qfcommon.dialog.UserCardDialog
import com.rongtuoyouxuan.chatlive.qfcommon.share.RxUmengSocial
import com.rongtuoyouxuan.chatlive.qfcommon.util.DiySystemDialogUtil
import com.rongtuoyouxuan.chatlive.qfcommon.util.UserCardHelper
import com.rongtuoyouxuan.chatlive.rtstream.qfzego.ZegoStreamView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.rongtuoyouxuan.chatlive.crtmatisse.dialog.DiySystemDialog
import com.rongtuoyouxuan.chatlive.crtutil.sp.SPConstants
import kotlinx.android.synthetic.main.qf_stream_activity_stream.*
import java.util.*

@Route(path = RouterConstant.PATH_ACTIVITY_STREAM)
class StreamActivity : BaseLiveStreamActivity() {
    private val TAG = "StreamActivity"
    private var streamContainer: FrameLayout? = null
    private var streamPreview: StreamPreviewLayout? = null
    private var streamControllerView: View? = null
    private var mStreamView: BaseStreamView? = null
    private var ivClose: ImageView? = null
    private var mMeasuredView: View? = null
    private var screenCapture: ScreenCapture? = null
    private var messageLayout: com.rongtuoyouxuan.chatlive.base.view.layout.RoomSendMessageLayout? = null
    private var networkErrorView: View? = null
    private var mInteractionLayout: InteractionLayout? = null
    private var mfaceLayout: RelativeLayout? = null
    private var gameAndStreamView: StreamAndOtherView? = null

    private val mBackPressListeners: MutableList<com.rongtuoyouxuan.chatlive.stream.view.layout.BackPressListener?>? = ArrayList() //存放onBack回调
    private val handler = Handler(Looper.getMainLooper())
    private var rxPermissions: RxPermissions? = null
    private val handlerSocket = Handler(Looper.getMainLooper())

    private var isDestroy = false

    private var mStreamViewModel: StreamViewModel? = null
    private var mControllerViewModel: com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel? = null
    private var mIMViewModel: IMLiveViewModel? = null
    private var roomInfoBean:EnterRoomBean? = null
    private val bottomFragmentViewModel = BottomFragmentViewModel
    private val beautifyFragmentViewModel = BeautifyBottomFragmentViewModel
    private var retryJoinGroupCount = 0

    private var liveLockObserver: Observer<LiveLockMsg> = Observer {
        showForbidDialog(it.endType)
    }

    private var suspenedObserver: Observer<SuspendedAccountMsg> = Observer {
        showBlockAnchorDialog(it.suspendedType)
    }

    init {
        anchorId = DataBus.instance().uid
        anchorId?.let { updateAnchorId(it) }
    }

    companion object {
        const val DEFAULT_SETTINGS_REQ_CODE = 16061
        const val DEFAULT_SCREEN_CAPTURE_REQ_CODE = 16070
    }

    private var gIftUILogic: LiveRoomGIftUILogic? = null
    private var onlineLogic: LiveRoomOnlineLogic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DataBus.instance().floaT_IS_RUN) {
            removeFloatingWindow()
        }
        setContentView(R.layout.qf_stream_activity_stream)
        initViewModel()
        initView()
        initListener()
        bindStreamData()
        showPermissonDialog()
        initData()
    }

    private fun initViewModel() {
        mStreamViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(this, StreamViewModel::class.java)
        mControllerViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(this, com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel::class.java)
        mIMViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(this, IMLiveViewModel::class.java)

    }

    private fun initView() {
        streamContainer = findViewById(R.id.stream_container)
        streamPreview = findViewById(R.id.ll_preview)
        streamControllerView = findViewById(R.id.ll_controllerview)
        mInteractionLayout = findViewById(R.id.rl_interaction)
        mMeasuredView = findViewById(R.id.view_measured)
        mBackPressListeners!!.add(ll_room_bottom_tools)

        //推流view
        createDefaultStreamView()
        messageLayout = findViewById(R.id.room_message_layout)
        networkErrorView = findViewById(R.id.fl_network_error)

    }

    private fun initListener() {
        mMeasuredView?.viewTreeObserver?.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    mMeasuredView?.viewTreeObserver?.removeOnPreDrawListener(this)
                    mMeasuredView?.width?.let {
                        mInteractionLayout?.setParams(it, mMeasuredView?.height!!)
                        streamPreview?.setParams(it, mMeasuredView?.height!!)
                        val layoutParams = stream_container!!.layoutParams
                        layoutParams.width = it
                        layoutParams.height = mMeasuredView?.height!!
                        stream_container!!.layoutParams = layoutParams
                    }
                    return true
                }
            })
        ivClose?.setOnClickListener(View.OnClickListener {
            if (streamPreview?.visibility == View.VISIBLE) {
                onBackPressed()
                return@OnClickListener
            }
            mControllerViewModel!!.mOutDialog.call()
        })
        streamPreview?.setStartLiveListener(object : StartLiveListener {
            override fun onVoiceRoomStartLive() {
                onBackPressed()
            }
        })
        mfaceLayout?.setOnClickListener {
            if (mControllerViewModel?.beautySettingVisibility?.value == true) {
                mfaceLayout?.visibility = View.GONE
                mControllerViewModel?.beautySettingVisibility?.value = false
            } else {
                mfaceLayout?.visibility = View.VISIBLE
                mControllerViewModel?.beautySettingVisibility?.value = true
            }
        }

        mInteractionLayout?.findViewById<RoundedImageView>(R.id.iv_room_master_headimg)
            ?.setOnClickListener {
                LiveRoomHelper.openUserCardVM.post(anchorId)
            }

    }


    private fun bindStreamData() {
        if (mStreamView != null) {
            mStreamViewModel!!.addUploadCrashLogs("oStreamBindData")
            mStreamViewModel!!.resetStreamApi(mStreamView)
        }
    }

    private fun initData() {
        streamPreview?.setUserInfo(DataBus.instance().USER_ID, DataBus.instance().USER_NAME)
        initObsever()
    }

    private fun initObsever() {
        mControllerViewModel!!.mControllerVisibility.observeOnce(this) { aBoolean ->
            streamControllerView!!.visibility = if (aBoolean!!) View.VISIBLE else View.GONE
        }
        mStreamViewModel?.startStreamEvent?.observeOnce(this) {
//            mIMViewModel?.joinGroup(false)

            streamAdslayout.visibility = View.VISIBLE
        }
        mControllerViewModel!!.mOutDialog.observeOnce(this) { showOutDialog() }

        mControllerViewModel!!.anchorSettingLiveEvent.observeOnce(this) {
            Router.toAnchorManagerDialog(roomID, roomInfoBean?.data?.scene_id_str, 1)
        }

        mIMViewModel!!.showTransteDialog.observeOnce(this) { msg ->
            val dialog = DiySystemDialog.Builder(this@StreamActivity)
            dialog.setMessage(R.string.translate)
            dialog.setIsCancle(true)
            dialog.setOnKeyCancle(true)
            dialog.setPositiveButton(R.string.alert_dialog_ok) { dialog, which ->
                mControllerViewModel!!.translateContent(msg)
                dialog.dismiss()
            }
            val diySystemDialog = dialog.create()
            diySystemDialog.setCancelable(true)
            diySystemDialog.show()
        }
        mControllerViewModel!!.mShareFbAndLive.observe(this) {
        }
        mStreamViewModel!!.showForbidRoomEvent.observe(this) { showForbidRoomDialog() }
        mStreamViewModel!!.closeActivityLiveData.observe(this) {
            finish()
        }

        mControllerViewModel?.beautySettingVisibility?.observe(this) {
            val rightMenuItemSelected =
                bottomFragmentViewModel?.rightMenuSelected?.value
                    ?: MenuItemType.RightMenuItemType.Unselected

            if (rightMenuItemSelected == MenuItemType.RightMenuItemType.Beautify) {
//                popUpBeautifyFragment.dismiss()
//                bottomFragmentViewModel?.setRightMenuSelectedOption(MenuItemType.RightMenuItemType.Unselected)
            } else {
                bottomFragmentViewModel.setRightMenuSelectedOption(MenuItemType.RightMenuItemType.Beautify)
                showBeautifyPopupDialog()
            }
        }

        mStreamViewModel?.startStreamModel?.observe(this) { startStreamBean ->
            ll_room_bottom_tools.visibility = View.VISIBLE
            mIMViewModel?.initIM(this, "enter_room", startStreamBean.data.room_id_str,
                startStreamBean.data.scene_id_str, DataBus.instance().USER_ID, DataBus.instance().USER_NAME, true)
            var enterRoomBean = EnterRoomBean.DataBean()
            updateLiveRoomID(startStreamBean.data.room_id_str)
            updateAnchorId(startStreamBean.data.anchor_id.toString())
            updateLiveStreamID(startStreamBean.data.stream_id)
            enterRoomBean.room_id = startStreamBean.data.room_id
            enterRoomBean.scene_id = startStreamBean.data.scene_id
            enterRoomBean.anchor_id = startStreamBean.data.anchor_id.toString()
            enterRoomBean.is_room_admin = false
            enterRoomBean.is_super_admin = false
            enterRoomBean.is_anchor = true
            enterRoomBean.user_avatar = ""
            enterRoomBean.user_id = DataBus.instance().USER_ID
            enterRoomBean.user_name = DataBus.instance().USER_NAME
            mIMViewModel?.roomInfoLiveEvent?.value = enterRoomBean
            mIMViewModel?.streamIdLiveEvent?.value = startStreamBean.data.room_id_str
            mIMViewModel?.getRoomInfo(startStreamBean.data.room_id_str, startStreamBean.data.scene_id_str, DataBus.instance().USER_ID, true)
            imObserver(startStreamBean.data.scene_id_str)
            ULog.d("clll", "streamID:$streamID----anchorId:$anchorId")
        }

        mIMViewModel?.showPanel?.observe(this) {
            if (it) {
                streamAdslayout?.visibility = View.GONE
                ll_room_bottom_tools?.visibility = View.GONE
            } else {
                ll_room_bottom_tools?.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    streamAdslayout.visibility = View.VISIBLE
                }, 100)

            }
        }

        mIMViewModel?.roomInfoLiveData?.observe(this){
            roomInfoBean = it
            it?.data?.room_id_str?.let { it1 -> it?.data?.scene_id_str?.let { it2 ->
                mIMViewModel?.getRoomInfoExtra(it1,
                    it2, DataBus.instance().USER_ID, true)
            } }
        }

        mIMViewModel?.roomInfoExtraLiveData?.observe(this){
            var anchorInfo = AnchorInfo()
            anchorInfo.avatar = roomInfoBean?.data?.user_avatar.toString()
            anchorInfo.nickname = roomInfoBean?.data?.anchor_name.toString()
            anchorInfo.likeNum = it?.data?.liking_count!!
            mControllerViewModel?.mHostInfo?.value = anchorInfo
        }
        IMSocketBase.instance().room(DataBus.instance().USER_ID).anchorLiveEndMsg.observe{
            intentStreamEnd(2)
        }
//        IMSocketImpl.getInstance().chatRoom(DataBus.instance().uid).liveLockMsgLiveCallback.observe(
//            liveLockObserver
//        )
//        IMSocketImpl.getInstance().chatRoom(DataBus.instance().uid).suspenedMsgLiveCallback.observe(
//            suspenedObserver
//        )

    }

    fun imObserver(sceneId:String) {
        val starText = findViewById<TextView>(R.id.liveRoomBottomZanTxt)
        val bcLayout = findViewById<BroadcastGiftLayout>(R.id.bcLayout)
        gIftUILogic = LiveRoomGIftUILogic(
            this, roomID, sceneId, anchorId, null, null, starText, giftSide, giftPlayView,
            liveBottomStarView, null, true
        )
        gIftUILogic?.init()

        val onlineLayout = findViewById<LinearLayout>(R.id.llOnlineLayout)
        var interactionLayout: LinearLayout? = null
//        onlineLogic = LiveRoomOnlineLogic(this, streamID, anchorId, interactionLayout, onlineLayout)
//        onlineLogic?.init()

        completeImCallBack()

        UserCardHelper.reportVM.observe(this) {
            CommonBottomDialog(
                this,
                streamID,
                anchorId,
                arrayListOf("${it.userId}")
            )
                .showChatRoomReportDialog()
        }

        UserCardHelper.atUserVM.observe(this) {
            mControllerViewModel?.mMessageButton?.value =
                com.rongtuoyouxuan.chatlive.base.view.model.SendEvent(
                    com.rongtuoyouxuan.chatlive.base.view.model.SendEvent.TYPE_AITE,
                    "@${it.userName}".plus(" ")
                )
        }

        LiveRoomHelper.openUserCardVM.observe(this) {
            if (anchorId?.isNotEmpty() == true) {
                XPopup.Builder(this)
                    .enableDrag(false)
                    .asCustom(
                        roomID?.let { it1 ->
                            UserCardDialog(
                                this@StreamActivity,
                                DataBus.instance().USER_ID,
                                it.toString(),
                                it1,
                                sceneId,
                                anchorId!!,false, false
                            )
                        }
                    )
                    .show()
            }
        }

        UserCardHelper.managerVM.observe(this) {
            CommonBottomDialog(
                this,
                roomID,
                sceneId,
                DataBus.instance().USER_ID,
                DataBus.instance().USER_ID,
                DataBus.instance().USER_NAME,
                it.follow_id,
                it.nick_name,
                it.is_forbid_speak,
                it.is_room_admin,
                it.is_super_admin
            )
                .showManagerDialog("", true)
        }

        IMSocketBase.instance().getSocketConnectStatus().observe(this){
            ULog.e("clll", "socketConnect---111")
            if (IMSocketBase.ERROR_SOKET == it.code && !IMSocketBase.instance().isConnected && !isDestroy) {
                ULog.e("clll", "socketConnect222---${it.msg}---${it.code}---${it.step}---${it.success}")
                if (retryJoinGroupCount < IMLiveViewModel.MAX_RETRY) {
                    retryJoinGroupCount++
                    handlerSocket.postDelayed(
                        { roomInfoBean?.data?.room_id_str?.let { it1 ->
                            roomInfoBean?.data?.scene_id_str?.let { it2 ->
                                mIMViewModel?.initIM(this, "re_enter_room",
                                    it1,
                                    it2, DataBus.instance().USER_ID, DataBus.instance().USER_NAME, true)
                            }
                        } },
                        (retryJoinGroupCount * 2 * 1000).toLong()
                    )
                }

            }
        }

    }

    private fun showForbidRoomDialog() {
        if (this.isFinishing) {
            return
        }
        if (mStreamView != null) {
            mStreamView!!.stopStreaming()
        }
        val dialog = DiySystemDialog.Builder(this@StreamActivity)
        dialog.setTitle(R.string.stream_dialog_forbid_room_title)
//        dialog.setMessage(R.string.stream_dialog_forbid_room_content)
        dialog.setNegativeButton(R.string.alert_dialog_ok) { dialog, which ->
            dialog.dismiss()
            finish()
        }
        val diySystemDialog = dialog.create()
        diySystemDialog.show()
    }

    /**
     * 违规提醒
     * @param notice
     */
    private fun showWarnRoomNotice(notice: String) {
        if (this.isFinishing) {
            return
        }
        val dialog = DiySystemDialog.Builder(this@StreamActivity)
        dialog.setTitle(R.string.stream_dialog_warn_room_title)
        dialog.setMessage(notice)
        dialog.setNegativeButton(R.string.alert_dialog_ok) { dialog, which -> dialog.dismiss() }
        val diySystemDialog = dialog.create()
        diySystemDialog.show()
    }

    private fun showOutDialog() {
        com.rongtuoyouxuan.chatlive.base.DialogUtils.createHostExitDialog(
            this,
            streamID,
            anchorId,
            object : HostExitDialog.HostExitDialogListener {
                override fun onHostExitDialog() {
                    intentStreamEnd(1)
                }

            }).show()
    }

    private fun intentStreamEnd(isFrom:Int) {
        ULog.e("clll", "intentStreamEnd--------")
        Router.toStreamEndActivity(
            this@StreamActivity,
            liveStreamInfo?.roomId,
            roomInfoBean?.data?.scene_id_str,
            roomInfoBean?.data?.user_avatar,
            isFrom
        )
        finish()
    }

    private fun createDefaultStreamView() {
        mStreamView = ZegoStreamView(
            this,
            mMeasuredView
        )
        gameAndStreamView = StreamAndOtherView(this)
        gameAndStreamView?.setZegoView(mStreamView as ZegoStreamView)
        streamContainer?.addView(gameAndStreamView)

    }

    private fun initCamera() {
        mStreamViewModel!!.onCreate()
//        streamPreview?.initLoacation()
    }

    override fun onRestart() {
        super.onRestart()
        mStreamViewModel!!.onRestart()
    }

    override fun onResume() {
        super.onResume()
        if (rxPermissions?.let { CameraAndMicPermissonUtlils.instance.isHasPermisson(it) } == true) {
            mStreamViewModel!!.addUploadCrashLogs("onResume have permission")
            mStreamViewModel!!.onResume()
        }
        startWakeLock()
        RxUmengSocial.get().onResume(this);
    }

    private fun doOnPause() {
        mStreamViewModel!!.onPause()
        if (screenCapture != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                screenCapture!!.onPause()
            }
        }
        stopWakeLock()
    }

    override fun onStop() {
        super.onStop()
        doOnPause()
    }

    override fun onDestroy() {
        destroy()
        RxUmengSocial.get().onDestroy(this);
        super.onDestroy()
    }

    /**
     * onDestroy 方法中释放资源不及时，在主动finish时释放资源
     */
    private fun destroy() {
        if (isDestroy) return
        SPUtils.getInstance().put(SPConstants.BooleanConstants.IS_SETTING_VISIBLE, false)
        retryJoinGroupCount
        hideSoftInputFromWindow()
        handler.removeCallbacksAndMessages(null)
        handlerSocket.removeCallbacksAndMessages(null)
        streamContainer!!.removeAllViews()
        if (mStreamViewModel != null) {
            mStreamViewModel!!.onDestroy()
        }
        mBackPressListeners?.clear()
        if (screenCapture != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                screenCapture!!.onDestroy()
            }
        }
        mIMViewModel!!.onDestroy()
        isDestroy = true
//        IMSocketImpl.getInstance()
//            .chatRoom(DataBus.instance().uid).liveLockMsgLiveCallback.removeObserver(
//            liveLockObserver
//        )
//        IMSocketImpl.getInstance()
//            .chatRoom(DataBus.instance().uid).suspenedMsgLiveCallback.removeObserver(
//            suspenedObserver
//        )
    }

    override fun onBackPressed() {
        if (isFinishing) {
            return
        }
        if (messageLayout!!.onBackPress()) {
            return
        }
        for (mBackPressListener in mBackPressListeners!!) {
            if (mBackPressListener!!.onBackPress()) {
                return
            }
        }
        super.onBackPressed()
    }

    private fun showPermissonDialog() {
        rxPermissions = RxPermissions(this)
        if (CameraAndMicPermissonUtlils.instance.isHasPermisson(rxPermissions!!)) {
            mStreamViewModel!!.addUploadCrashLogs("has permissions")
            initCamera()
            return
        } else {
            showPermissonDialog1()
        }
    }

    private fun showPermissonDialog1() {
        startWithPermission()
    }

    private fun startWithPermission() {
        rxPermissions?.let {
            CameraAndMicPermissonUtlils.instance.requestPermisson(
                it,
                object : CameraAndMicPermissonUtlils.PermissonListener {
                    override fun onISHaveListener() {
                        initCamera()
                        if (mStreamView != null) {
                            mStreamView!!.onResume()
                        }
                    }

                    override fun onErrorListener() {
                        requestSystemPermissonSetting()
                    }

                })
        }
    }

    //相机麦克风权限跳转系统权限设置
    fun requestSystemPermissonSetting() {
        val builder = DiySystemDialog.Builder(this@StreamActivity)
        builder.setMessage(getString(R.string.permission_camera_record))
        builder.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
        builder.setPositiveButton(getString(R.string.to_allow)) { dialog, which ->
            dialog.dismiss()
            //跳转系统设置
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + this@StreamActivity.packageName)
            startActivityForResult(intent, DEFAULT_SETTINGS_REQ_CODE)
        }
        builder.create().show()
    }

    private fun hideSoftInputFromWindow() {
        //隐藏软键盘
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val decorView = window.peekDecorView()
        if (decorView != null) {
            imm.hideSoftInputFromWindow(decorView.windowToken, 0)
        }
    }
    private fun completeImCallBack() {
        val chatLayout = findViewById<com.rongtuoyouxuan.chatlive.stream.view.layout.LivePublicChatAreaListLayout>(R.id.danmaku_list)
//        IMSocketImpl.getInstance()
//            .chatRoom(streamID).joinLiveRoomCallback?.observe(this) { entity ->
//                //插入一条加入直播间消息并播放座驾
//                chatLayout?.addLiveJoinConvention(entity)
////                gIftUILogic?.playCar(entity)
////                entity?.from?.let {
////                    onlineLogic?.refresh(
////                        true,
////                        it.userId.toLong(),
////                        it.nickname,
////                        it.avatar,
////                        entity.score,
////                        entity.total
////                    )
////                }
//            }

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
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        RxUmengSocial.get().onActivityResult(this, requestCode, resultCode, data);
        ll_room_bottom_tools.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DEFAULT_SETTINGS_REQ_CODE -> {
                mStreamViewModel!!.addUploadCrashLogs("onActivityResult startWithPermission")
                showPermissonDialog()
            }
            DEFAULT_SCREEN_CAPTURE_REQ_CODE -> if (screenCapture != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    screenCapture!!.onActivityResult(requestCode, resultCode, data)
                }
            }
            else -> mControllerViewModel!!.onActivityResultEvent(requestCode, resultCode, data)
        }
    }

    private fun startWakeLock() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun stopWakeLock() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun removeFloatingWindow() {
        val intent = Intent()
        intent.setClassName(
            this@StreamActivity,
            FloatWindowsService::class.java.name
        )
        intent.type = "remove"
        startService(intent)
    }

    fun showForbidDialog(type: Int) {
        when (type) {
            1 -> {
                if (this != null && !isDestroyed && !isFinishing) {
                    DiySystemDialogUtil.showOneTapConfirmDialog(
                        this, StringUtils.getString(R.string.stream_stream_exception),
                        StringUtils.getString(R.string.stream_stream_exception_btn)
                    ) {
                        finish()
                    }
                }
            }
            2 -> {
                if (this != null && !isDestroyed && !isFinishing) {
                    com.rongtuoyouxuan.chatlive.base.DialogUtils.createAnchorBlockedTipDialog(
                        this,
                        1,
                        object : AnchorBlockedTipDialog.AnchorBlockedTipDialogListener {
                            override fun onConfirm() {
                                intentStreamEnd(10)
                                finish()
                            }

                        }).show()
                }
            }
        }

    }

    private fun showBlockAnchorDialog(type: Int) {
        when (type) {
            2 -> {
                if (this != null && !isDestroyed && !isFinishing) {
                    com.rongtuoyouxuan.chatlive.base.DialogUtils.createAnchorBlockedTipDialog(
                        this,
                        1,
                        object : AnchorBlockedTipDialog.AnchorBlockedTipDialogListener {
                            override fun onConfirm() {
                                intentStreamEnd(100)
                                finish()
                            }

                        }).show()
                }
            }
        }

    }

    var handler1 = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: android.os.Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                Router.toStreamActivity()
            } else if (msg.what == 2) {
                streamPreview?.startLiving()
            }

        }
    }

    // 底部弹窗fragment
    private val popUpBeautifyFragment = BeautifyBottomFragment().also {
        it.setPopUpWindowClickListener(object : OnClickListener {
            override fun onClick() {
                // 设置seekBar
                beautifyFragmentViewModel.seekBarVisible.value = false
            }

        })
    }

    /**
     * 显示美颜弹窗
     */
    private fun showBeautifyPopupDialog() {
        if (!popUpBeautifyFragment.isAdded) {
            supportFragmentManager.let { popUpBeautifyFragment.show(it, "beautify") }
        }
    }

    // 点击底部弹窗之外的区域监听器
    private val outOfPopUpWindowListener: OnClickListener = object : OnClickListener {
        override fun onClick() {
            // 设置seekBar
            beautifyFragmentViewModel.seekBarVisible.value = false


        }
    }

}