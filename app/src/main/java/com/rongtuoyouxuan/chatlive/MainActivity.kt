package com.rongtuoyouxuan.chatlive

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.GsonUtils
import com.rongtuoyouxuan.app.Open3rdpayActivity
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.EnterRoomMsgRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.MainLiveEnterBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.bean.ISource
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libsocket.WebSocketManager
import com.rongtuoyouxuan.libsocket.base.ChatSendCallback
import com.rongtuoyouxuan.libsocket.base.EventCallback
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
import kotlinx.android.synthetic.main.activity_main.*
import java.net.InetAddress

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pushStreamBtn.setOnClickListener {
            if(!TextUtils.isEmpty(etUserId.text.toString().trim()) && !TextUtils.isEmpty(etUserName.text.toString().trim())){
                Router.toStreamActivity(etUserId.text.toString().trim(), etUserName.text.toString().trim())
            }else{
                Router.toStreamActivity()
            }

//            IMSocketBase.instance().init(this)
//            WebSocketManager._getInstance().initIM("", true, "", "")
//            IMSocketBase.instance().login(object : EventCallback {
//                override fun Success() {
//                    LaToastUtil.showShort("socket成功---")
////                    IMSocketBase.instance().sendMessageBySocket("", GsonUtils.toJson(
////                        EnterRoomMsgRequest(action, roomId, sceneId, userId, isLogin)
////                    ), object: ChatSendCallback {
////                        override fun sendSuccess(msg: String?) {
////
////                        }
////
////                        override fun sendFail(code: Int, desc: String?) {
////                        }
////
////                    })
//                }
//
//                override fun Error(code: String?, desc: String?) {
//                    LaToastUtil.showShort("socket失败")
//                }
//
//            }, StreamPreviewLayout.USER_ID, "1000000")
        }

        pullLiveBtn.setOnClickListener {
            getMainLiveEnter()
        }

        payBtn.setOnClickListener {
            var intent = Intent(MainActivity@this, Open3rdpayActivity::class.java)
            startActivity(intent)
        }

        identificationBtn.setOnClickListener {
            Router.toUserIdentificationActivity()
        }
    }

    var handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            try{

            }catch (e:Exception){
                e.stackTrace
            }
        }
    }

    fun getMainLiveEnter(){
        if(!TextUtils.isEmpty(etUserId.text.toString().trim()) && !TextUtils.isEmpty(etUserName.text.toString().trim())){
            DataBus.instance().USER_ID = etUserId.text.toString().trim()
            DataBus.instance().USER_NAME = etUserName.text.toString().trim()
        }
        StreamBiz.mainLiveEnter(DataBus.instance().USER_ID, object :RequestListener<MainLiveEnterBean>{
            override fun onSuccess(reqId: String?, result: MainLiveEnterBean?) {
                Router.toLiveRoomActivity(result?.data?.room_id_str, result?.data?.stream_id, result?.data?.scene_id_str, result?.data?.anchor_id, ISource.FROM_MAIN_TAB)

            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                LaToastUtil.showShort("直播入口失败")
            }

        })
    }
}