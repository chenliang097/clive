package com.rongtuoyouxuan.chatlive.crtnet

class SimpleRequestListener<Model : BaseModel> :
    RequestListener<Model> {
    override fun onSuccess(reqId: String?, result: Model) {
    }

    override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
    }
}