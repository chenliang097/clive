package com.rongtuoyouxuan.chatlive.base.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowStatusBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz.liveFollows
import com.rongtuoyouxuan.chatlive.biz2.user.UserRelationBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class FansListViewModel(application: Application):BaseListFragmentViewModel<FansListBean>(application) {

    private var toUserId:String = ""
    private var status:Int = 0
    var addFollowLiveData = MutableLiveData<FollowStatusBean?>()
    var cancelFollowLiveData = MutableLiveData<FollowStatusBean?>()
    var removeFansLiveData = MutableLiveData<FollowStatusBean?>()


    fun setToUserId(toUserId:String){
        this.toUserId = toUserId
        if(toUserId==DataBus.instance().USER_ID){
            status =0
        }else{
            status =1
        }
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        UserRelationBiz.instance?.getFansList(null, DataBus.instance().USER_ID, toUserId, mPage, 20, object :RequestListener<FansListBean?>{
            override fun onSuccess(reqId: String?, result: FansListBean?) {
                result?.event = event
                _getData().value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    fun addFollow(fUserId:String,tUserId:String, position:Int, bean:FansListBean.ItemBean){
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

    fun cancelFollow(fUserId:String,tUserId:String, position:Int, bean:FansListBean.ItemBean){
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

    fun removeFans(fUserId:String,tUserId:String, position:Int, bean:FansListBean.ItemBean){
        UserRelationBiz.instance?.cancelFollow(null, fUserId, tUserId, object : RequestListener<FollowStatusBean?> {
            override fun onSuccess(reqId: String, result: FollowStatusBean?) {
                result?.data?.position = position
                result?.data?.followBean = bean
                removeFansLiveData.value = result
            }

            override fun onFailure(reqId: String, errCode: String, msg: String) {
                try {
                    val baseModel = FollowStatusBean()
                    baseModel.errCode = errCode.toInt()
                    baseModel.errMsg = msg
                    removeFansLiveData.setValue(baseModel)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }
}