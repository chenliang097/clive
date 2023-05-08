package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import java.lang.Exception

class StreamEndViewModel(application: Application):AndroidViewModel(application) {

    var liveEndLiveData:MutableLiveData<StreamEndBean> = MutableLiveData<StreamEndBean>()

    fun getStreamStatiscData(streamId:String, t:Int){
        StreamBiz.getStreamStatiscData(null, streamId, t, object :RequestListener<StreamEndBean>{
            override fun onSuccess(reqId: String?, result: StreamEndBean?) {
                liveEndLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var streamEndBean = StreamEndBean();
                    streamEndBean.errCode = errCode?.toInt()!!
                    streamEndBean.errMsg = msg
                    liveEndLiveData.value = streamEndBean
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })

    }

    fun getStreamEnd(roomId:String, sceneId:String){
        var streamEndRequest = StreamEndRequest(DataBus.instance().USER_ID, roomId, sceneId)
        StreamBiz.getStreamEnd(streamEndRequest, object :RequestListener<StreamEndBean>{
            override fun onSuccess(reqId: String?, result: StreamEndBean?) {
                liveEndLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var streamEndBean = StreamEndBean();
                    streamEndBean.errCode = errCode?.toInt()!!
                    streamEndBean.errMsg = msg
                    liveEndLiveData.value = streamEndBean
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })

    }
}