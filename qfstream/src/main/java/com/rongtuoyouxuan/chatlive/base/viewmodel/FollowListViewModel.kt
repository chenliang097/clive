package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowStatusBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz.liveFollows
import com.rongtuoyouxuan.chatlive.biz2.user.UserBiz
import com.rongtuoyouxuan.chatlive.biz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class FollowListViewModel(application: Application):BaseListFragmentViewModel<FollowListBean>(application) {

    private var toUserId:String = ""
    var delManagerLiveData: MutableLiveData<Int> = MutableLiveData<Int>()
    var addFollowLiveData = MutableLiveData<FollowStatusBean?>()
    var cancelFollowLiveData = MutableLiveData<FollowStatusBean?>()

    fun setToUserId(toUserId:String){
        this.toUserId = toUserId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        UserBiz.getFollowList(mPage, 20, DataBus.instance().USER_ID, toUserId, object :RequestListener<FollowListBean>{
            override fun onSuccess(reqId: String?, result: FollowListBean?) {
                result?.event = event
                _getData().value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    fun addFollow(fUserId:String,tUserId:String, position:Int, bean: FansListBean.ItemBean){
        UserRelationBiz.instance?.addFollow(null, fUserId, tUserId, object : RequestListener<FollowStatusBean?> {
            override fun onSuccess(reqId: String, result: FollowStatusBean?) {
                result?.data?.position = position
                result?.data?.followBean = bean
                addFollowLiveData.value = result
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {
                try {
                    val baseModel = FollowStatusBean()
                    baseModel.errCode = errCode.toInt()
                    baseModel.errMsg = msg
                    addFollowLiveData.setValue(baseModel)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun cancelFollow(fUserId:String,tUserId:String, position:Int, bean: FansListBean.ItemBean){
        UserRelationBiz.instance?.cancelFollow(null, fUserId, tUserId, object : RequestListener<FollowStatusBean?> {
            override fun onSuccess(reqId: String, result: FollowStatusBean?) {
                result?.data?.position = position
                result?.data?.followBean = bean
                cancelFollowLiveData.value = result
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {
                try {
                    val baseModel = FollowStatusBean()
                    baseModel.errCode = errCode.toInt()
                    baseModel.errMsg = msg
                    cancelFollowLiveData.setValue(baseModel)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}