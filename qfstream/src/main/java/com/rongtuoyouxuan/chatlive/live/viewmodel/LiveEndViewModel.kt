package com.rongtuoyouxuan.chatlive.live.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel

open class LiveEndViewModel(application: Application):AndroidViewModel(application) {
     var followAddLiveData:MutableLiveData<BaseModel>  = MutableLiveData<BaseModel>()

     fun addFollow(anchorId:Long){

    }
}