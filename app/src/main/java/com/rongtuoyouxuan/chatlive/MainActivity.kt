package com.rongtuoyouxuan.chatlive

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.NotificationUtils
import com.rongtuoyouxuan.app.Open3rdpayActivity
import com.rongtuoyouxuan.chatlive.biz2.model.stream.MainLiveEnterBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.bean.ISource
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.util.NotificationSetUtil
import com.rongtuoyouxuan.chatlive.util.NotificationUtil
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        check_update_tv.text = "V" + AppUtils.getAppVersionName()
        pushStreamBtn.setOnClickListener {
            if(!TextUtils.isEmpty(etUserId.text.toString().trim())){
                DataBus.instance().setUSER_ID(etUserId.text.toString().trim())
                if(TextUtils.isEmpty(etUserName.text.toString().trim())){
                    DataBus.instance().USER_NAME = "1111111"
                }else{
                    DataBus.instance().USER_NAME = etUserName.text.toString().trim()
                }
                Router.toStreamActivity(DataBus.instance().USER_ID, DataBus.instance().USER_NAME)
            }else{
                LaToastUtil.showShort("先输入id")
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
            requestNotificationPermission(this)
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
        if(!TextUtils.isEmpty(etUserId.text.toString().trim())){
            DataBus.instance().setUSER_ID(etUserId.text.toString().trim())
            if(TextUtils.isEmpty(etUserName.text.toString().trim())){
                DataBus.instance().USER_NAME = "1111111"
            }else{
                DataBus.instance().USER_NAME = etUserName.text.toString().trim()
            }
        }else{
            LaToastUtil.showShort("先输入id")
            return
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

    fun requestNotificationPermission(activity: Activity?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) === PackageManager.PERMISSION_DENIED
            ) {
                // 如果上次申请权限被用户选择了禁止
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf<String>(POST_NOTIFICATIONS),
                    100
                )
            }else{
                getMainLiveEnter()
            }
        } else {
            val enabled: Boolean =
                NotificationManagerCompat.from(this).areNotificationsEnabled()
            if (!enabled) {
                NotificationSetUtil.OpenNotificationSetting(this){
                    getMainLiveEnter()
                }
            }else{
                getMainLiveEnter()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            100->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMainLiveEnter()
                } else {

                }
            }
        }
    }

}