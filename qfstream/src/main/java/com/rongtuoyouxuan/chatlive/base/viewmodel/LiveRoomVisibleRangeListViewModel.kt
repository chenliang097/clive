package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.biz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener

class LiveRoomVisibleRangeListViewModel(application: Application) : AndroidViewModel(application) {
    private val PAGE_NUM = 10
    var fansListLiveData = MutableLiveData<LiveRoomVisibleRangeListBean?>()
    var setUserAllowRangLiveData = MutableLiveData<BaseModel>()

    fun getStartLiveFansList(userId: String?, status: Int, pageNo: Int) {
        UserRelationBiz.instance?.getStartLiveFansList(
            null,
            userId,
            status,
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

    fun setUserAllowRange(ruleType: Int, user_id: String, sceneId: String, userIdList:MutableList<String>) {
        var request = LiveRoomVisibleRangeRequest(ruleType, user_id, sceneId, userIdList)
        StreamBiz.setUserAllowRange(request,
            object : RequestListener<BaseModel> {
                override fun onSuccess(reqId: String, result: BaseModel) {
                    setUserAllowRangLiveData.value = result
                }

                override fun onFailure(reqId: String, errCode: String, msg: String) {
                    try {
                        var baseModel = BaseModel()
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