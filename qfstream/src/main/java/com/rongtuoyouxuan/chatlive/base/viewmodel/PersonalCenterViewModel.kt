package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PersonalCenterInfoRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.user.UserBiz
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener

open class PersonalCenterViewModel(application: Application):AndroidViewModel(application) {

    private var roomId:String = ""
    var infoLiveData: MutableLiveData<PersonalCenterInfoBean> = MutableLiveData<PersonalCenterInfoBean>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    fun getPersonalCenterInfo(followId:String) {
        var request = PersonalCenterInfoRequest(DataBus.instance().USER_ID, followId)
        UserBiz.getPersonalCenterInfo(request, object : RequestListener<PersonalCenterInfoBean> {
            override fun onSuccess(reqId: String?, result: PersonalCenterInfoBean?) {
                infoLiveData?.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try {
                    var infoBean = PersonalCenterInfoBean()
                    infoBean.errCode = errCode?.toInt()!!
                    infoBean.errMsg = msg
                    infoLiveData.value = infoBean
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        })
    }
}