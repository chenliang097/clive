package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorRoomSettingRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.PopolarityRankBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class PopularityRankViewModel(application: Application):BaseListFragmentViewModel<PopolarityRankBean>(application) {

    private var roomId:String = ""
    var rankLiveData: MutableLiveData<PopolarityRankBean> = MutableLiveData<PopolarityRankBean>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getPopularityRank(roomId, mPage, 20, object :RequestListener<PopolarityRankBean>{
            override fun onSuccess(reqId: String?, result: PopolarityRankBean?) {
                result?.event = event
                _getData().value = result
                rankLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }
}