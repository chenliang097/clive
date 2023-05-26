package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import java.lang.Exception

class AnchorCenterViewModel(application: Application):AndroidViewModel(application) {

    var streamStaticsLiveData:MutableLiveData<StreamEndBean> = MutableLiveData<StreamEndBean>()

    fun getStreamStatiscData(userId:String, t:Int){
        StreamBiz.getStreamStatiscData(null, userId, t, object : RequestListener<StreamEndBean> {
            override fun onSuccess(reqId: String?, result: StreamEndBean?) {
                streamStaticsLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try{
                    var streamEndBean = StreamEndBean();
                    streamEndBean.errCode = errCode?.toInt()!!
                    streamEndBean.errMsg = msg
                    streamStaticsLiveData.value = streamEndBean
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }

        })
    }
}