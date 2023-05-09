package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowListBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PersonalCenterInfoRequest
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworkForUser
import okhttp3.RequestBody

object UserBiz {
    /**
     * 登录
     * */
    fun login(request: LoginRequest, listener: RequestListener<LoginResponse>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).login(request)
        }

    /**
     * 上传文件
     * */
    fun uploadFile(request: RequestBody, listener: RequestListener<UploadFileResponse>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).uploadFile(request)
        }

    fun getPersonalCenterInfo(request: PersonalCenterInfoRequest, listener: RequestListener<PersonalCenterInfoBean>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).getPersonalCenterInfo(request)
        }

    fun getFollowList(page:Int,size:Int, userId:String, followId:String, listener: RequestListener<FollowListBean>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).getFollowlist(page, size, userId, followId)
        }


}