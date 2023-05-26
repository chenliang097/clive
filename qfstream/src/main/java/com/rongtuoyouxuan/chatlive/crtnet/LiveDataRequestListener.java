package com.rongtuoyouxuan.chatlive.crtnet;

import androidx.lifecycle.MutableLiveData;

public class LiveDataRequestListener<T extends BaseModel> implements RequestListener<T> {

    private MutableLiveData<Result<T>> liveDataResult;
    public LiveDataRequestListener(MutableLiveData<Result<T>> result) {
        this.liveDataResult = result;
    }
    @Override
    public void onSuccess(String reqId, T result) {
        if (liveDataResult == null) {
            return;
        }
        this.liveDataResult.setValue(new Result<>(result));
    }

    @Override
    public void onFailure(String reqId, String errCode, String msg) {
        if (liveDataResult == null) {
            return;
        }
        this.liveDataResult.setValue(new Result<T>(errCode, msg));
    }
}
