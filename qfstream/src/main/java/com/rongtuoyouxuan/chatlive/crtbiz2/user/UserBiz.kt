package com.rongtuoyouxuan.chatlive.crtbiz2.user

import com.rongtuoyouxuan.chatlive.crtbiz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FollowListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PersonalCenterInfoBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PersonalCenterInfoRequest
import newNetworkForUser
import okhttp3.RequestBody

object UserBiz {
    /**
     * 登录
     * */
    fun login(request: LoginRequest, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LoginResponse>) =
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
    fun uploadFile(request: RequestBody, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<UploadFileResponse>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).uploadFile(request)
        }

    fun getPersonalCenterInfo(request: PersonalCenterInfoRequest, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<PersonalCenterInfoBean>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).getPersonalCenterInfo(request)
        }

    fun getFollowList(page:Int,size:Int, userId:String, followId:String, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<FollowListBean>) =
        newNetworkForUser(
            null,
            listener,
            "",
        ) {
            it.create(UserServer::class.java).getFollowlist(page, size, userId, followId)
        }


}