package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.arch.LiveEvent
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomMaskWordsBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.SetRoomMaskWordsRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener

class AnchorManagerViewModel(application: Application):AndroidViewModel(application) {

    var roomMaskWordsLiveData:MutableLiveData<RoomMaskWordsBean> = MutableLiveData<RoomMaskWordsBean>()
    var setRoomMaskWordsLiveData:MutableLiveData<BaseModel> = MutableLiveData<BaseModel>()
    @JvmField
    var mMessageButton = LiveEvent<SendEvent>() //弹出控件
    @JvmField
    var hideInputViewLiveEvent = LiveEvent<Boolean>()


    fun getRoomMaskWords(roomId:String){
        StreamBiz.getRoomMaskWords(roomId, object :RequestListener<RoomMaskWordsBean>{
            override fun onSuccess(reqId: String?, result: RoomMaskWordsBean?) {
                roomMaskWordsLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var roomMaskWordsBean = RoomMaskWordsBean();
                    roomMaskWordsBean.errCode = errCode?.toInt()!!
                    roomMaskWordsBean.errMsg = msg
                    roomMaskWordsLiveData.value = roomMaskWordsBean
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })
    }

    fun setRoomMaskWords(roomId:String, userId:String, words:String){
        var requests = SetRoomMaskWordsRequest(roomId, userId, words)
        StreamBiz.setRoomMaskWord(requests, object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                setRoomMaskWordsLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var baseModel = BaseModel();
                    baseModel.errCode = errCode?.toInt()!!
                    baseModel.errMsg = msg
                    setRoomMaskWordsLiveData.value = baseModel
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })
    }

    fun deleteRoomMaskWords(roomId:String, userId:String, words:String){
        var requests = SetRoomMaskWordsRequest(roomId, userId, words)
        StreamBiz.deleteRoomMaskWord(requests, object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                setRoomMaskWordsLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var baseModel = BaseModel();
                    baseModel.errCode = errCode?.toInt()!!
                    baseModel.errMsg = msg
                    setRoomMaskWordsLiveData.value = baseModel
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })
    }
}