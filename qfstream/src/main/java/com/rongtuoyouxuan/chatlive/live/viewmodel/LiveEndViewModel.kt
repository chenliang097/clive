package com.rongtuoyouxuan.chatlive.live.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import java.lang.Exception

open class LiveEndViewModel(application: Application):AndroidViewModel(application) {
     var followAddLiveData:MutableLiveData<BaseModel>  = MutableLiveData<BaseModel>()

     fun addFollow(anchorId:Long){

    }
}