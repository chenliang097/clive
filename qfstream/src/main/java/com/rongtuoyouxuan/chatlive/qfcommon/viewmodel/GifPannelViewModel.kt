package com.rongtuoyouxuan.chatlive.qfcommon.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.gif.GifListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.GifMsg
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.GifListBiz
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtrouter.bean.ISource
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener

class GifPannelViewModel(application: Application):AndroidViewModel(application) {


    var sendLiveData:MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var gifPannelListLiveData:MutableLiveData<GifListBean> = MutableLiveData<GifListBean>()

    fun getGifPannelList(mPage: Int) {
        GifListBiz.getGifList(mPage, 2000, object : RequestListener<GifListBean> {
            override fun onSuccess(reqId: String?, result: GifListBean?) {
                gifPannelListLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    //发送gif信息
    fun sendGifMessage(streamId:String?, gifId: Int, gifUrl: String, fromSource: String) {
        var gifMsg: GifMsg = GifMsg()
        gifMsg.fansName = DataBus.instance().curLiveRoomFansClub
        if(!TextUtils.isEmpty(gifMsg.fansName)){
            gifMsg.fans = 1
        }
        gifMsg.gifId = gifId
        gifMsg.gifUrl = gifUrl
        when(fromSource){
            ISource.FROM_LIVE_ROOM->sendChatRoomMsg(streamId, gifMsg)
            ISource.FROM_PRIVATE_CHAT->sendPrivateChatMsg(streamId, gifMsg)
            ISource.FROM_GROUP_CHAT->sendGroupChatMsg(streamId, gifMsg)
        }

    }

    private fun sendChatRoomMsg(streamId:String?, gifMsg: GifMsg){
//        IMSocketImpl.getInstance().sendChatRoomMessage(streamId, gifMsg , DataBus.instance().userInfo.value,
//            object : ISendMsgCallBack {
//                override fun onSuccess(
//                    message: Message,
//                    msgBody: BaseMsg.MsgBody?
//                ) {
//                    sendLiveData.value = true
//                    IMSocketImpl.getInstance().chatRoom(streamId).gifCallback.setValue(gifMsg)
//                }
//
//                override fun onFail(errorCode: ImErrorCode) {
////                    ToastUtils.showLong("sendGifMessage：发送失败")
//                }
//
//            });
    }

    private fun  sendPrivateChatMsg(streamId:String?, gifMsg: GifMsg){
//        IMSocketImpl.getInstance().sendPrivateMessage(streamId, gifMsg, DataBus.instance().userInfo.value, object : ISendMsgCallBack{
//            override fun onSuccess(message: Message, msgBody: BaseMsg.MsgBody?) {
//                LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_SEND_IM_GIF_SUC).value = message
//            }
//
//            override fun onFail(errorCode: ImErrorCode) {
////                ToastUtils.showLong("发送失败")
//            }
//
//        })
    }

    private fun  sendGroupChatMsg(streamId:String?, gifMsg: GifMsg){
//        IMSocketImpl.getInstance().sendGroupMessage(streamId, gifMsg, DataBus.instance().userInfo.value, object : ISendMsgCallBack{
//            override fun onSuccess(message: Message, msgBody: BaseMsg.MsgBody?) {
//                LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_SEND_IM_GIF_SUC).value = message
//
//            }
//
//            override fun onFail(errorCode: ImErrorCode) {
////                ToastUtils.showLong("sendGifMessage：发送失败")
//            }
//
//        })
    }
}