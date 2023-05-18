package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.rongtuoyouxuan.chatlive.arch.LiveEvent
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo
import com.rongtuoyouxuan.chatlive.biz2.im.ChatIMBiz
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg.BannedMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTTxtMsgRequest
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.BannedRequest
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.EnterRoomMsgRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AdsBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomInfoExtraBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.ShareLiveRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomUserInfo
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.biz2.stream.UserCardBiz.getLiveUserCardInfo
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libsocket.WebSocketManager
import com.rongtuoyouxuan.libsocket.base.ChatSendCallback
import com.rongtuoyouxuan.libsocket.base.EventCallback
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IMLiveViewModel(liveStreamInfo: LiveStreamInfo) : LiveBaseViewModel(liveStreamInfo) {
    @JvmField
    var showRechargeDialog = LiveEvent<Void>()
    @JvmField
    var joinGroupSuccess = LiveEvent<Void>()
    @JvmField
    var joinGroupFail = LiveEvent<String>()
    var showTransteDialog = LiveEvent<String>()
    @JvmField
    var systemMsgLiveEvent = LiveEvent<String>()
    var followStateLiveData = MutableLiveData<Boolean>() //主播关注状态
    var followAndExitLiveData = MutableLiveData<Boolean>() //主播并且退出
    var mUserInfo = MutableLiveData<RoomUserInfo>() //观众当前房间信息
    var clickFollow = LiveEvent<Void>()
    var roomInfoLiveData = MutableLiveData<EnterRoomBean?>() //房间信息
    var roomInfoExtraLiveData = MutableLiveData<RoomInfoExtraBean?>() //房间额外信息
    var roomManagerLiveData = MutableLiveData<Boolean>() //房管
    var sendTxtLiveData = MutableLiveData<BaseModel?>()


    @JvmField
    var isShowChatView = LiveEvent<Boolean>()
    var showGiftDialog = LiveEvent<Int>() //弹出购买礼物dialog
    var bannedLiveData = LiveEvent<Boolean>() //禁言
    @JvmField
    var showPanel = LiveEvent<Boolean>()
    private var retryJoinGroupCount = 0
    private val handler = Handler(Looper.getMainLooper())
    @JvmField
    var streamIdLiveEvent = LiveEvent<String>()

    @JvmField
    var roomInfoLiveEvent = LiveEvent<EnterRoomBean.DataBean>()

    @JvmField
    var shareUrlLiveEvent = LiveEvent<String>()
    var fansLightBoardLiveEvent = LiveEvent<String>()
    @JvmField
    var selfSendTxtLiveEvent = LiveEvent<String>()
    @JvmField
    var hideInputViewLiveEvent = LiveEvent<Boolean>()
    @JvmField
    var showIGifPannelLiveEvent = LiveEvent<Boolean>()
    var adsLiveEvent = LiveEvent<List<AdsBean>>()
    @JvmField
    var clickGifPannelLiveEvent = LiveEvent<Boolean>()

    var minLiveEvent = LiveEvent<Boolean>()
    var giftCallClickEvent = LiveEvent<Boolean>()//礼物按钮点击
    var gameSpecialSettingLiveEvent = LiveEvent<Boolean>()//特效设置
    private var joinChatRoomCode = ""//加入房间错误码



    init {
//        followStateLiveData.value = false
        mUserInfo.value = RoomUserInfo()
        bannedLiveData.value = false
    }

    companion object {
        private const val MAX_RETRY = 10
    }

    fun isHostId(host: Int): Boolean {
        return streamId == host.toString()
    }

    fun shareSuccess(platform: String?) {
        StreamBiz.sharedLive(ShareLiveRequest(anchorId,
            streamId,platform), listener = object:RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
            }

        })
    }

    fun addFollow(
        followId: String,
        userId: String,
        roomId: String? = null,
        sceneId: String? = null,
        status: Int? = null,
    ) {
        StreamBiz.liveFollows(
            followId, userId, roomId, sceneId, status, null,
            object : RequestListener<BaseModel> {
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                if (result?.errCode == 0) {
                    if(status == 0){
                        followStateLiveData.value = false
                    }else{
                        followStateLiveData.value = true
                    }

                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                followStateLiveData.value = false
            }

        })
    }

    fun  addFollowAndExit() {

    }

    /**
     * isCreateRoom 主播：ture，用户：false
     */
    fun joinGroup(isRoomExist: Boolean) {
        handler.postDelayed({ _joinGroup(isRoomExist) }, 500)
    }

    private fun _joinGroup(isRoomExist: Boolean) {
//        IMSocketImpl.getInstance().joinChatRoom(isRoomExist, streamId, object : IOperateCallback {
//            override fun onSuccess() {
////                joinGroupSuccess.postCall()
//                ULog.d("clll", "joinGroup:onSuccess")
//            }
//
//            override fun onFail(code: String, desc: String) {
//                if (retryJoinGroupCount < MAX_RETRY) {
//                    retryJoinGroupCount++
//                    handler.postDelayed(
//                        { _joinGroup(isRoomExist) },
//                        (retryJoinGroupCount * 2 * 1000).toLong()
//                    )
//                }
//                if(joinChatRoomCode != code){
//                    joinGroupFail.value = "($code)$desc"
//                }
//                joinChatRoomCode = code
//                ULog.d("clll", "joinGroup:onFail$code---$desc")
//            }
//        })
    }

    //发送公聊区聊天信息
    fun sendLiveTxtMsg(roomId: String, sceneId: String, anchorId: String,
                       content: String, isSuperAdmin: Boolean, isRoomAdmin: Boolean,
                       isAnchor: Boolean, userAvatar: String, userId: String, userName: String) {

        var rtTxtMsgRequest = RTTxtMsgRequest(roomId, sceneId, anchorId, content, isSuperAdmin, isRoomAdmin, isAnchor, userAvatar, userId, userName)
        ChatIMBiz.sendTextMsg(rtTxtMsgRequest, object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
//                LaToastUtil.showShort("发送成功")
                sendTxtLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                LaToastUtil.showShort("发送失败")
            }

        })
    }

    //发送禁言信息
    fun sendBannedMsg(context: Context) {
        val banned = !bannedLiveData.value
        var bannedStatus = ""
        var bannedMsg:BannedMsg = BannedMsg()
        if(banned){
            bannedMsg.bannedType = 1
            bannedStatus = "mute"
        }else{
            bannedMsg.bannedType = 2
            bannedStatus = "unmute"
        }

        ChatIMBiz.bannedChatRoomAllUser(BannedRequest(streamId, anchorId, bannedStatus), object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                when(banned){
                    true-> {
                        LaToastUtil.showShort(context.getString(R.string.stream_banned))//暂未提供文案
                    }
                    false-> {
                        LaToastUtil.showShort(context.getString(R.string.stream_banned_remove))
                    }
                }
                bannedLiveData.value = banned
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
            }

        })
    }


    //暂时不用
    fun getShareInfo() {
        StreamBiz.getShareInfo(object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
            }

        })
    }

    fun sendCommonMessage(msg: String?) {

    }

    /*

     */
    fun getRoomInfo(roomId:String, sceneId:String, userId:String, is_login:Boolean ){
        StreamBiz.getRoomInfo(roomId, sceneId, userId, object :
            RequestListener<EnterRoomBean> {
            override fun onSuccess(reqId: String?, result: EnterRoomBean?) {
                roomInfoLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                var enterRoomBean = EnterRoomBean()
                enterRoomBean.errCode = errCode?.toInt()!!
                enterRoomBean.errMsg = msg
                roomInfoLiveData.value = enterRoomBean
            }

        })
    }

    /*
     */
    fun getRoomInfoExtra(roomId:String, sceneId:String, userId:String, is_login:Boolean ){
        StreamBiz.getRoomInfoExtra(roomId, sceneId, userId, object :
            RequestListener<RoomInfoExtraBean> {
            override fun onSuccess(reqId: String?, result: RoomInfoExtraBean?) {
                roomInfoExtraLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                var roomInfoExtraBean = RoomInfoExtraBean()
                roomInfoExtraBean.errCode = errCode?.toInt()!!
                roomInfoExtraBean.errMsg = msg
                roomInfoExtraLiveData.value = roomInfoExtraBean
            }

        })
    }


    fun onDestroy() {
        retryJoinGroupCount = 0
//        handler.removeCallbacksAndMessages(null)
        IMSocketBase.instance().signOut()
    }

    fun giftCallClick() {
        giftCallClickEvent.value = true
    }

    fun initIM(context: Context, action:String, roomId:String, sceneId:String, userId:String, userName: String, isLogin:Boolean){
        IMSocketBase.instance().init(context)
        WebSocketManager._getInstance().initIM("", true, "", "")
        IMSocketBase.instance().login(object : EventCallback {
            override fun Success() {
                LaToastUtil.showShort("socket成功-------")
                GlobalScope.launch(Dispatchers.Main) {
                    IMSocketBase.instance().sendMessageBySocket(BaseRoomMessage.TYPE_ENTER_ROOM_TO_SERVER.toString(), GsonUtils.toJson(EnterRoomMsgRequest(action, roomId, sceneId, userId, userName, isLogin)), object: ChatSendCallback{
                        override fun sendSuccess(msg: String?) {

                        }

                        override fun sendFail(code: Int, desc: String?) {
                        }

                    })
                }
            }

            override fun Error(code: String?, desc: String?) {
                LaToastUtil.showShort("socket失败")
            }

        }, DataBus.instance().USER_ID, roomId)
    }

    fun imOutRoom(action:String, roomId:String, sceneId:String, userId:String, userName: String, isLogin:Boolean){
        IMSocketBase.instance().sendMessageBySocket(BaseRoomMessage.TYPE_ENTER_ROOM_TO_SERVER.toString(), GsonUtils.toJson(EnterRoomMsgRequest(action, roomId, sceneId, userId, userName, isLogin)), object: ChatSendCallback{
            override fun sendSuccess(msg: String?) {

            }

            override fun sendFail(code: Int, desc: String?) {
            }

        })

    }
}