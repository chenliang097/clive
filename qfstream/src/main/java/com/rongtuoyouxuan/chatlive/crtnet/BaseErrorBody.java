package com.rongtuoyouxuan.chatlive.crtnet;

import androidx.annotation.Keep;

/*
 *Create by {Mr秦} on 2022/7/23
 */
@Keep
public class BaseErrorBody {
    private String msg;
    private String errCode;

    public BaseErrorBody(String msg, String errCode) {
        this.msg = msg;
        this.errCode = errCode;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getErrCode() {
        return errCode == null ? "" : errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}