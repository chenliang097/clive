package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.BlacklistRequest
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RoomManagerBlackListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.viewmodel.BaseListFragmentViewModel

open class AnchorManagerBlackListViewModel(application: Application):
    BaseListFragmentViewModel<RoomManagerBlackListBean>(application) {

    private var roomId:String = ""
    var delManagerLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getManagerBlackList(roomId, mPage, 30, object :
            RequestListener<RoomManagerBlackListBean> {
            override fun onSuccess(reqId: String?, result: RoomManagerBlackListBean?) {
                result?.event = event
                _getData().value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    fun deleteRoomBlack(fUserId:String, tUserId:String, fNickName:String, tNickName:String, position:Int){
        var request = BlacklistRequest(fUserId, tUserId, roomId, fNickName, tNickName)
        StreamBiz.relieveRoomBlack(request, object :
            RequestListener<BaseModel> {
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                if (result?.errCode == 0){
                    delManagerLiveData.value = position
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                LaToastUtil.showShort(msg)
            }

        })
    }
}