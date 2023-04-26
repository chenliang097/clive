package com.rongtuoyouxuan.chatlive.net2;

import android.text.TextUtils;

public class Result<T> {
    private T result;
    private String errCode = "";
    private String errMsg = "";

    public Result() {
    }

    public Result(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Result(T result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return TextUtils.isEmpty(errCode) || errCode.equals("0");
    }

    public String getErrCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public T getData() {
        return result;
    }

}