package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import java.lang.Exception

class StreamEndViewModel(application: Application):AndroidViewModel(application) {

    var liveEndLiveData:MutableLiveData<StreamEndBean> = MutableLiveData<StreamEndBean>()

    fun streamEndLive(streamId:String){
        StreamBiz.streamEndlive(null, streamId, object :RequestListener<StreamEndBean>{
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