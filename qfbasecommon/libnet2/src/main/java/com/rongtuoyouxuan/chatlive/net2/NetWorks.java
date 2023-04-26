package com.rongtuoyouxuan.chatlive.net2;

import androidx.lifecycle.LifecycleOwner;
import android.os.Looper;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.log.PLog;

public abstract class NetWorks<Model extends BaseModel> extends NetWorkBoundResource<Model> {
    private RequestListener<Model> mListener;
    public NetWorks(LifecycleOwner lifecycleOwner, final RequestListener<Model> listener) {
        super(lifecycleOwner);
        if (PLog.isDebugMode() && Looper.myLooper() != Looper.getMainLooper()) {
            //在开发模式下，强烈建议在主线程实例化NetWorks，在线上模式中，该不会抛出该异常
            throw new RuntimeException("NetWorks 实例化必须在主线程调整用(lifecycleOwner 线程不安全)");
        }
        mListener = listener;
    }

    private String getErrorMsg(String errCode, String msg) {
        String reqId = getReqId();
        String mixErrCode;
//        if (TextUtils.isEmpty(reqId)) {
//            mixErrCode = "("+errCode+")";
//        } else {
//            mixErrCode = "("+reqId+errCode+")";
//        }
        return TextUtils.isEmpty(msg) ?
                Constants.MSG_DATA_ERROR
                : msg;
    }


    @Override
    public void success(BaseNetImpl.NetInfo netInfo, Model result) {
        if(mListener != null) {
            if(result.errCode == 0) {
                mListener.onSuccess(getReqId(), result);
            } else {
                String code  = result.errCode+"";
                String msg = getErrorMsg(result.errCode+"", result.errMsg);
                if (!hookNetWorkError(netInfo, code, msg)) {
                    return;
                }
                mListener.onFailure(getReqId(), code, msg);
            }
        }
    }


    private boolean hookNetWorkError(BaseNetImpl.NetInfo netInfo, String code, String msg) {
        BaseNetImpl.HookNetworkError hookNetworkError = BaseNetImpl.getInstance().getHookNetworkError();
        if (hookNetworkError != null) {
            return hookNetworkError.hookError(netInfo, code, msg);
        }
        return true;
    }
    @Override
    public void fail(BaseNetImpl.NetInfo netInfo, String code, String msg) {
        if (!hookNetWorkError(netInfo, code, msg)) {
            return;
        }

        if(mListener != null){
            mListener.onFailure(getReqId(), code, getErrorMsg(code, msg));
        }
    }

    @Override
    public String getBaseUrl() {
        return "";
    }

    @Override
    protected void cancelListener() {
        mListener = null;
    }

    abstract protected String getReqId();
}
