package com.rongtuoyouxuan.chatlive.biz2.stream

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.model.stream.UserCardModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks
import retrofit2.Retrofit
import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest
import com.rongtuoyouxuan.chatlive.net2.BaseNetImpl
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.Md5Utils
import com.rongtuoyouxuan.chatlive.util.UUIDUtil
import retrofit2.Call
import java.util.*

object UserCardBiz {

    /**
     * @param uid
     * @param listener
     * 获取资料卡
     */
    fun getLiveUserCardInfo(
        userId:String,
        userCardInfoRequest: UserCardInfoRequest,
        lifecycleOwner: LifecycleOwner? = null,
        listener: RequestListener<UserCardInfoBean?>
    ) {
//        addHeader(userCardInfoRequest, userId)
        object : NetWorks<UserCardInfoBean>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<UserCardInfoBean> {
                return retrofit.create(UserCardServer::class.java).getLiveUserCardInfo(userCardInfoRequest)
            }

            override fun getReqId(): String {
                return ReqId.USER_CARD_INFO
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    private fun addHeader(request:UserCardInfoRequest, userId:String){
        var map:MutableMap<String,String> = HashMap()
        var uuid = UUIDUtil.getUUID()
        var timestamp = System.currentTimeMillis().toString()
        map.put("scbuid", userId)
        map.put("nonce", uuid) //随机数
        map.put("timestamp", timestamp) //时间戳
        map.put("token", Md5Utils.getMD5("appid=" + BaseNetImpl.APPID + "&user_id=$userId"+ "&shijian=" + timestamp))
        map.put("sign", BaseNetImpl.getInstance().treeMap(treeMap(request, uuid, timestamp)))
        BaseNetImpl.getInstance().addCommonQueryParam(map)
    }

    private fun treeMap(request:UserCardInfoRequest, uuid:String, timestamp:String): TreeMap<String, String> {
        val treeMap: TreeMap<String, String> = TreeMap()
        treeMap.put("nonce", uuid)
        treeMap.put("timestamp", timestamp)
        treeMap.put("user_id", request.user_id)
        treeMap.put("follow_id", request.follow_id)
        treeMap.put("room_id", request.room_id)
        treeMap.put("scene_id", request.scene_id)
        return treeMap
    }
}