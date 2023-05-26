package com.rongtuoyouxuan.chatlive.qfcommon.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.face.FaceIdBiz
import com.rongtuoyouxuan.chatlive.crtbiz2.model.face.FaceIdBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.face.FaceIdResultBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.face.FaceIdStatusBean
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener

class TencentFaceViewModel(application: Application) : AndroidViewModel(application) {
    var faceDataLiveData = MutableLiveData<FaceIdBean?>()
    var faceResultLiveData = MutableLiveData<FaceIdResultBean?>()
    var faceStatusLiveData = MutableLiveData<FaceIdStatusBean?>()

    fun getFaceData(name: String,
                    id_no: String,
                    userId: String,) {
        FaceIdBiz.getTencentFaceData(name, id_no, userId, object : RequestListener<FaceIdBean> {
            override fun onSuccess(reqId: String?, result: FaceIdBean?) {
                faceDataLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try {
                    var faceIdBean = FaceIdBean()
                    faceIdBean.errCode = errCode?.toInt()!!
                    faceIdBean.errMsg = msg
                    faceDataLiveData.value = faceIdBean
                }catch (e:Exception){
                    e.stackTrace
                }

            }

        })
    }

    fun sendTencentFaceResult(sim: String,
                    order_no: String,
                    userId: String) {
        FaceIdBiz.sendTencentFaceResult(sim, order_no, userId, object :
            RequestListener<FaceIdResultBean> {
            override fun onSuccess(reqId: String?, result: FaceIdResultBean?) {
                faceResultLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try {
                    var faceIdBean = FaceIdResultBean()
                    faceIdBean.errCode = errCode?.toInt()!!
                    faceIdBean.errMsg = msg
                    faceResultLiveData.value = faceIdBean
                }catch (e:Exception){
                    e.stackTrace
                }
            }

        })
    }

    fun getTencentIdentificationStatus(userId: String) {
        FaceIdBiz.getTencentIdentificationStatus(userId, object :
            RequestListener<FaceIdStatusBean> {
            override fun onSuccess(reqId: String?, result: FaceIdStatusBean?) {
                faceStatusLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try {
                    var faceIdBean = FaceIdStatusBean()
                    faceIdBean.errCode = errCode?.toInt()!!
                    faceIdBean.errMsg = msg
                    faceStatusLiveData.value = faceIdBean
                }catch (e:Exception){
                    e.stackTrace
                }
            }

        })
    }
}