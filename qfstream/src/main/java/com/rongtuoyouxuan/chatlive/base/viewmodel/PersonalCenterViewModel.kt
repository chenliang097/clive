package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorRoomSettingRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.PopolarityRankBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomInfoExtraBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PersonalCenterInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.biz2.user.UserBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class PersonalCenterViewModel(application: Application):AndroidViewModel(application) {

    private var roomId:String = ""
    var infoLiveData: MutableLiveData<PersonalCenterInfoBean> = MutableLiveData<PersonalCenterInfoBean>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    fun getPersonalCenterInfo(followId:String) {
        var request = PersonalCenterInfoRequest(DataBus.instance().USER_ID, followId)
        UserBiz.getPersonalCenterInfo(request, object :RequestListener<PersonalCenterInfoBean>{
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