package com.rongtuoyouxuan.chatlive.biz2.model.login.response;

/*
 *Create by {Mr秦} on 2022/7/23
 */

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;

@Keep
public class LoginResponse extends BaseModel {
    private UserInfo data;

    public UserInfo getData() {
        return data;
    }
}