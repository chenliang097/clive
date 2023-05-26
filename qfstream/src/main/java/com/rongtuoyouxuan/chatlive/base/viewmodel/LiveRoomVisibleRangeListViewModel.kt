package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveRoomVisibleRangeRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtbiz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.qfcommon.eventbus.LiveEventData
import com.rongtuoyouxuan.chatlive.qfcommon.eventbus.MLiveEventBus

class LiveRoomVisibleRangeListViewModel(application: Application) : AndroidViewModel(application) {
    private val PAGE_NUM = 10
    var fansListLiveData = MutableLiveData<LiveRoomVisibleRangeListBean?>()
    var setUserAllowRangLiveData = MutableLiveData<BaseModel>()

    fun getStartLiveFansList(userId: String?, roomId: String, sceneId : String, pageNo: Int) {
        if (userId != null) {
            UserRelationBiz.instance?.getStartLiveFansList(
                null,
                userId,
                userId,
                roomId,
                sceneId,
                pageNo,
                PAGE_NUM,
                object : RequestListener<LiveRoomVisibleRangeListBean?> {
                    override fun onSuccess(reqId: String, result: LiveRoomVisibleRangeListBean?) {
                        fansListLiveData.value = result
                    }

                    override fun onFailure(reqId: String, errCode: String, msg: String) {
                        try {
                            val liveRoomVisibleRangeListBean = LiveRoomVisibleRangeListBean()
                            liveRoomVisibleRangeListBean.errCode = errCode.toInt()
                            fansListLiveData.setValue(liveRoomVisibleRangeListBean)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
        }
    }

    fun setUserAllowRange(ruleType: Int, user_id: String, sceneId: String, userIdList:MutableList<String>) {
        var request = LiveRoomVisibleRangeRequest(ruleType, user_id, sceneId, userIdList)
        StreamBiz.setUserAllowRange(request,
            object :
                RequestListener<BaseModel> {
                override fun onSuccess(reqId: String, result: BaseModel) {
                    setUserAllowRangLiveData.value = result
                    MLiveEventBus.get(LiveEventData.LIVE_ALLOW_RANGE).post(ruleType.toString())
                }

                override fun onFailure(reqId: String, errCode: String, msg: String) {
                    try {
                        var baseModel =
                            BaseModel()
                        baseModel.errCode = errCode.toInt()
                        baseModel.errMsg = msg
                        setUserAllowRangLiveData.value = baseModel
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }
            })
    }
}