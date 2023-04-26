package com.rongtuoyouxuan.chatlive.biz2.face

import com.rongtuoyouxuan.chatlive.biz2.model.face.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.*
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworks

object FaceIdBiz {

    //获取人脸识别数据
    fun getTencentFaceData(
        name: String,
        id_no: String,
        userId: String,
        listener: RequestListener<FaceIdBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(FaceIdServer::class.java)
                .getTencentFaceData(FaceIdRequest(name, id_no, userId))
        }
    }

    //人脸识别结果
    fun sendTencentFaceResult(
        sim: String,
        order_no: String,
        userId: String,
        listener: RequestListener<FaceIdResultBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(FaceIdServer::class.java)
                .sendTencentFaceResult(FaceIdResultRequest(sim, order_no, userId))
        }
    }

    //人脸识别结果
    fun getTencentIdentificationStatus(
        userId: String,
        listener: RequestListener<FaceIdStatusBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(FaceIdServer::class.java)
                .getTencentIdentificationStatus(userId)
        }
    }

}