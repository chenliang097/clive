@file:JvmName("NetListener")

package com.rongtuoyouxuan.chatlive.crtnet

import com.rongtuoyouxuan.chatlive.crtutil.util.ToastUtils

fun <Model : BaseModel> listener(
    success: (reqId: String, result: Model) -> Unit,
    failure: ((reqId: String, errCode: String?, msg: String?) -> Unit)? = null,
    complete: (() -> Unit)? = null
): RequestListener<Model> {
    return object : RequestListener<Model> {
        override fun onSuccess(reqId: String, result: Model) {
            success(reqId, result)
            complete?.invoke()
        }

        override fun onFailure(reqId: String, errCode: String?, msg: String?) {
            failure?.invoke(reqId, errCode, msg)
            complete?.invoke()
        }
    }
}

fun <Model : BaseModel> toastListener(
    success: (reqId: String, result: Model) -> Unit,
    failure: ((reqId: String, errCode: String?, msg: String?) -> Unit)? = null,
    complete: (() -> Unit)? = null
): RequestListener<Model> {
    return object : RequestListener<Model> {
        override fun onSuccess(reqId: String, result: Model) {
            success(reqId, result)
            complete?.invoke()
        }

        override fun onFailure(reqId: String, errCode: String?, msg: String?) {
            BaseNetImpl.getInstance()?.mContext?.apply {
                ToastUtils.show(this, msg)
            }
            failure?.invoke(reqId, errCode, msg)
            complete?.invoke()
        }
    }
}
